package com.example.tfgapp.Models;

import java.io.Serializable;

public class Completados implements Serializable {

    private String usuario;
    private String curso;
    private int score;



    public String getUsuario() { return usuario; }

    public String getCurso() {
        return curso;
    }

    public int getScore() {
        return score;
    }
}
