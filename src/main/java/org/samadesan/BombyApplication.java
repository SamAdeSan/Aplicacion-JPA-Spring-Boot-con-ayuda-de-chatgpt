package org.samadesan;

import org.samadesan.entidades.Personaje;
import org.samadesan.servicios.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BombyApplication implements CommandLineRunner {

    @Autowired
    private PersonajeService personajeServicio;

    public static void main(String[] args) {
        SpringApplication.run(BombyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Aquí puedes llamar a los métodos del servicio para interactuar con la base de datos.
        Personaje personaje = new Personaje();
        personaje.setNombre("Bomby");
        personaje.setAtaque(100);
        personaje.setVida(200);
        personaje.setDefensa(50);
        personajeServicio.guardar(personaje);

        System.out.println("Personaje guardado: " + personaje.getNombre());
    }
}
