package com.chatapp.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileController {

    private final String UPLOAD_DIR = "data/images/";

    public FileController() {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        if (file.isEmpty()) {
            response.put("error", "Please select a file to upload");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + extension;
            Path path = Paths.get(UPLOAD_DIR + newFilename);
            
            // Save file
            Files.write(path, file.getBytes());

            // Return URL (assuming server runs on localhost:8080)
            // In a real app, this should be a full URL or relative path handled by frontend
            String fileUrl = "http://localhost:8080/images/" + newFilename;
            response.put("url", fileUrl);
            
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "Failed to upload file");
            return ResponseEntity.status(500).body(response);
        }
    }
}
