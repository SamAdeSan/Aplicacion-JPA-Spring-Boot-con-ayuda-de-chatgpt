package org.samadesan;

import org.samadesan.entidades.Personaje;
import org.samadesan.servicios.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class BombyApplication implements CommandLineRunner {

    @Autowired
    private PersonajeService personajeServicio;

    public static void main(String[] args) {
        SpringApplication.run(BombyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Juego juego = new Juego();
        juego.iniciar();
    }
}
