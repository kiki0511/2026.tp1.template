package com.bibliotech.repository;

import com.bibliotech.model.Socio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SocioRepositoryImpl implements SocioRepository {

    private final Map<Integer, Socio> socios = new HashMap<>();

    @Override
    public void guardar(Socio socio) {
        socios.put(socio.getId(), socio);
    }

    @Override
    public Optional<Socio> buscarPorId(Integer id) {
        return Optional.ofNullable(socios.get(id));
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(socios.values());
    }

    @Override
    public Optional<Socio> buscarPorDni(String dni) {
        return socios.values().stream()
                .filter(s -> s.getDni().equals(dni))
                .findFirst();
    }

    @Override
    public Optional<Socio> buscarPorEmail(String email) {
        return socios.values().stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst();
    }
}