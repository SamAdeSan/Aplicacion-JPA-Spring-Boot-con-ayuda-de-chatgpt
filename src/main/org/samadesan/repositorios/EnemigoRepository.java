package org.samadesan.repositorios;

import org.samadesan.entidades.Enemigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnemigoRepository extends JpaRepository<Enemigo, Integer> {
    // Métodos personalizados si son necesarios
    @Query("SELECT e FROM Enemigo e ORDER BY RANDOM() LIMIT 1")
    Enemigo getRandomEnemigo();
}
