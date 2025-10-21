import cv2
import os

def register_employee():
    name = input("Donner le nom de l'employé : ").strip()
    folder = "employees"
    os.makedirs(folder, exist_ok=True)

    cap = cv2.VideoCapture(0)
    print("Appuyez sur 'q' pour capturer et enregistrer le visage.")

    while True:
        ret, frame = cap.read()
        cv2.imshow("Enregistrement du visage", frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            file_path = os.path.join(folder, f"{name}.jpg")
            cv2.imwrite(file_path, frame)
            print(f"Visage enregistré pour {name} dans {file_path}")
            break

    cap.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    register_employee()
