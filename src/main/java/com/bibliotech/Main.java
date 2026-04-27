package com.bibliotech;

import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;
import com.bibliotech.exception.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    static LibroRepository libroRepository = new LibroRepositoryImpl();
    static SocioRepository socioRepository = new SocioRepositoryImpl();
    static PrestamoServiceImpl prestamoService = new PrestamoServiceImpl(libroRepository, socioRepository);
    static HistorialService historialService = new HistorialService(prestamoService);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean ejecutando = true;
        while (ejecutando) {
            System.out.println("\n=== BiblioTech ===");
            System.out.println("1. Registrar libro fisico");
            System.out.println("2. Registrar ebook");
            System.out.println("3. Registrar socio estudiante");
            System.out.println("4. Registrar socio docente");
            System.out.println("5. Realizar prestamo");
            System.out.println("6. Devolver libro");
            System.out.println("7. Buscar libros");
            System.out.println("8. Ver historial");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> registrarLibroFisico();
                case 2 -> registrarEbook();
                case 3 -> registrarEstudiante();
                case 4 -> registrarDocente();
                case 5 -> realizarPrestamo();
                case 6 -> devolverLibro();
                case 7 -> buscarLibros();
                case 8 -> historialService.mostrarHistorialTransacciones();
                case 0 -> ejecutando = false;
                default -> System.out.println("Opcion invalida.");
            }
        }
        System.out.println("Hasta luego!");
    }

    static void registrarLibroFisico() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine());
        System.out.print("Categoría (FICCION, CIENCIA, HISTORIA, TECNOLOGIA, ARTE, OTRO): ");
        Categoria categoria = Categoria.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Número de páginas: ");
        int paginas = Integer.parseInt(scanner.nextLine());
        libroRepository.guardar(new LibroFisico(isbn, titulo, autor, anio, categoria, paginas));
        System.out.println("Libro registrado correctamente.");
    }

    static void registrarEbook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine());
        System.out.print("Categoría (FICCION, CIENCIA, HISTORIA, TECNOLOGIA, ARTE, OTRO): ");
        Categoria categoria = Categoria.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Formato (PDF, EPUB, MOBI): ");
        String formato = scanner.nextLine();
        libroRepository.guardar(new Ebook(isbn, titulo, autor, anio, categoria, formato));
        System.out.println("Ebook registrado correctamente.");
    }

    static void registrarEstudiante() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        try {
            socioRepository.guardar(new Estudiante(id, nombre, dni, email));
            System.out.println("Estudiante registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void registrarDocente() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        try {
            socioRepository.guardar(new Docente(id, nombre, dni, email));
            System.out.println("Docente registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void realizarPrestamo() {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("ID del socio: ");
        int socioId = Integer.parseInt(scanner.nextLine());
        try {
            prestamoService.realizarPrestamo(isbn, socioId);
        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void devolverLibro() {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();
        System.out.print("ID del socio: ");
        int socioId = Integer.parseInt(scanner.nextLine());
        try {
            prestamoService.devolverLibro(isbn, socioId);
        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void buscarLibros() {
        System.out.println("Buscar por: 1. Título  2. Autor  3. Categoría");
        System.out.print("Opcion: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        List<Recurso> resultados;
        switch (opcion) {
            case 1 -> {
                System.out.print("Título: ");
                resultados = libroRepository.buscarPorTitulo(scanner.nextLine());
            }
            case 2 -> {
                System.out.print("Autor: ");
                resultados = libroRepository.buscarPorAutor(scanner.nextLine());
            }
            case 3 -> {
                System.out.print("Categoría (FICCION, CIENCIA, HISTORIA, TECNOLOGIA, ARTE, OTRO): ");
                resultados = libroRepository.buscarPorCategoria(Categoria.valueOf(scanner.nextLine().toUpperCase()));
            }
            default -> {
                System.out.println("Opcion invalida.");
                return;
            }
        }
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            resultados.forEach(r -> System.out.println("- " + r.titulo() + " | " + r.autor() + " | " + r.isbn()));
        }
    }
}