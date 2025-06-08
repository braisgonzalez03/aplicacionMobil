package com.example.appclubtenis.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfigDAO {

    private DBHelper dbHelper;

    public ConfigDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public void updateConfig(String url, String username, String password, String image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_URL, url);
        values.put(DBHelper.COLUMN_USERNAME, username);
        values.put(DBHelper.COLUMN_PASSWORD, password);
        values.put(DBHelper.COLUMN_IMAGE, image);
        db.insert(DBHelper.TABLE_CONFIG, null, values);
    }

    public List<ConfigModel> getAllConfigs() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DBHelper.COLUMN_URL,
                DBHelper.COLUMN_USERNAME,
                DBHelper.COLUMN_PASSWORD,
                DBHelper.COLUMN_IMAGE
        };

        Cursor cursor = db.query(
                DBHelper.TABLE_CONFIG,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<ConfigModel> configList = new ArrayList<>();

        while (cursor.moveToNext()) {
            ConfigModel config = new ConfigModel();

            config.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_URL)));
            config.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USERNAME)));
            config.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PASSWORD)));;
            config.setImagen(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_IMAGE)));
            configList.add(config);
        }

        cursor.close();
        db.close();

        return configList;
    }



}
