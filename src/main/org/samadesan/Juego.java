package org.samadesan;

import org.samadesan.entidades.*;
import org.samadesan.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

@Service
public class Juego {
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();
    private int vidaPersonaje = 100;
    private int ataquePersonaje = 20;
    private int defensaPersonaje = 10;
    private int nivelActual;
    private int casillaActual = 1;
    private int totalCasillas = 5;

    @Autowired
    private NivelRepository nivelRepositorio;
    @Autowired
    private CasillaRepository casillaRepositorio;
    @Autowired
    private EnemigoRepository enemigoRepositorio;
    @Autowired
    private JefeRepository jefeRepositorio;

    public void iniciar() {
        System.out.println("\n¡Bienvenido al Mundo de Bomby!");
        cargarOGenerarNivel();
        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("\nEstás en el Nivel " + nivelActual + ", Casilla " + casillaActual);
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Avanzar a la siguiente casilla");
            System.out.println("2. Ver estadísticas");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    avanzarCasilla();
                    break;
                case 2:
                    verEstadisticas();
                    break;
                case 3:
                    ejecutando = false;
                    System.out.println("Saliendo del juego...");
                    break;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
            }
        }
    }

    private void cargarOGenerarNivel() {
        Optional<Nivel> nivelExistente = Optional.ofNullable(nivelRepositorio.findTopByOrderByIdDesc());
        if (nivelExistente.isPresent()) {
            nivelActual = nivelExistente.get().getId();
        } else {
            nivelActual = 1;
            generarDatosIniciales();
        }
    }

    private void generarDatosIniciales() {
        Nivel nivel = new Nivel();
        nivelRepositorio.save(nivel);

        for (int i = 1; i <= totalCasillas; i++) {
            Casilla casilla = new Casilla("Casilla " + i, nivel);
            casillaRepositorio.save(casilla);

            if (random.nextBoolean()) {
                Enemigo enemigo = new Enemigo("Enemigo " + i, random.nextInt(50) + 50, random.nextInt(20) + 10, random.nextInt(10) + 5);
                enemigoRepositorio.save(enemigo);
                casilla.getEnemigos().add(enemigo);
            } else if (random.nextInt(10) > 7) {
                Jefe jefe = new Jefe("Jefe " + i, 150, 30, 15);
                jefeRepositorio.save(jefe);
                casilla.setJefe(jefe);
            }
            casillaRepositorio.save(casilla);
        }
    }

    private void avanzarCasilla() {
        System.out.println("Has avanzado a la siguiente casilla...");
        int evento = random.nextInt(4);

        if (evento == 0) {
            System.out.println("La casilla está vacía. No hay nada interesante aquí.");
        } else if (evento == 1) {
            System.out.println("¡Un enemigo ha aparecido!");
            combatir();
        } else if (evento == 2) {
            System.out.println("¡Has encontrado un cofre con una poción de vida!");
            vidaPersonaje += 20;
            System.out.println("Tu vida ha aumentado a " + vidaPersonaje);
        } else {
            System.out.println("¡Un jefe te desafía!");
            combatirJefe();
        }

        if (++casillaActual > totalCasillas) {
            System.out.println("¡Has completado el Nivel " + nivelActual + "!");
            nivelActual++;
            casillaActual = 1;
            totalCasillas += 2;
            generarDatosIniciales();
        }
    }

    private void combatir() {
        System.out.println("Te enfrentas a un enemigo...");
        int danoCausado = ataquePersonaje - random.nextInt(5);
        System.out.println("Has causado " + danoCausado + " de daño al enemigo.");
    }

    private void combatirJefe() {
        System.out.println("Te enfrentas a un jefe poderoso...");
        int danoCausado = ataquePersonaje - random.nextInt(10);
        System.out.println("Has causado " + danoCausado + " de daño al jefe.");
    }

    private void verEstadisticas() {
        System.out.println("\n=== Estadísticas del Personaje ===");
        System.out.println("Vida: " + vidaPersonaje);
        System.out.println("Ataque: " + ataquePersonaje);
        System.out.println("Defensa: " + defensaPersonaje);
        System.out.println("Nivel actual: " + nivelActual);
    }
}