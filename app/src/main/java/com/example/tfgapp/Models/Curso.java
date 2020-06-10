package com.example.tfgapp.Models;

import java.io.Serializable;

public class Curso implements Serializable {
    private String name;
    private String description;
    private String level;
    //Pregunta pregunta[];

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }
}
