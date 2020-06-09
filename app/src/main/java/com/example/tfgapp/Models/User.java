package com.example.tfgapp.Models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    private int id;

    private String name;

    private String email;

    private int points;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPoints() {
        return points;
    }
}
