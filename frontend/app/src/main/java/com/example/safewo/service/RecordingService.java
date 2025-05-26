package com.example.safewo.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.example.safewo.MainActivity;
import com.example.safewo.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordingService extends Service {

    private static final String TAG = "RecordingService";
    private static final String CHANNEL_ID = "recording_channel";
    private static final int NOTIFICATION_ID = 2;

    private MediaRecorder mediaRecorder;
    private String currentFilePath;
    private boolean isRecording = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, getNotification());

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "START_RECORDING":
                    startRecording();
                    break;
                case "STOP_RECORDING":
                    stopRecording();
                    stopSelf();
                    break;
            }
        }

        return START_STICKY;
    }

    private void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            currentFilePath = getOutputFile();

            mediaRecorder.setOutputFile(currentFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;
            Log.d(TAG, "Recording started at " + currentFilePath);

            // Broadcast que l'enregistrement a commencé
            Intent intent = new Intent("recording_update");
            intent.putExtra("status", "started");
            intent.putExtra("file_path", currentFilePath);
            sendBroadcast(intent);
        } catch (IOException e) {
            Log.e(TAG, "Failed to start recording", e);
        }
    }

    private void stopRecording() {
        if (isRecording && mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                isRecording = false;
                Log.d(TAG, "Recording stopped");

                // Broadcast que l'enregistrement est terminé
                Intent intent = new Intent("recording_update");
                intent.putExtra("status", "stopped");
                intent.putExtra("file_path", currentFilePath);
                sendBroadcast(intent);
            } catch (Exception e) {
                Log.e(TAG, "Failed to stop recording", e);
            }
            mediaRecorder = null;
        }
    }

    private String getOutputFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "AUDIO_" + timeStamp + ".3gp";

        File storageDir = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "SafeWomen");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        return new File(storageDir, fileName).getAbsolutePath();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Recording Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private Notification getNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("SafeWomen - Enregistrement")
                .setContentText("Enregistrement audio en cours")
                .setSmallIcon(R.drawable.ic_alert)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRecording();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
