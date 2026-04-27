package com.bibliotech.service;

import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Transaccion;

import java.util.List;

public class HistorialService {

    private final PrestamoServiceImpl prestamoService;

    public HistorialService(PrestamoServiceImpl prestamoService) {
        this.prestamoService = prestamoService;
    }

    public void mostrarHistorialPrestamos() {
        List<Prestamo> prestamos = prestamoService.getHistorial();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
            return;
        }
        System.out.println("\n=== HISTORIAL DE PRÉSTAMOS ===");
        for (Prestamo p : prestamos) {
            System.out.println("ID: " + p.id()
                    + " | Libro: " + p.recurso().titulo()
                    + " | Socio: " + p.socio().getNombre()
                    + " | Fecha: " + p.fechaPrestamo()
                    + " | Devuelto: " + (p.devuelto() ? "Sí" : "No"));
        }
    }

    public void mostrarHistorialTransacciones() {
        List<Transaccion> transacciones = prestamoService.getTransacciones();
        if (transacciones.isEmpty()) {
            System.out.println("No hay devoluciones registradas.");
            return;
        }
        System.out.println("\n=== HISTORIAL DE DEVOLUCIONES ===");
        for (Transaccion t : transacciones) {
            System.out.println("ID: " + t.id()
                    + " | Libro: " + t.prestamo().recurso().titulo()
                    + " | Socio: " + t.prestamo().socio().getNombre()
                    + " | Fecha devolución: " + t.fechaDevolucion()
                    + " | Días de retraso: " + t.diasRetraso());
        }
    }
}