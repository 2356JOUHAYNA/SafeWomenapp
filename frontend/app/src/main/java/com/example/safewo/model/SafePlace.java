package com.example.safewo.model;

public class SafePlace {
    private double latitude;
    private double longitude;
    private String address;
    private String userComment;

    public SafePlace(double latitude, double longitude, String address, String userComment) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userComment = userComment;
        this.address = address;
    }



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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}

