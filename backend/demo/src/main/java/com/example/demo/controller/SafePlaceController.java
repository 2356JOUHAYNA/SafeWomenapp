package com.example.demo.controller;

import com.example.demo.dto.SafePlaceDTO;
import com.example.demo.service.SafePlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/safe-places")
@CrossOrigin(origins = "*") // à adapter selon besoin
public class SafePlaceController {

    private final SafePlaceService safePlaceService;

    public SafePlaceController(SafePlaceService safePlaceService) {
        this.safePlaceService = safePlaceService;
    }

    // ✅ POST: Ajouter un lieu safe
    @PostMapping
    public ResponseEntity<?> addSafePlace(@RequestBody SafePlaceDTO safePlace) {
        safePlaceService.save(safePlace);
        return ResponseEntity.ok("Lieu ajouté avec succès");
    }

    // ✅ GET: Obtenir tous les lieux safe
    @GetMapping
    public ResponseEntity<List<SafePlaceDTO>> getAllSafePlaces() {
        return ResponseEntity.ok(safePlaceService.getAllSafePlaces());
    }
}
