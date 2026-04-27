package com.bibliotech.model;

public abstract class Socio {
    private final int id;
    private final String nombre;
    private final String dni;
    private final String email;

    public Socio(int id, String nombre, String dni, String email) {
        validarEmail(email);
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
    }

    private void validarEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getEmail() { return email; }

    public abstract int getMaximoLibros();
}