package com.bibliotech.service;

import com.bibliotech.exception.LibroNoDisponibleException;
import com.bibliotech.exception.LimitePrestamosException;
import com.bibliotech.exception.SocioNoEncontradoException;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.LibroRepository;
import com.bibliotech.repository.SocioRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoServiceImpl implements PrestamoService {

    private final LibroRepository libroRepository;
    private final SocioRepository socioRepository;
    private final List<Prestamo> prestamos = new ArrayList<>();
    private int contadorId = 1;

    public PrestamoServiceImpl(LibroRepository libroRepository, SocioRepository socioRepository) {
        this.libroRepository = libroRepository;
        this.socioRepository = socioRepository;
    }

    @Override
    public void realizarPrestamo(String isbn, int socioId) {
        Recurso recurso = libroRepository.buscarPorId(isbn)
                .orElseThrow(() -> new LibroNoDisponibleException(isbn));

        Socio socio = socioRepository.buscarPorId(socioId)
                .orElseThrow(() -> new SocioNoEncontradoException(socioId));

        long prestamosActivos = prestamos.stream()
                .filter(p -> p.socio().getId() == socioId && !p.devuelto())
                .count();

        if (prestamosActivos >= socio.getMaximoLibros()) {
            throw new LimitePrestamosException(socio.getNombre(), socio.getMaximoLibros());
        }

        boolean libroEnPrestamo = prestamos.stream()
                .anyMatch(p -> p.recurso().isbn().equals(isbn) && !p.devuelto());

        if (libroEnPrestamo) {
            throw new LibroNoDisponibleException(isbn);
        }

        Prestamo prestamo = new Prestamo(
                contadorId++,
                recurso,
                socio,
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                false
        );

        prestamos.add(prestamo);
        System.out.println("Préstamo realizado: " + socio.getNombre() + " - " + recurso.titulo());
    }

    @Override
    public void devolverLibro(String isbn, int socioId) {
        Prestamo prestamo = prestamos.stream()
                .filter(p -> p.recurso().isbn().equals(isbn)
                        && p.socio().getId() == socioId
                        && !p.devuelto())
                .findFirst()
                .orElseThrow(() -> new LibroNoDisponibleException(isbn));

        int index = prestamos.indexOf(prestamo);
        prestamos.set(index, new Prestamo(
                prestamo.id(),
                prestamo.recurso(),
                prestamo.socio(),
                prestamo.fechaPrestamo(),
                prestamo.fechaDevolucionEsperada(),
                true
        ));

        long diasRetraso = LocalDate.now().isAfter(prestamo.fechaDevolucionEsperada())
                ? java.time.temporal.ChronoUnit.DAYS.between(prestamo.fechaDevolucionEsperada(), LocalDate.now())
                : 0;

        if (diasRetraso > 0) {
            System.out.println("Devolución con " + diasRetraso + " días de retraso.");
        } else {
            System.out.println("Devolución exitosa: " + prestamo.recurso().titulo());
        }
    }

    public List<Prestamo> getHistorial() {
        return new ArrayList<>(prestamos);
    }
}
