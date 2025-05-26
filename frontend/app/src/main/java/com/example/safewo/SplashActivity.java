package com.example.safewo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.safewo.ui.auth.autH.LoginActivity;
import com.example.safewo.util.PreferenceManager;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 secondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::checkAuth, SPLASH_DELAY);
    }

    private void checkAuth() {
        PreferenceManager preferenceManager = new PreferenceManager(this);

        if (preferenceManager.isLoggedIn()) {
            // L'utilisateur est déjà connecté, aller à l'écran principal
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            // L'utilisateur n'est pas connecté, aller à l'écran de connexion
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

        finish();
    }
}
