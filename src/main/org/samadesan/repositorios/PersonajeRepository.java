package org.samadesan.repositorios;

import org.samadesan.entidades.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {
    Personaje findByNombre(String nombre);
}
