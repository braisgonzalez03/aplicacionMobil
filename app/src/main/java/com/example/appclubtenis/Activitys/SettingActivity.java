package com.example.appclubtenis.Activitys;


import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.widget.ArrayAdapter;

import android.widget.EditText;

import android.widget.ImageButton;

import android.widget.Spinner;

import android.widget.Toast;


import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;

import androidx.core.view.ViewCompat;

import androidx.core.view.WindowInsetsCompat;


import com.example.appclubtenis.Helper.ConfigDAO;


import com.example.appclubtenis.Helper.LanguageLocale;

import com.example.appclubtenis.Preferences.AppPreferences;

import com.example.appclubtenis.R;

import com.example.appclubtenis.Utils.EncryptionPassword;


public class SettingActivity extends AppCompatActivity {


    private EditText urlEditText, usernameEditText, passwordEditText;

    private Spinner languageSpinner, themeSpinner;

    private ImageButton saveButton;


    private ConfigDAO configDAO;

    private AppPreferences appPreferences;

    public static final String RESULT_LANGUAGE_CHANGED = "language_changed";

    public static final String RESULT_THEME_CHANGED = "theme_changed";


    @Override

    protected void attachBaseContext(Context newBase) {

        AppPreferences prefs = new AppPreferences(newBase);

        String language = prefs.getLanguage();

        super.attachBaseContext(LanguageLocale.setLocale(newBase, language));

    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_setting);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;


        });

        configDAO = new ConfigDAO(this);

        appPreferences = new AppPreferences(this);


        urlEditText = findViewById(R.id.editTextUrl);

        usernameEditText = findViewById(R.id.editTextUsername);

        passwordEditText = findViewById(R.id.editTextPassword);

        languageSpinner = findViewById(R.id.spinnerLanguage);

        themeSpinner = findViewById(R.id.spinnerTheme);

        saveButton = findViewById(R.id.buttonSaveSettings);


        setupSpinners();

        loadSettings();


        saveButton.setOnClickListener(v -> saveSettings());

    }


    private void setupSpinners() {

        String[] languages = {"es", "gl"};

        ArrayAdapter<String> langAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);

        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languageSpinner.setAdapter(langAdapter);


        String[] themes = {"light", "dark"};

        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, themes);

        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        themeSpinner.setAdapter(themeAdapter);

    }


    private void loadSettings() {

        String savedUrl = appPreferences.getServerUrl();
        String password = passwordEditText.getText().toString().trim();

        if (savedUrl != null && !savedUrl.isEmpty()) {

            urlEditText.setText(savedUrl);

        } else {

            urlEditText.setText("http://10.0.2.2:8080/");
            usernameEditText.setText("");
            passwordEditText.setText("");

        }


        String savedUsername = appPreferences.getUsername();



        if (savedUsername != null && !savedUsername.isEmpty()) {

            usernameEditText.setText(savedUsername);

        }

        String encryptedPassword = "";
        if (!password.isEmpty()) {
            encryptedPassword = EncryptionPassword.encrypt(password);
            Toast.makeText(this, getString(R.string.password_encrypted_message), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.password_empty_message), Toast.LENGTH_SHORT).show();
        }


        String language = appPreferences.getLanguage();

        ArrayAdapter languageAdapter = (ArrayAdapter) languageSpinner.getAdapter();

        int langPos = languageAdapter.getPosition(language);

        if (langPos >= 0) {

            languageSpinner.setSelection(langPos);

        } else {

            languageSpinner.setSelection(0);

        }


        String theme = appPreferences.getTheme();

        ArrayAdapter themeAdapter = (ArrayAdapter) themeSpinner.getAdapter();

        int themePos = themeAdapter.getPosition(theme);

        if (themePos >= 0) {

            themeSpinner.setSelection(themePos);

        } else {

            themeSpinner.setSelection(0);

        }

    }


    private void saveSettings() {

        String url = urlEditText.getText().toString().trim();

        String username = usernameEditText.getText().toString().trim();

        String password = passwordEditText.getText().toString().trim();

        String language = languageSpinner.getSelectedItem().toString();

        String theme = themeSpinner.getSelectedItem().toString();


        if (url.isEmpty()) {

            Toast.makeText(this, "La URL no puede estar vacía", Toast.LENGTH_SHORT).show();

            return;

        }


        String encryptedPassword = EncryptionPassword.encrypt(password);

        String oldLanguage = appPreferences.getLanguage();

        String oldTheme = appPreferences.getTheme();


        configDAO.updateConfig(url, username, encryptedPassword);


        appPreferences.setUsername(username);

        appPreferences.setPassword(password);


        appPreferences.setLanguage(language);

        appPreferences.setTheme(theme);


        Intent resultIntent = new Intent();

        boolean languageChanged = !oldLanguage.equals(language);

        boolean themeChanged = !oldTheme.equals(theme);


        resultIntent.putExtra(RESULT_LANGUAGE_CHANGED, languageChanged);

        resultIntent.putExtra(RESULT_THEME_CHANGED, themeChanged);

        setResult(RESULT_OK, resultIntent);

        finish();


        Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show();


    }

}