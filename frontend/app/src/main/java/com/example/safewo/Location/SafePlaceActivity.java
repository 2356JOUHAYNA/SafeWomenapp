package com.example.safewo.Location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.safewo.R;
import com.example.safewo.api.ApiClient;
import com.example.safewo.api.ApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SafePlaceActivity extends AppCompatActivity {

    private TextView tvLocation;
    private Button btnMarkSafe;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude, longitude;
    private ApiService apiService;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_place);

        tvLocation = findViewById(R.id.tv_location);
        btnMarkSafe = findViewById(R.id.btn_mark_safe);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        apiService = ApiClient.getAPIService(); // No token required

        getLocation();

        btnMarkSafe.setOnClickListener(v -> sendSafePlace());
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                tvLocation.setText("Lat: " + latitude + " | Lng: " + longitude);
            } else {
                tvLocation.setText("Position indisponible");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation(); // Relancer la localisation si permission accordée
            } else {
                Toast.makeText(this, "Permission localisation refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendSafePlace() {
        Map<String, Object> request = new HashMap<>();
        request.put("latitude", latitude);
        request.put("longitude", longitude);

        apiService.addSafePlace(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SafePlaceActivity.this, "✅ Lieu ajouté avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SafePlaceActivity.this, "❌ Erreur serveur : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SafePlaceActivity.this, "❌ Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
