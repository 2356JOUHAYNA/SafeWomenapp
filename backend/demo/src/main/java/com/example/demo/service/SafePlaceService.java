package com.example.demo.service;



import com.example.demo.dto.SafePlaceDTO;

import com.example.demo.model.SafePlace;
import com.example.demo.repository.SafePlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SafePlaceService {

    private final SafePlaceRepository repository;

    public SafePlaceService(SafePlaceRepository repository) {
        this.repository = repository;
    }

    public void save(SafePlaceDTO dto) {
        SafePlace safePlace = new SafePlace(dto.getLatitude(), dto.getLongitude());
        repository.save(safePlace);
    }

    public List<SafePlaceDTO> getAllSafePlaces() {
        return repository.findAll()
                .stream()
                .map(place -> new SafePlaceDTO(place.getLatitude(), place.getLongitude()))
                .toList();
    }
}

