package org.samadesan.repositorios;

import org.samadesan.entidades.Enemigo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnemigoRepository extends JpaRepository<Enemigo, Integer> {
    // Métodos personalizados si son necesarios
    Enemigo getRandomEnemigo();
}
