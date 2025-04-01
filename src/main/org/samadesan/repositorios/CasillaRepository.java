package org.samadesan.repositorios;

import org.samadesan.entidades.Casilla;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasillaRepository extends JpaRepository<Casilla, Integer> {
    // Métodos personalizados si son necesarios
}
