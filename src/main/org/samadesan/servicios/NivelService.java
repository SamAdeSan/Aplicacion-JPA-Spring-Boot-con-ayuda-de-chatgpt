package org.samadesan.servicios;

import org.samadesan.entidades.Nivel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NivelService {

    @Autowired
    private org.samadesan.repositorios.NivelRepository nivelRepository;

    // Crear un nuevo nivel
    public Nivel crearNivel(Nivel nivel) {
        return nivelRepository.save(nivel);
    }

    // Obtener todos los niveles
    public List<Nivel> obtenerTodosLosNiveles() {
        return nivelRepository.findAll();
    }

    // Obtener un nivel por ID
    public Nivel obtenerNivelPorId(Integer nivelId) {
        Optional<Nivel> nivelOpt = nivelRepository.findById(nivelId);
        return nivelOpt.orElse(null); // Devuelve null si no existe el nivel
    }

    // Actualizar un nivel existente
    public Nivel actualizarNivel(Integer nivelId, Nivel nuevoNivel) {
        Optional<Nivel> nivelOpt = nivelRepository.findById(nivelId);
        if (nivelOpt.isPresent()) {
            Nivel nivel = nivelOpt.get();
            nivel.setNombre(nuevoNivel.getNombre());
            nivel.setTipo(nuevoNivel.getTipo());
            nivel.setCasillas(nuevoNivel.getCasillas()); // Si es necesario, actualizar las casillas
            return nivelRepository.save(nivel);
        }
        return null;
    }

    // Eliminar un nivel
    public boolean eliminarNivel(Integer nivelId) {
        Optional<Nivel> nivelOpt = nivelRepository.findById(nivelId);
        if (nivelOpt.isPresent()) {
            nivelRepository.delete(nivelOpt.get());
            return true;
        }
        return false;
    }
}
