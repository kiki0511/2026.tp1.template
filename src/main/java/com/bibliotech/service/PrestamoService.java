package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;

public interface PrestamoService {
    void realizarPrestamo(String isbn, int socioId) throws BibliotecaException;
    void devolverLibro(String isbn, int socioId) throws BibliotecaException;
}