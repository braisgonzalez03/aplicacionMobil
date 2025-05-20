package com.example.appclubtenis.Service;

import com.example.appclubtenis.Model.Inscriptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InscriptionService {
    // Todas las inscriptions
    @GET("inscriptions")
    Call<List<Inscriptions>> getAllInscriptions();
}
