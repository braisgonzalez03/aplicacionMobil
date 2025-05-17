package com.example.appclubtenis.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appclubtenis.Modelos.Players;
import com.example.appclubtenis.R;
import com.example.appclubtenis.Requests.LoginRequest;
import com.example.appclubtenis.SQLite.Helper.PreferencesHelper;
import com.example.appclubtenis.SQLite.Preferences.AppPreferences;
import com.example.appclubtenis.Service.ApiPlayersService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText etUserName, etPassword;
    private Button btnLogin;

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



        etUserName = findViewById(R.id.etnombreUsuario);
        etPassword = findViewById(R.id.etcontraseña);
        btnLogin = findViewById(R.id.btnLogin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiPlayersService apiPlayersService = retrofit.create(ApiPlayersService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                LoginRequest loginRequest = new LoginRequest(name, password);

                Call<Map<String, String>> call = apiPlayersService.login(loginRequest);


                call.enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String message = response.body().get("message");
                            String userIdStr = response.body().get("player_id"); // asegúrate de que el backend lo devuelve


                            int userId = userIdStr != null ? Integer.parseInt(userIdStr) : 0;

                            PreferencesHelper manager = new PreferencesHelper(LoginActivity.this);
                            String theme = PreferencesHelper.getCurrentTheme(getApplicationContext());
                            String language = PreferencesHelper.getCurrentLanguage(getApplicationContext());

                            AppPreferences preferences = new AppPreferences(userId, name, theme, language, password);
                            manager.savePreferences(preferences);

                            Toast.makeText(LoginActivity.this, "Éxito: " + message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}