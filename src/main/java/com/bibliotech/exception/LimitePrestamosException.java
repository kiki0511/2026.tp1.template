package com.bibliotech.exception;

public class LimitePrestamosException extends BibliotecaException {
    public LimitePrestamosException(String nombreSocio, int limite) {
        super("El socio " + nombreSocio + " alcanzó el límite de " + limite + " préstamos.");
    }
}