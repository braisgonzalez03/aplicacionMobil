package com.example.appclubtenis.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConfigDAO {

    private DBHelper dbHelper;

    public ConfigDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public void updateConfig(String url, String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_URL, url);
        values.put(DBHelper.COLUMN_USERNAME, username);
        db.update(DBHelper.TABLE_CONFIG, values, DBHelper.COLUMN_ID + " = ?", new String[]{"1"});
    }

    public ConfigModel getConfig() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_CONFIG, null, DBHelper.COLUMN_ID + " = ?", new String[]{"1"}, null, null, null);
        ConfigModel config = new ConfigModel();
        if (cursor.moveToFirst()) {
            config.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_URL)));
            config.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USERNAME)));
        }
        cursor.close();
        return config;
    }
}
