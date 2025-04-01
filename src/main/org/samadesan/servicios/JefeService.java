package org.samadesan.servicios;

import org.samadesan.entidades.Jefe;
import org.samadesan.repositorios.JefeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JefeService {

    @Autowired
    private JefeRepository jefeRepository;

    // Crear un nuevo jefe
    public Jefe crearJefe(Jefe jefe) {
        return jefeRepository.save(jefe);
    }

    // Obtener todos los jefes
    public List<Jefe> obtenerTodosLosJefes() {
        return jefeRepository.findAll();
    }

    // Obtener un jefe por ID
    public Optional<Jefe> obtenerJefePorId(Integer id) {
        return jefeRepository.findById(id);
    }

    // Actualizar un jefe
    public Jefe actualizarJefe(Jefe jefe) {
        if (jefeRepository.existsById(jefe.getId())) {
            return jefeRepository.save(jefe);
        }
        return null;
    }

    // Eliminar un jefe
    public void eliminarJefe(Integer id) {
        jefeRepository.deleteById(id);
    }
}
