package com.example.appclubtenis.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String PREFERENCES_NAME = "app_preferences";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_THEME = "theme";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_PLAYER_ID = "player_id";
    private static final String KEY_IS_ADMIN = "is_admin";
    private static final String KEY_SELECTED_IMAGE_POS = "selected_image_position";
    private static final String KEY_SERVER_URL = "server_url";

    private SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

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
        preferences.edit().putString(KEY_PASSWORD, password).apply();
    }

    public String getPassword() {
        return preferences.getString(KEY_PASSWORD, null);
    }

    public void clearCredentials() {
        preferences.edit().remove(KEY_USERNAME).remove(KEY_PASSWORD).apply();
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

    public void setSelectedImagePosition(int position) {
        preferences.edit().putInt(KEY_SELECTED_IMAGE_POS, position).apply();
    }

    public int getSelectedImagePosition() {
        return preferences.getInt(KEY_SELECTED_IMAGE_POS, -1);
    }
    public void setServerUrl(String url) {
        preferences.edit().putString(KEY_SERVER_URL, url).apply();
    }

    public String getServerUrl() {
        return preferences.getString(KEY_SERVER_URL, null);
    }
}
