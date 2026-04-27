package com.bibliotech.exception;

public class SocioNoEncontradoException extends RuntimeException {
    public SocioNoEncontradoException(int id) {
        super("El socio con ID " + id + " no fue encontrado.");
    }
}