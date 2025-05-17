package com.example.appclubtenis.SQLite.Contract;

import android.provider.BaseColumns;

public class PreferencesContract {

    private PreferencesContract(){}

    public static class AppTenis implements BaseColumns{
        public static final String TABLE_NAME = "Tenis";
        public static final String COLUMN_USERNAME = "user_name";
        public static final String COLUMN_THEME = "theme";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_PASSWORD = "password";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppTenis.TABLE_NAME + " (" +
                    AppTenis._ID + " INTEGER PRIMARY KEY," +
                    AppTenis.COLUMN_USERNAME + " TEXT," +
                    AppTenis.COLUMN_THEME + " TEXT," +
                    AppTenis.COLUMN_LANGUAGE + " TEXT," +
                    AppTenis.COLUMN_PASSWORD + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppTenis.TABLE_NAME;
}
