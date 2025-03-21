package org.samadesan.servicios;

import org.samadesan.entidades.*;
import org.samadesan.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CasillaService {

    @Autowired
    private CasillaRepository casillaRepository;

    @Autowired
    private EnemigoRepository enemigoRepository;

    // Asignar enemigos a una casilla
    public Casilla asignarEnemigosACasilla(Integer casillaId, List<Integer> enemigosIds) {
        Optional<Casilla> casillaOpt = casillaRepository.findById(casillaId);

        if (casillaOpt.isPresent()) {
            Casilla casilla = casillaOpt.get();

            // Obtenemos los enemigos por sus IDs
            List<Enemigo> enemigos = enemigoRepository.findAllById(enemigosIds);

            // Asignamos los enemigos a la casilla
            casilla.setEnemigos(enemigos);

            // Guardamos los cambios en la base de datos
            return casillaRepository.save(casilla);
        }
        return null;
    }
}
