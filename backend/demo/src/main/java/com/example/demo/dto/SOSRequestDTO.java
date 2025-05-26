package com.example.demo.dto;

import lombok.Data;

@Data
public class SOSRequestDTO {
    private double latitude;
    private double longitude;
    private String audioFilePath;
}
