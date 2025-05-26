package com.example.safewo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {


    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";String PREF_NAME = "SafeWoPreferences";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";


    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Set the login status
     */
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    /**
     * Check if the user is logged in
     */
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Save the username
     */
    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    /**
     * Get the username
     */
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "Guest");
    }

    public void saveAuthData(Long userId, String username, String email) {
        editor.putLong(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    /**
     * Clear all stored data (e.g., during logout)
     */
    public void clearData() {
        editor.clear();
        editor.apply();
    }
}
