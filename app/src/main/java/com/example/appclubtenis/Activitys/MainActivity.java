package com.example.appclubtenis.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appclubtenis.Preferences.AppPreferences;
import com.example.appclubtenis.R;

public class MainActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private Button btnPlayers, btnTournaments, btnInscriptions, btnSettings, btnLogout;
    private AppPreferences appPreferences;
    private ImageView userImageView;

    private final int[] imageResIds = {
            R.raw.tenista1,
            R.raw.tenista2,
            R.raw.tenista3,
            R.raw.tenista4,
            R.raw.tenista5,
            R.raw.tenista6,
            R.raw.tenista7,
            R.raw.tenista8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        appPreferences = new AppPreferences(this);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        btnPlayers = findViewById(R.id.btnPlayers);
        btnTournaments = findViewById(R.id.btnTournaments);
        btnInscriptions = findViewById(R.id.btnInscriptions);
        btnSettings = findViewById(R.id.btnSettings);
        btnLogout = findViewById(R.id.btnLogout);

        String username = appPreferences.getUsername();
        if (username != null) {
            welcomeTextView.setText(getString(R.string.welcome) + username);
        } else {
            welcomeTextView.setText(getString(R.string.welcome)+" invitado");
        }
        int selectedPosition = appPreferences.getSelectedImagePosition();
        if (selectedPosition != -1 && selectedPosition < imageResIds.length) {

            userImageView = findViewById(R.id.imageViewPlayer);
            if (userImageView != null) {
                userImageView.setImageResource(imageResIds[selectedPosition]);
            }
        }

        if (!appPreferences.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        if (appPreferences.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        btnPlayers.setOnClickListener(v -> {
            Intent player = new Intent(this, PlayersActivity.class);
            startActivity(player);
            });

        btnTournaments.setOnClickListener(v -> {
            Intent tournament = new Intent(this, TournamentActivity.class);
            startActivity(tournament);
            });

        btnInscriptions.setOnClickListener(v -> {
            Intent inscription = new Intent(this, InscriptionsActivity.class);
            startActivity(inscription);
             });

        btnSettings.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            appPreferences.setLoggedIn(false);
            appPreferences.clearCredentials();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}