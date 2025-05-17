package com.example.appclubtenis.SQLite.Preferences;

public class AppPreferences {

    private int id;
    private String userName;
    private String theme;
    private String language;
    private String password;

    public AppPreferences() {
    }

    public AppPreferences(int id, String userName, String theme, String language, String password) {
        this.id = id;
        this.userName = userName;
        this.theme = theme;
        this.language = language;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
