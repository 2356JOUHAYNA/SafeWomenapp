package com.example.safewo.util;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.safewo.service.RecordingService;


public class AudioRecorder {

    private static final String TAG = "AudioRecorder";
    private final Context context;
    private String currentFilePath;

    public AudioRecorder(Context context) {
        this.context = context;
    }

    public void startRecording() {
        Intent intent = new Intent(context, RecordingService.class);
        intent.setAction("START_RECORDING");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }

        Log.d(TAG, "Recording service started");
    }

    public void stopRecording() {
        Intent intent = new Intent(context, RecordingService.class);
        intent.setAction("STOP_RECORDING");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }

        Log.d(TAG, "Recording service stopped");
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String filePath) {
        this.currentFilePath = filePath;
    }
}
