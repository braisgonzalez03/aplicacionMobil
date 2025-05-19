package com.example.appclubtenis.Service;

import com.example.appclubtenis.Model.Players;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlayerService {

    // Login por usuario y contraseña
    @POST("players/login")
    Call<Players> login(@Body Players player);

    // Obtener todos los jugadores
    @GET("players")
    Call<List<Players>> getAllPlayers();

    // Obtener un jugador por ID
    @GET("players/{id}")
    Call<Players> getPlayerById(@Path("id") int id);

    // Crear nuevo jugador
    @POST("players")
    Call<Players> createPlayer(@Body Players player);

    // Buscar jugador por username (opcional)
    @GET("players/search")
    Call<Players> getPlayerByUsername(@Query("name") String userName);
}
