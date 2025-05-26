package com.example.demo.repository;


import com.example.demo.model.SafePlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SafePlaceRepository extends JpaRepository<SafePlace, Long> {
}
