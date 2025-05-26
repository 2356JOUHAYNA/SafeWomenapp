package com.example.safewo.ui.auth.sos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.safewo.R;
import com.example.safewo.api.ApiClient;
import com.example.safewo.api.ApiService;
import com.example.safewo.util.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SOSFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "SOSFragment";
    private MaterialButton btnRecordAudio, btnSendSOS;
    private TextView tvRecordingStatus;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap googleMap;
    private Location currentLocation;
    private MediaRecorder mediaRecorder;
    private String audioFilePath;
    private boolean isRecording = false;

    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sos, container, false);

        btnRecordAudio = view.findViewById(R.id.btnRecordAudio);
        btnSendSOS = view.findViewById(R.id.btnSendSOS);
        tvRecordingStatus = view.findViewById(R.id.tvRecordingStatus);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        apiService = ApiClient.getAPIService();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        checkAndRequestPermissions();

        btnRecordAudio.setOnClickListener(v -> {
            if (!isRecording) {
                startRecording();
                btnRecordAudio.setText("Arrêter l'enregistrement");
            } else {
                stopRecording();
                btnRecordAudio.setText("Enregistrer Audio");
            }
        });

        btnSendSOS.setOnClickListener(v -> sendSOS());

        getLastLocation();
        return view;
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), permissions, Constants.LOCATION_PERMISSION_REQUEST_CODE);
                break;
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        currentLocation = location;
                        updateMapLocation(location);
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error getting location", e));
    }

    private void startRecording() {
        audioFilePath = requireActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/audio_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".3gp";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(audioFilePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRecording = true;
            tvRecordingStatus.setText("Enregistrement en cours...");
        } catch (IOException e) {
            Log.e(TAG, "Audio recording failed: " + e.getMessage());
            isRecording = false;
        }
    }

    private void stopRecording() {
        if (isRecording) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                isRecording = false;
                tvRecordingStatus.setText("Enregistrement terminé");
            } catch (Exception e) {
                Log.e(TAG, "Error stopping audio recording: " + e.getMessage());
            }
        }
    }

    private void sendSOS() {
        if (currentLocation == null) {
            Toast.makeText(requireContext(), "Position non disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();
        File audioFile = new File(audioFilePath);

        RequestBody latPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude));
        RequestBody lngPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude));
        MultipartBody.Part audioPart = null;

        if (audioFile.exists()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("audio/3gp"), audioFile);
            audioPart = MultipartBody.Part.createFormData("audioFile", audioFile.getName(), requestBody);
        }

        apiService.sendSOS(latPart, lngPart, audioPart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(requireContext(), "SOS envoyé avec succès", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Échec d'envoi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        getLastLocation();
    }

    private void updateMapLocation(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(userLocation).title("Vous êtes ici"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
    }
}
