package com.bibliotech.model;

import java.time.LocalDate;

public record Prestamo(
        int id,
        Recurso recurso,
        Socio socio,
        LocalDate fechaPrestamo,
        LocalDate fechaDevolucionEsperada,
        boolean devuelto
) {}