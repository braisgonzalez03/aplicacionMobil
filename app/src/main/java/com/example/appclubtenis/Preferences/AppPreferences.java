package com.example.appclubtenis.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AppPreferences {

    private static final String PREFERENCES_NAME = "app_preferences";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_THEME = "theme";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_PLAYER_ID = "player_id";
    private static final String KEY_IS_ADMIN = "is_admin";

    private SharedPreferences preferences;
    private SharedPreferences encryptedPrefs;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            encryptedPrefs = EncryptedSharedPreferences.create(
                    context,
                    PREFERENCES_NAME + "_encrypted",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            encryptedPrefs = preferences;
        }
    }

    // Idioma y tema sin cifrar
    public void setLanguage(String language) {
        preferences.edit().putString(KEY_LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return preferences.getString(KEY_LANGUAGE, "es");
    }

    public void setTheme(String theme) {
        preferences.edit().putString(KEY_THEME, theme).apply();
    }

    public String getTheme() {
        return preferences.getString(KEY_THEME, "light");
    }

    public boolean isDarkMode() {
        return "dark".equalsIgnoreCase(getTheme());
    }

    public void setUsername(String username) {
        preferences.edit().putString(KEY_USERNAME, username).apply();
    }

    public String getUsername() {
        return preferences.getString(KEY_USERNAME, null);
    }

    public void setPassword(String password) {
        encryptedPrefs.edit().putString(KEY_PASSWORD, password).apply();
    }

    public String getPassword() {
        return encryptedPrefs.getString(KEY_PASSWORD, null);
    }

    public void clearCredentials() {
        preferences.edit().remove(KEY_USERNAME).apply();
        encryptedPrefs.edit().remove(KEY_PASSWORD).apply();
    }

    public void setLoggedIn(boolean loggedIn) {
        preferences.edit().putBoolean(KEY_LOGGED_IN, loggedIn).apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public void setPlayerId(int playerId) {
        preferences.edit().putInt(KEY_PLAYER_ID, playerId).apply();
    }

    public int getPlayerId() {
        return preferences.getInt(KEY_PLAYER_ID, -1);
    }

    public void setIsAdmin(boolean isAdmin) {
        preferences.edit().putBoolean(KEY_IS_ADMIN, isAdmin).apply();
    }

    public boolean isAdmin() {
        return preferences.getBoolean(KEY_IS_ADMIN, false);
    }
}
