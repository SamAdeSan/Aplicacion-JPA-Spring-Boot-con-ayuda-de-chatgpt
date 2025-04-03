package org.samadesan.repositorios;

import org.samadesan.entidades.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {
    // Aquí agregamos consultas personalizadas si es necesario
    Nivel findTopByOrderByIdDesc();  // Esto obtiene el último nivel registrado
}