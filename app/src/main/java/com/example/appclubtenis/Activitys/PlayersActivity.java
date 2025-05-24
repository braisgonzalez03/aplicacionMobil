package com.example.appclubtenis.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appclubtenis.Adapter.PlayersAdapter;
import com.example.appclubtenis.Helper.ConfigDAO;
import com.example.appclubtenis.Helper.ConfigModel;
import com.example.appclubtenis.Helper.LanguageLocale;
import com.example.appclubtenis.Model.Players;
import com.example.appclubtenis.Preferences.AppPreferences;
import com.example.appclubtenis.R;
import com.example.appclubtenis.Service.PlayerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayersActivity extends AppCompatActivity {

    private ListView listViewPlayers;
    private PlayerService playerService;
    private ConfigDAO configDAO;

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
        setContentView(R.layout.activity_players);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listViewPlayers = findViewById(R.id.listViewPlayers);
        configDAO = new ConfigDAO(this);
        loadApiService();
        fetchPlayers();
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
        } else {
            Toast.makeText(this, "No hay configuración guardada", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPlayers() {
        AppPreferences preferences = new AppPreferences(this);
        if (preferences.isAdmin()) {
            playerService.getAllPlayers().enqueue(new Callback<List<Players>>() {
                @Override
                public void onResponse(Call<List<Players>> call, Response<List<Players>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Players> playersList = response.body();


                        List<String> imageNames = getImageAssetNames();

                        PlayersAdapter adapter = new PlayersAdapter(PlayersActivity.this, playersList,imageNames);
                        listViewPlayers.setAdapter(adapter);
                    } else {
                        Toast.makeText(PlayersActivity.this, "No se pudo obtener la lista", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Players>> call, Throwable t) {
                    Toast.makeText(PlayersActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            int playerId = preferences.getPlayerId();
            playerService.getPlayerById(playerId).enqueue(new Callback<Players>() {
                @Override
                public void onResponse(Call<Players> call, Response<Players> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Players player = response.body();

                        List<String> imageNames = getImageAssetNames();

                        listViewPlayers.setAdapter(new PlayersAdapter(PlayersActivity.this, List.of(player),imageNames));
                    } else {
                        Toast.makeText(PlayersActivity.this, "No se pudo obtener tu información", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Players> call, Throwable t) {
                    Toast.makeText(PlayersActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private List<String> getImageAssetNames() {
        List<String> imageNames = new ArrayList<String>();
        try {
            String[] files = getAssets().list("");
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    String file = files[i];
                    if (file.endsWith(".png") || file.endsWith(".jpg") || file.endsWith(".jpeg")) {
                        imageNames.add(file);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageNames;
    }


}