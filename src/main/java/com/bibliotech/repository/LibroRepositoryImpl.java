package com.bibliotech.repository;

import com.bibliotech.model.Categoria;
import com.bibliotech.model.Recurso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LibroRepositoryImpl implements LibroRepository {

    private final Map<String, Recurso> libros = new HashMap<>();

    @Override
    public void guardar(Recurso recurso) {
        libros.put(recurso.isbn(), recurso);
    }

    @Override
    public Optional<Recurso> buscarPorId(String isbn) {
        return Optional.ofNullable(libros.get(isbn));
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(libros.values());
    }

    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        return libros.values().stream()
                .filter(r -> r.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        return libros.values().stream()
                .filter(r -> r.autor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        return libros.values().stream()
                .filter(r -> r.categoria().equals(categoria))
                .toList();
    }
}