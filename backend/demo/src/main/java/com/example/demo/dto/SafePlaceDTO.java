package com.example.demo.dto;


public class SafePlaceDTO {
    private double latitude;
    private double longitude;

    // Constructors
    public SafePlaceDTO() {}

    public SafePlaceDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters & Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

