package org.samadesan.repositorios;

import org.samadesan.entidades.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}