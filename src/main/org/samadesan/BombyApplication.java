package org.samadesan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BombyApplication implements CommandLineRunner {

    private Juego juego;

    public static void main(String[] args) {
        SpringApplication.run(BombyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        juego.iniciarJuego();
    }
}
