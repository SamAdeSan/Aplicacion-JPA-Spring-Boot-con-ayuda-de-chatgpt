package org.samadesan.repositorios;

import org.samadesan.entidades.Jefe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JefeRepository extends JpaRepository<Jefe, Integer> {
    // Métodos personalizados si son necesarios
}
