package com.bibliotech.exception;

public class LibroNoDisponibleException extends RuntimeException {
    public LibroNoDisponibleException(String isbn) {
        super("El libro con ISBN " + isbn + " no está disponible.");
    }
}