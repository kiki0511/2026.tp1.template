package com.bibliotech.model;

public class Estudiante extends Socio {

    public Estudiante(int id, String nombre, String dni, String email) {
        super(id, nombre, dni, email);
    }

    @Override
    public int getMaximoLibros() {
        return 3;
    }
}