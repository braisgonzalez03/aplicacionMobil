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

import com.example.appclubtenis.Adapter.TournamentAdapter;
import com.example.appclubtenis.Helper.ConfigDAO;
import com.example.appclubtenis.Helper.ConfigModel;
import com.example.appclubtenis.Helper.LanguageLocale;
import com.example.appclubtenis.Model.Tournaments;
import com.example.appclubtenis.Preferences.AppPreferences;
import com.example.appclubtenis.R;
import com.example.appclubtenis.Service.TournamentService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TournamentActivity extends AppCompatActivity {

    private ListView listViewTournaments;
    private TournamentService tournamentService;
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
        setContentView(R.layout.activity_tournament);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        listViewTournaments = findViewById(R.id.listViewTournament);
        configDAO = new ConfigDAO(this);

        loadApiService();

        AppPreferences prefs = new AppPreferences(this);
        int playerId = prefs.getPlayerId();
        if (prefs.isAdmin()) {
            fetchTournaments();
        } else {
            fetchTournamentsForPlayer(playerId);
        }

    }

    private void loadApiService() {
        ConfigModel config = configDAO.getConfig();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tournamentService = retrofit.create(TournamentService.class);
    }

    private void fetchTournaments() {
        AppPreferences preferences = new AppPreferences(this);
        if (preferences.isAdmin()) {
            tournamentService.getAllTournaments().enqueue(new Callback<List<Tournaments>>() {
                @Override
                public void onResponse(Call<List<Tournaments>> call, Response<List<Tournaments>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Tournaments> tournaments = response.body();
                        TournamentAdapter adapter = new TournamentAdapter(TournamentActivity.this, tournaments);
                        listViewTournaments.setAdapter(adapter);
                    } else {
                        Toast.makeText(TournamentActivity.this, "No se pudo obtener la lista", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Tournaments>> call, Throwable t) {
                    Toast.makeText(TournamentActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private void fetchTournamentsForPlayer(int playerId) {
        tournamentService.getTournamentsByPlayer(playerId).enqueue(new Callback<List<Tournaments>>() {
            @Override
            public void onResponse(Call<List<Tournaments>> call, Response<List<Tournaments>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Tournaments> tournaments = response.body();
                    TournamentAdapter adapter = new TournamentAdapter(TournamentActivity.this, tournaments);
                    listViewTournaments.setAdapter(adapter);
                } else {
                    Toast.makeText(TournamentActivity.this, "No se pudo obtener la lista", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tournaments>> call, Throwable t) {
                Toast.makeText(TournamentActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}