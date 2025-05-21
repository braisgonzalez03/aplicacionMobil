package com.example.appclubtenis.Service;

import com.example.appclubtenis.Model.Tournaments;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TournamentService {
    // Todos los tournaments
    @GET("tournaments")
    Call<List<Tournaments>> getAllTournaments();

    @GET("players/{id}")
    Call<Tournaments> getTournamentById(@Path("id") int id);

    @GET("tournaments/player/{playerId}")
    Call<List<Tournaments>> getTournamentsByPlayer(@Path("playerId") int playerId);
}
