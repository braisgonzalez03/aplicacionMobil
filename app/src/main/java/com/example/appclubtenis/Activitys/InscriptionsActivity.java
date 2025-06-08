package com.example.appclubtenis.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appclubtenis.Adapter.InscriptionAdapter;
import com.example.appclubtenis.Adapter.TournamentAdapter;
import com.example.appclubtenis.Helper.ConfigDAO;
import com.example.appclubtenis.Helper.ConfigModel;
import com.example.appclubtenis.Helper.LanguageLocale;
import com.example.appclubtenis.Model.Inscriptions;
import com.example.appclubtenis.Model.Tournaments;
import com.example.appclubtenis.Preferences.AppPreferences;
import com.example.appclubtenis.R;
import com.example.appclubtenis.Service.InscriptionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InscriptionsActivity extends AppCompatActivity {

    private ListView listViewInscriptions;
    private InscriptionService inscriptionService;
    private ConfigDAO configDAO;
    private ImageButton btnCerrar;

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
        setContentView(R.layout.activity_inscriptions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCerrar = findViewById(R.id.btnCerrar);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(InscriptionsActivity.this, MainActivity.class);
                startActivity(main);
            }
        });


        AppPreferences preferences = new AppPreferences(this);

        listViewInscriptions = findViewById(R.id.listViewInscriptions);
        configDAO = new ConfigDAO(this);

        loadApiService();


        int playerId = preferences.getPlayerId();
        if(preferences.isAdmin()){
            fetchInscriptions();
        }else{
            fetchTournamentsForPlayer(playerId);
        }

    }

    private void loadApiService() {
        List<ConfigModel> configList = configDAO.getAllConfigs();

        if (!configList.isEmpty()) {
            ConfigModel config = configList.get(0);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(config.getUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            inscriptionService = retrofit.create(InscriptionService.class);
        } else {
            Toast.makeText(this, getString(R.string.no_hay_configuraciones_guardadas), Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchInscriptions() {
        AppPreferences preferences = new AppPreferences(this);
        if (preferences.isAdmin()) {
            inscriptionService.getAllInscriptions().enqueue(new Callback<List<Inscriptions>>() {
                @Override
                public void onResponse(Call<List<Inscriptions>> call, Response<List<Inscriptions>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Inscriptions> inscriptions = response.body();
                        InscriptionAdapter adapter = new InscriptionAdapter(InscriptionsActivity.this, inscriptions);
                        listViewInscriptions.setAdapter(adapter);
                    } else {
                        Toast.makeText(InscriptionsActivity.this, getString(R.string.no_se_pudo_obtener_la_lista), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Inscriptions>> call, Throwable t) {
                    Toast.makeText(InscriptionsActivity.this, getString(R.string.error_de_conexi_n), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void fetchTournamentsForPlayer(int playerId) {
        inscriptionService.getInscriptionByPlayerId(playerId).enqueue(new Callback<List<Inscriptions>>() {

            @Override
            public void onResponse(Call<List<Inscriptions>> call, Response<List<Inscriptions>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Inscriptions> inscriptions = response.body();
                    InscriptionAdapter adapter = new InscriptionAdapter(InscriptionsActivity.this, inscriptions);
                    listViewInscriptions.setAdapter(adapter);
                } else {
                    Toast.makeText(InscriptionsActivity.this, getString(R.string.no_se_pudo_obtener_la_lista), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inscriptions>> call, Throwable t) {
                Toast.makeText(InscriptionsActivity.this, getString(R.string.error_de_conexi_n), Toast.LENGTH_SHORT).show();
            }
        });
    }
}