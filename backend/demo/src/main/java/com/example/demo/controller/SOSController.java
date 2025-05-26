package com.example.demo.controller;

import com.example.demo.model.SOS;
import com.example.demo.service.SOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/sos")
@CrossOrigin(origins = "*")
public class SOSController {

    @Autowired
    private SOSService sosService;

    @PostMapping("/send")
    public ResponseEntity<?> sendSOS(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam(value = "audioFile", required = false) MultipartFile audioFile) {

        try {
            SOS sos = sosService.saveSOS(latitude, longitude, audioFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "SOS enregistré", "sosId", sos.getId()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur lors de l'enregistrement du SOS"));
        }
    }
}
