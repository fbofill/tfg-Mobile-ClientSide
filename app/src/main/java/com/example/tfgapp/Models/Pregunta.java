package com.example.tfgapp.Models;

import java.io.Serializable;

public class Pregunta implements Serializable {
    private String enunciado;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;

    public String getEnunciado() {
        return enunciado;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }
}
