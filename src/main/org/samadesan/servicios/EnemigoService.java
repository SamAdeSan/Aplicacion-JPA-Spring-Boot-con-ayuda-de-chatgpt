package org.samadesan.servicios;

import org.samadesan.entidades.Enemigo;
import org.samadesan.repositorios.EnemigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnemigoService {

    @Autowired
    private EnemigoRepository enemigoRepository;

    // Crear un nuevo esbirro enemigo
    public Enemigo crearEsbirroEnemigo(Enemigo esbirroEnemigo) {
        return enemigoRepository.save(esbirroEnemigo);
    }

    // Obtener todos los esbirros enemigos
    public List<Enemigo> obtenerTodosLosEsbirrosEnemigos() {
        return enemigoRepository.findAll();
    }

    // Obtener un esbirro enemigo por ID
    public Optional<Enemigo> obtenerEsbirroEnemigoPorId(Integer id) {
        return enemigoRepository.findById(id);
    }

    // Actualizar un esbirro enemigo
    public Enemigo actualizarEsbirroEnemigo(Enemigo esbirroEnemigo) {
        if (enemigoRepository.existsById(esbirroEnemigo.getId())) {
            return enemigoRepository.save(esbirroEnemigo);
        }
        return null;
    }

    // Eliminar un esbirro enemigo
    public void eliminarEsbirroEnemigo(Integer id) {
        enemigoRepository.deleteById(id);
    }
}
