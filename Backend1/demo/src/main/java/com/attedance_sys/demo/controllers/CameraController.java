package com.attedance_sys.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/camera")
public class CameraController {

    @GetMapping("/start")
    public ResponseEntity<String> startCamera() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\Chaimae\\Desktop\\FaceKey\\face_recognition_python\\recognize_face.py");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Lire la sortie du script Python
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            return ResponseEntity.ok("Script exécuté avec succès ✅\n" + output.toString());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors du lancement du script Python: " + e.getMessage());
        }
    }
}
