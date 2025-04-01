package org.samadesan.servicios;

import org.samadesan.entidades.*;
import org.samadesan.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private CasillaRepository casillaRepository;

    // Asignar un personaje a una casilla
    public Personaje asignarPersonajeACasilla(Integer personajeId, Integer casillaId) {
        Optional<Personaje> personajeOpt = personajeRepository.findById(personajeId);
        Optional<Casilla> casillaOpt = casillaRepository.findById(casillaId);

        if (personajeOpt.isPresent() && casillaOpt.isPresent()) {
            Personaje personaje = personajeOpt.get();
            Casilla casilla = casillaOpt.get();

            // Añadir la casilla a la lista de casillas del personaje
            personaje.getCasilla().add(casilla);

            // Guardar los cambios en la base de datos
            return personajeRepository.save(personaje);
        }
        return null;
    }
    /**
     * Listar todos los personajes.
     *
     * @return Lista de personajes
     */
    public List<Personaje> listarTodos() {
        return personajeRepository.findAll();
    }

    /**
     * Guardar o actualizar un personaje.
     * Si el personaje tiene un ID, se actualizará. Si no, se creará uno nuevo.
     *
     * @param personaje El personaje a guardar
     * @return El personaje guardado
     */
    public Personaje guardar(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    /**
     * Obtener un personaje por su ID.
     *
     * @param id El ID del personaje
     * @return El personaje si existe, en caso contrario, un Optional vacío
     */
    public Optional<Personaje> obtenerPorId(Integer id) {
        return personajeRepository.findById(id);
    }

    /**
     * Eliminar un personaje por su ID.
     *
     * @param id El ID del personaje a eliminar
     */
    public void eliminar(Integer id) {
        personajeRepository.deleteById(id);
    }

    /**
     * Actualizar un personaje.
     *
     * @param personaje El personaje a actualizar
     * @return El personaje actualizado
     */
    public Personaje actualizar(Personaje personaje) {
        return personajeRepository.save(personaje);
    }
}
