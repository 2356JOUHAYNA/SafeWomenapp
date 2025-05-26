package com.example.safewo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Alert implements Serializable {

    private static final long serialVersionUID = 1L; // Good practice for Serializable classes

    private Long id;
    private LocalDateTime timestamp;
    private Double latitude;
    private Double longitude;
    private String audioRecordingUrl;
    private String status;
    private Long userId;

    // ===== Constructors =====
    public Alert() {
        // Default constructor
    }

    public Alert(Long id, LocalDateTime timestamp, Double latitude, Double longitude, String audioRecordingUrl, String status, Long userId) {
        this.id = id;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audioRecordingUrl = audioRecordingUrl;
        this.status = status;
        this.userId = userId;
    }

    // ===== Getters and Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getAudioRecordingUrl() { return audioRecordingUrl; }
    public void setAudioRecordingUrl(String audioRecordingUrl) { this.audioRecordingUrl = audioRecordingUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    // ===== Utility Methods =====

    /**
     * Formats the timestamp in a readable format: "dd MMM yyyy, HH:mm"
     *
     * @return Formatted timestamp or "N/A" if null
     */
    public String getFormattedTimestamp() {
        if (timestamp == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);
        return timestamp.format(formatter);
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", audioRecordingUrl='" + audioRecordingUrl + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}
