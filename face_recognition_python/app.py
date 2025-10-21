from flask import Flask, request, jsonify
from deepface import DeepFace
import tempfile, os, requests

app = Flask(__name__)

SPRING_API = "http://localhost:8080/api/pointage/mark/json"

@app.route("/recognize", methods=["POST"])
def recognize():
    if "file" not in request.files:
        return jsonify({"error": "No file uploaded"}), 400

    file = request.files["file"]

    # Sauvegarde temporaire
    temp_file = tempfile.NamedTemporaryFile(delete=False, suffix=".jpg")
    file.save(temp_file.name)
    temp_file.close() #Close before processing

    try:
        # Reconnaissance faciale
        result = DeepFace.find(img_path=temp_file.name, db_path="faces_db")

        if len(result) > 0:
            name = os.path.basename(result[0]["identity"]).split(".")[0]
            status = "AtTime"
        else:
            name = "Unknown"
            status = "Abscent"

        # Payload pour Spring Boot
        payload = {
            "employeeName": name,
            "statut": status
        }

        # POST vers Spring Boot
        spring_response = requests.post(SPRING_API, json=payload)
        spring_data = spring_response.json() if spring_response.ok else spring_response.text

        return jsonify({
            "recognized": name,
            "status": status,
            "springResponse": spring_data
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

    finally:
        try:
            os.remove(temp_file.name)
        except Exception as e:
            print(f"Error deleting temp file: {e}")
        
        

if __name__ == "__main__":
    app.run(port=5001, debug=True)
