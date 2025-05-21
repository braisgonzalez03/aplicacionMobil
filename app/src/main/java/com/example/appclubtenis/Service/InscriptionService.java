package com.example.appclubtenis.Service;

import com.example.appclubtenis.Model.Inscriptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InscriptionService {
    // Todas las inscriptions
    @GET("inscriptions")
    Call<List<Inscriptions>> getAllInscriptions();

    @GET("inscriptions/byPlayer/{playerId}")
    Call<List<Inscriptions>> getInscriptionByPlayerId(@Path("playerId") int playerId);
}
