package com.example.appclubtenis.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.appclubtenis.Helper.ConfigDAO;
import com.example.appclubtenis.Helper.ConfigModel;
import com.example.appclubtenis.Helper.LanguageLocale;
import com.example.appclubtenis.Model.Players;
import com.example.appclubtenis.Preferences.AppPreferences;
import com.example.appclubtenis.R;
import com.example.appclubtenis.Service.PlayerService;
import com.example.appclubtenis.Utils.EncryptionPassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, configButton;
    private PlayerService playerService;
    private ConfigDAO configDAO;
    private AppPreferences appPreferences;


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
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });





        appPreferences = new AppPreferences(this);

        if (appPreferences.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        configButton = findViewById(R.id.buttonConfig);

        configDAO = new ConfigDAO(this);
        loadApiService();

        String savedUser = appPreferences.getUsername();
        String savedPass = appPreferences.getPassword();
        if(savedUser != null) {
            usernameEditText.setText(savedUser);
        }
        if(savedPass != null) {
            String decryptedPassword = EncryptionPassword.decrypt(savedPass);
            passwordEditText.setText(decryptedPassword);
        }

        loginButton.setOnClickListener(v -> loginUser());
        configButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }

    private void loadApiService() {
        List<ConfigModel> configList = configDAO.getAllConfigs();

        if (!configList.isEmpty()) {
            ConfigModel config = configList.get(0);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(config.getUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            playerService = retrofit.create(PlayerService.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadApiService();
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.introduce_nombre_y_contrase_a), Toast.LENGTH_SHORT).show();
            return;
        }
        if (playerService == null) {
            Toast.makeText(this, getString(R.string.tienes_que_guardar_una_configuracion_primero), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
            startActivity(intent);
            return;
        }
        if(username.equals("admin") && password.equals("admin123")) {
            appPreferences.setUsername(username);
            appPreferences.setPassword(password);
            appPreferences.setLoggedIn(true);
            appPreferences.setIsAdmin(true);
            appPreferences.setPlayerId(-1);

            Toast.makeText(this, getString(R.string.bienvenido_admin), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;


        }


        Players player = new Players();
        player.setUserName(username);
        player.setPassword(password);

        playerService.login(player).enqueue(new Callback<Players>() {
            @Override
            public void onResponse(Call<Players> call, Response<Players> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Players player = response.body();
                    Toast.makeText(LoginActivity.this, getString(R.string.bienvenido) + player.getName(), Toast.LENGTH_SHORT).show();

                    appPreferences.setUsername(username);
                    appPreferences.setPassword(password);
                    appPreferences.setLoggedIn(true);
                    appPreferences.setIsAdmin(false);
                    appPreferences.setPlayerId(player.getPlayerId());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.credenciales_incorrectas), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Players> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getString(R.string.error_de_conexi_n), Toast.LENGTH_SHORT).show();
            }
        });
    }



}