package com.example.safewo.model;



public class SafePlaceDTO {
    private double latitude;
    private double longitude;

    // Obligatoire pour Retrofit
    public SafePlaceDTO() {}

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
