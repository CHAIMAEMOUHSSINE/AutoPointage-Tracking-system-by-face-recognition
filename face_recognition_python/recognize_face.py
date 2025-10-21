import sys
import os
from deepface import DeepFace
from datetime import datetime
import json

employees_folder = "employees"

def recognize_from_image(image_path):
    employees = [f for f in os.listdir(employees_folder) if f.endswith(".jpg")]

    for emp in employees:
        try:
            result = DeepFace.verify(image_path, os.path.join(employees_folder, emp), enforce_detection=False)
            if result["verified"]:
                name = emp.replace(".jpg", "")
                statut = "Late" if datetime.now().hour > 9 else "AtTime"
                print(json.dumps({"status": "success", "name": name, "statut": statut}))
                return
        except Exception as e:
            continue

    print(json.dumps({"status": "fail", "message": "Aucun visage reconnu"}))

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print(json.dumps({"status": "error", "message": "Aucune image reçue"}))
        sys.exit(1)

    image_path = sys.argv[1]
    recognize_from_image(image_path)
