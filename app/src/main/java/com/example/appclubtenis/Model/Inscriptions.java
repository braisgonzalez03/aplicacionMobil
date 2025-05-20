package com.example.appclubtenis.Model;

import com.google.gson.annotations.SerializedName;

public class Inscriptions {

    @SerializedName("inscriptionId")
    private int inscriptionId;

    @SerializedName("player")
    private Players player;

    @SerializedName("tournament")
    private Tournaments tournament;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    public Inscriptions() {
    }

    public Inscriptions(int inscriptionId, Players player, Tournaments tournament, String startDate, String endDate) {
        this.inscriptionId = inscriptionId;
        this.player = player;
        this.tournament = tournament;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(int inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Players getPlayer() {
        return player;
    }

    public void setPlayer(Players player) {
        this.player = player;
    }

    public Tournaments getTournament() {
        return tournament;
    }

    public void setTournament(Tournaments tournament) {
        this.tournament = tournament;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
