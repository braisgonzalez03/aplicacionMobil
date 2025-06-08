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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appclubtenis.Adapter.CustomSpinnerAdapter;
import com.example.appclubtenis.Adapter.ImageAdapter;
import com.example.appclubtenis.Helper.ConfigDAO;


import com.example.appclubtenis.Helper.LanguageLocale;

import com.example.appclubtenis.Preferences.AppPreferences;

import com.example.appclubtenis.R;

import com.example.appclubtenis.Utils.EncryptionPassword;
import com.example.appclubtenis.Utils.SpinnerItem;

import java.util.ArrayList;
import java.util.List;


public class SettingActivity extends AppCompatActivity {


    private EditText urlEditText, usernameEditText, passwordEditText;

    private Spinner languageSpinner, themeSpinner;

    private ImageButton saveButton;

    private RecyclerView recyclerViewImages;

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

        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        int[] imageResIds = new int[] {
                R.raw.tenista1,
                R.raw.tenista2,
                R.raw.tenista3,
                R.raw.tenista4,
                R.raw.tenista5,
                R.raw.tenista6,
                R.raw.tenista7,
                R.raw.tenista8
        };

        configDAO = new ConfigDAO(this);

        appPreferences = new AppPreferences(this);


        ImageAdapter adapter = new ImageAdapter(this, imageResIds);
        recyclerViewImages.setAdapter(adapter);

        int savedPosition = appPreferences.getSelectedImagePosition();
        if (savedPosition != -1) {
            adapter.setSelectedPosition(savedPosition);
        }

        adapter.setOnItemClickListener(position -> {
            appPreferences.setSelectedImagePosition(position);
        });


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

        List<SpinnerItem> languageItems = new ArrayList<>();

        languageItems.add(new SpinnerItem("Español", R.drawable.es, "es"));
        languageItems.add(new SpinnerItem("Galego", R.drawable.gl, "gl"));

        CustomSpinnerAdapter languageAdapter = new CustomSpinnerAdapter(this, languageItems);
        languageSpinner.setAdapter(languageAdapter);


        String[] themes = {"light", "dark"};

        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, themes);

        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        themeSpinner.setAdapter(themeAdapter);

    }


    private void loadSettings() {

        String savedUrl = appPreferences.getServerUrl();
        String savedUsername = appPreferences.getUsername();
        String savedEncryptedPassword = appPreferences.getPassword();

        if (savedUrl != null && !savedUrl.isEmpty()) {
            urlEditText.setText(savedUrl);
        } else {
            urlEditText.setText("http://10.0.2.2:8080/");
            usernameEditText.setText("");
            passwordEditText.setText("");
        }

        if (savedUsername != null && !savedUsername.isEmpty()) {
            usernameEditText.setText(savedUsername);
        }

        if (savedEncryptedPassword != null && !savedEncryptedPassword.isEmpty()) {
            try {
                String decryptedPassword = EncryptionPassword.decrypt(savedEncryptedPassword);
                passwordEditText.setText(decryptedPassword);
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.error_al_cargar_la_contrase_a), Toast.LENGTH_LONG).show();
                passwordEditText.setText("");
                e.printStackTrace();
            }
        } else {
            passwordEditText.setText("");
        }

        String languageToSelect = appPreferences.getLanguage();
        CustomSpinnerAdapter languageAdapter = (CustomSpinnerAdapter) languageSpinner.getAdapter();
        int langPos = 0;

        if (languageAdapter != null) {
            for (int i = 0; i < languageAdapter.getCount(); i++) {
                SpinnerItem item = languageAdapter.getItem(i);
                if (item != null && item.getLanguageCode().equals(languageToSelect)) {
                    langPos = i;
                    break;
                }
            }
        }
        languageSpinner.setSelection(langPos);


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

        SpinnerItem selectedLanguageItem = (SpinnerItem) languageSpinner.getSelectedItem();
        String newLanguage = selectedLanguageItem != null ? selectedLanguageItem.getLanguageCode() : "";

        String newTheme = themeSpinner.getSelectedItem().toString();

        int selectedImagePosition = appPreferences.getSelectedImagePosition();

        String selectedImageName = null;
        ImageAdapter adapter = (ImageAdapter) recyclerViewImages.getAdapter();
        if (adapter != null && selectedImagePosition != RecyclerView.NO_POSITION) {
            selectedImageName = adapter.getResourceName(selectedImagePosition);
        }
        String encryptedImageName = null;
        if (selectedImageName != null && !selectedImageName.isEmpty()) {
            try {
                encryptedImageName = EncryptionPassword.encrypt(selectedImageName);
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.error_al_encriptar_el_nombre_de_la_imagen), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        if (url.isEmpty()) {
            Toast.makeText(this, getString(R.string.la_url_no_puede_estar_vac_a), Toast.LENGTH_SHORT).show();
            return;
        }

        String encryptedPassword = "";
        if (!password.isEmpty()) {
            encryptedPassword = EncryptionPassword.encrypt(password);
            Toast.makeText(this, getString(R.string.password_encrypted_message), Toast.LENGTH_SHORT).show();
        } else {
            encryptedPassword = "";
            Toast.makeText(this, getString(R.string.password_empty_message), Toast.LENGTH_SHORT).show();
        }

        String oldLanguage = appPreferences.getLanguage();
        String oldTheme = appPreferences.getTheme();

        configDAO.updateConfig(url, username, encryptedPassword,encryptedImageName);

        appPreferences.setUsername(username);
        appPreferences.setPassword(encryptedPassword);

        appPreferences.setLanguage(newLanguage);
        appPreferences.setTheme(newTheme);

        boolean languageChanged = !oldLanguage.equals(newLanguage);
        boolean themeChanged = !oldTheme.equals(newTheme);

        Toast.makeText(this, getString(R.string.configuraci_n_guardada), Toast.LENGTH_SHORT).show();

        if (languageChanged) {
            LanguageLocale.setLocale(this, newLanguage);
        }

        Intent loginIntent = new Intent(SettingActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
        finish();
    }

}