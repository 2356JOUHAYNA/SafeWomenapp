package com.example.safewo.Location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.safewo.R;
import com.example.safewo.api.ApiClient;
import com.example.safewo.api.ApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SafePlaceFragment extends Fragment implements OnMapReadyCallback {

    private TextView tvLocation;
    private Button btnMarkSafe;
    private FusedLocationProviderClient fusedLocationClient;
    private ApiService apiService;
    private double latitude, longitude;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safe_place, container, false);

        tvLocation = view.findViewById(R.id.tv_location);
        btnMarkSafe = view.findViewById(R.id.btn_mark_safe);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        apiService = ApiClient.getAPIService(); // No token required

        getLocation();

        btnMarkSafe.setOnClickListener(v -> sendSafePlace());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_container);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map_container, mapFragment)
                    .commit();
        }
        mapFragment.getMapAsync(this);

        return view;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                tvLocation.setText("Lat: " + latitude + " | Lng: " + longitude);
                updateMapLocation(latitude, longitude);
            } else {
                tvLocation.setText("Localisation : impossible d'obtenir la position");
            }
        });
    }

    private void updateMapLocation(double lat, double lng) {
        if (googleMap != null) {
            LatLng currentPosition = new LatLng(lat, lng);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(currentPosition).title("Position actuelle"));
            googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(currentPosition, 15));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        updateMapLocation(latitude, longitude);
    }

    private void sendSafePlace() {
        Map<String, Object> request = new HashMap<>();
        request.put("latitude", latitude);
        request.put("longitude", longitude);

        apiService.addSafePlace(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "✅ Lieu ajouté avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "❌ Erreur serveur : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "❌ Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
