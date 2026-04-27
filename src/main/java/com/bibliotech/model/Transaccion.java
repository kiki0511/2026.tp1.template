package com.bibliotech.model;

import java.time.LocalDate;

public record Transaccion(
        int id,
        Prestamo prestamo,
        LocalDate fechaDevolucion,
        long diasRetraso
) {}