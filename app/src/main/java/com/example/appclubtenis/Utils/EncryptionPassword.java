package com.example.appclubtenis.Utils;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionPassword {

    private static final String AES_MODE = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 16;
    private static final String SECRET_KEY = "1234567890abcdef";

    public static String encrypt(String input) {
        try {
            byte[] iv = new byte[12];
            Cipher cipher = Cipher.getInstance(AES_MODE);
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
            byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedBase64) {
        try {
            byte[] iv = new byte[12];
            Cipher cipher = Cipher.getInstance(AES_MODE);
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
            byte[] decoded = Base64.decode(encryptedBase64, Base64.NO_WRAP);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
