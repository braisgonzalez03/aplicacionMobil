package com.example.appclubtenis.SQLite.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appclubtenis.SQLite.Contract.PreferencesContract;
import com.example.appclubtenis.SQLite.Preferences.AppPreferences;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Locale;

public class PreferencesHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "app_preferences.db";

    public PreferencesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PreferencesContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(PreferencesContract.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public void savePreferences(AppPreferences preferences) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Encriptar la contraseña antes de guardarla
        String encryptedPassword = BCrypt.hashpw(preferences.getPassword(), BCrypt.gensalt());

        ContentValues values = new ContentValues();
        values.put(PreferencesContract.AppTenis._ID, preferences.getId());
        values.put(PreferencesContract.AppTenis.COLUMN_USERNAME, preferences.getUserName());
        values.put(PreferencesContract.AppTenis.COLUMN_THEME, preferences.getTheme());
        values.put(PreferencesContract.AppTenis.COLUMN_LANGUAGE, preferences.getLanguage());
        values.put(PreferencesContract.AppTenis.COLUMN_PASSWORD, encryptedPassword);  // Almacenando la contraseña encriptada

        long rowId = db.insertWithOnConflict(PreferencesContract.AppTenis.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        if (rowId == -1) {
            System.out.println("Error al guardar las preferencias");
        }
    }

    public AppPreferences getUserPreferences(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                PreferencesContract.AppTenis._ID,
                PreferencesContract.AppTenis.COLUMN_USERNAME,
                PreferencesContract.AppTenis.COLUMN_THEME,
                PreferencesContract.AppTenis.COLUMN_LANGUAGE,
                PreferencesContract.AppTenis.COLUMN_PASSWORD
        };

        String selection = PreferencesContract.AppTenis.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { userName };

        Cursor cursor = db.query(
                PreferencesContract.AppTenis.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null, null, null
        );

        AppPreferences preferences = null;
        if (cursor != null && cursor.moveToFirst()) {
            preferences = new AppPreferences(
                    cursor.getInt(cursor.getColumnIndexOrThrow(PreferencesContract.AppTenis._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PreferencesContract.AppTenis.COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PreferencesContract.AppTenis.COLUMN_THEME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PreferencesContract.AppTenis.COLUMN_LANGUAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PreferencesContract.AppTenis.COLUMN_PASSWORD))
            );
            cursor.close();
        }

        return preferences;
    }

    public static String getCurrentLanguage(Context context) {
        return Locale.getDefault().getLanguage();
    }

    public static String getCurrentTheme(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) ? "dark" : "light";
    }

}
