package com.attedance_sys.demo.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/face")
public class FaceRecognitionController {

    private static final String PYTHON_PATH = "C:\\Users\\Chaimae\\AppData\\Local\\Programs\\Python\\Python310\\python.exe"; // adapte si besoin
    private static final String SCRIPT_PATH = "C:\\Users\\Chaimae\\Desktop\\FaceKey\\face_recognition_python\\recognize_face.py";

    @PostMapping("/recognize")
    public ResponseEntity<String> recognizeFace(@RequestParam("file") MultipartFile file) {
        try {
            // 🔹 1. Sauvegarder temporairement l'image reçue
            Path tempFile = Files.createTempFile("face_", ".jpg");
            file.transferTo(tempFile.toFile());

            // 🔹 2. Lancer le script Python avec le chemin de l'image
            ProcessBuilder pb = new ProcessBuilder(PYTHON_PATH, SCRIPT_PATH, tempFile.toString());
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 🔹 3. Lire la sortie du script Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            Files.deleteIfExists(tempFile); // nettoyage

            if (exitCode == 0) {
                return ResponseEntity.ok(output.toString());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur dans le script Python : " + output);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur : " + e.getMessage());
        }
    }
}
