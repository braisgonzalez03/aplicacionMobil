package com.example.appclubtenis.Service;

import com.example.appclubtenis.Model.Tournaments;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TournamentService {
    // Todos los tournaments
    @GET("tournaments")
    Call<List<Tournaments>> getAllTournaments();
}
