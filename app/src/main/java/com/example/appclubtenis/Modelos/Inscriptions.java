package com.example.appclubtenis.Modelos;

public class Inscriptions {

    private Long incripcionId;
    private Players players;
    private Tournaments tournaments;

    public Inscriptions() {
    }

    public Inscriptions(Long incripcionId, Players players, Tournaments tournaments) {
        this.incripcionId = incripcionId;
        this.players = players;
        this.tournaments = tournaments;
    }

    public Long getIncripcionId() {
        return incripcionId;
    }

    public void setIncripcionId(Long incripcionId) {
        this.incripcionId = incripcionId;
    }

    public Players getJugadores() {
        return players;
    }

    public void setJugadores(Players players) {
        this.players = players;
    }

    public Tournaments getTorneos() {
        return tournaments;
    }

    public void setTorneos(Tournaments tournaments) {
        this.tournaments = tournaments;
    }

    @Override
    public String toString() {
        return "Inscriptions{" +
                "incripcionId=" + incripcionId +
                ", players=" + players +
                ", tournaments=" + tournaments +
                '}';
    }
}
