package com.example.appclubtenis.Model;

import com.google.gson.annotations.SerializedName;

public class Tournaments {
    @SerializedName("tournamentId")
    private int tournamentId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("classification")
    private String classification;


    public Tournaments() {
    }


    public Tournaments(int tournamentId, String name, String description, String classification) {
        this.tournamentId = tournamentId;
        this.name = name;
        this.description = description;
        this.classification = classification;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
