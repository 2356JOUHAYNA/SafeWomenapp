package com.example.safewo.util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    public static final int REQUEST_CODE_PERMISSIONS = 123;

    public static String[] getRequiredPermissions() {
        List<String> permissions = new ArrayList<>();

        // Localisation
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        // Audio
        permissions.add(Manifest.permission.RECORD_AUDIO);

        // Stockage
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        return permissions.toArray(new String[0]);
    }

    public static boolean checkPermissions(Context context) {
        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                getRequiredPermissions(),
                REQUEST_CODE_PERMISSIONS
        );
    }
}
