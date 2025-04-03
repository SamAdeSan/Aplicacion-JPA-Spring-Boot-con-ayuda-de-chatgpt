package org.samadesan.repositorios;

import org.samadesan.entidades.Batalla;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatallaRepository extends JpaRepository<Batalla, Integer> {
    // Métodos personalizados si son necesarios
}
