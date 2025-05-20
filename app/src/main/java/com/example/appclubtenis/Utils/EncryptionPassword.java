package com.example.appclubtenis.Utils;

import android.util.Base64;

public class EncryptionPassword {

    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        if (input == null || input.isEmpty()) return "";
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return new String(decodedBytes);
    }
}
