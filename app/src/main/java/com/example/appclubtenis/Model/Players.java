package com.example.appclubtenis.Model;

import com.google.gson.annotations.SerializedName;

public class Players {

    @SerializedName("playerId")
    private Integer playerId;

    @SerializedName("name")
    private String name;

    @SerializedName("surnames")
    private String surnames;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private int phone;

    @SerializedName("dni")
    private String dni;

    @SerializedName("userName")
    private String userName;

    @SerializedName("password")
    private String password;


    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
