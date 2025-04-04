package org.samadesan;

import org.samadesan.repositorios.EnemigoRepository;
import org.samadesan.repositorios.JefeRepository;
import org.samadesan.repositorios.PersonajeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BombyApplication implements CommandLineRunner {
    private final PersonajeRepository personajeRepository;
    private final EnemigoRepository enemigoRepository;
    private final JefeRepository jefeRepository;
    private Juego juego;
    public BombyApplication(PersonajeRepository personajeRepository, EnemigoRepository enemigoRepository, JefeRepository jefeRepository) {
        this.personajeRepository = personajeRepository;
        this.enemigoRepository = enemigoRepository;
        this.jefeRepository = jefeRepository;
        juego = new Juego(personajeRepository, enemigoRepository, jefeRepository);
    }
    public static void main(String[] args) {
        SpringApplication.run(BombyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        juego.iniciarJuego();
    }
}
