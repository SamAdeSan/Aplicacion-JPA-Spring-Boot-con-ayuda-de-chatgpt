package org.samadesan.servicios;

import org.samadesan.entidades.Batalla;
import org.samadesan.repositorios.BatallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatallaService {

    @Autowired
    private BatallaRepository batallaRepository;

    // Crear una nueva batalla
    public Batalla crearBatalla(Batalla batalla) {
        return batallaRepository.save(batalla);
    }

    // Obtener todas las batallas
    public List<Batalla> obtenerTodasLasBatallas() {
        return batallaRepository.findAll();
    }

    // Obtener una batalla por ID
    public Optional<Batalla> obtenerBatallaPorId(Integer id) {
        return batallaRepository.findById(id);
    }

    // Actualizar una batalla
    public Batalla actualizarBatalla(Batalla batalla) {
        if (batallaRepository.existsById(batalla.getId())) {
            return batallaRepository.save(batalla);
        }
        return null;
    }

    // Eliminar una batalla
    public void eliminarBatalla(Integer id) {
        batallaRepository.deleteById(id);
    }
}
