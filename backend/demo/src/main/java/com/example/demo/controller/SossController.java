package com.example.demo.controller;

import com.example.demo.model.SosAlert;
import com.example.demo.service.SosServicce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sos")
@CrossOrigin(origins = "*") // Autoriser l'accès depuis le front React
public class SossController {

    @Autowired
    private SosServicce sosService;

    // ✅ 1. Ajouter une alerte SOS
    @PostMapping
    public ResponseEntity<String> handleSOS(
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam(value = "audioFile", required = false) MultipartFile audioFile) {

        try {
            SosAlert alert = sosService.saveAlert(
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    audioFile
            );
            return ResponseEntity.ok("SOS enregistré avec l'ID : " + alert.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur serveur : " + e.getMessage());
        }
    }

    // ✅ 2. Récupérer toutes les alertes SOS
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllSOS() {
        List<Map<String, Object>> alerts = sosService.getAllAlerts().stream()
                .map(alert -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", alert.getId());
                    map.put("latitude", alert.getLatitude());
                    map.put("longitude", alert.getLongitude());
                    map.put("timestamp", alert.getTimestamp());
                    map.put("audioPath", alert.getAudioPath());

                    if (alert.getAudioPath() != null) {
                        String fileName = new File(alert.getAudioPath()).getName();
                        map.put("audioUrl", "http://192.168.0.128:8080/audio/" + fileName);
                    }

                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(alerts);
    }

    // ✅ 3. Supprimer une alerte SOS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSOS(@PathVariable Long id) {
        boolean deleted = sosService.deleteAlertById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
