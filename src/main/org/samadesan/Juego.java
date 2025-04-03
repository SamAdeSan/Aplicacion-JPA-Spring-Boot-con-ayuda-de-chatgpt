package org.samadesan;

import org.samadesan.entidades.Enemigo;
import org.samadesan.entidades.Jefe;
import org.samadesan.entidades.Personaje;
import org.samadesan.repositorios.EnemigoRepository;
import org.samadesan.repositorios.JefeRepository;
import org.samadesan.repositorios.PersonajeRepository;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Juego {
    private static final double RPG_PROB = 0.50;
    private static final double OBJETO_PROB = 0.25;
    private static final double LUCHA_PROB = 0.15;
    private static final double MINIJEF_PROB = 0.09;
    private static final double JEFE_PROB = 0.01;

    private final Scanner scanner;
    private Personaje personaje;
    private final PersonajeRepository personajeRepository;
    private final EnemigoRepository enemigoRepository;
    private final JefeRepository jefeRepository;

    // Constructor que recibe los repositorios y el escáner
    public Juego(PersonajeRepository personajeRepository, EnemigoRepository enemigoRepository,
                 JefeRepository jefeRepository) {
        this.scanner = new Scanner(System.in);
        this.personajeRepository = personajeRepository;
        this.enemigoRepository = enemigoRepository;
        this.jefeRepository = jefeRepository;
    }

    // Método para iniciar el juego
    public void iniciarJuego() {
        cargarPersonaje();

        while (true) {
            mostrarOpciones();
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    entrarEnCasilla();
                    break;
                case 2:
                    verEstadisticas();
                    break;
                case 3:
                    System.out.println("¡Gracias por jugar!");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Cargar o crear personaje
    private void cargarPersonaje() {
        System.out.print("Ingresa el nombre de tu personaje: ");
        String nombre = scanner.next();
        personaje = personajeRepository.findByNombre(nombre);

        if (personaje == null) {
            System.out.println("Personaje no encontrado. Creando uno nuevo...");
            personaje = new Personaje(nombre, 100, 10, 5); // Valores predeterminados
            personajeRepository.save(personaje);
        } else {
            System.out.println("Personaje cargado: " + personaje.getNombre());
        }
    }

    // Mostrar opciones al jugador
    private void mostrarOpciones() {
        System.out.println("\n¿Qué deseas hacer?");
        System.out.println("1. Entrar en una casilla");
        System.out.println("2. Ver estadísticas del personaje");
        System.out.println("3. Salir");
    }

    // Ver las estadísticas del personaje
    private void verEstadisticas() {
        System.out.println("\nEstadísticas de " + personaje.getNombre());
        System.out.println("Vida: " + personaje.getVida());
        System.out.println("Ataque: " + personaje.getAtaque());
        System.out.println("Defensa: " + personaje.getDefensa());
    }

    // Método para entrar en una casilla
    private void entrarEnCasilla() {
        Random rand = new Random();
        double probabilidad = rand.nextDouble();

        if (probabilidad <= RPG_PROB) {
            procesarCasillaRPG();
        } else if (probabilidad <= RPG_PROB + OBJETO_PROB) {
            procesarCasillaObjeto();
        } else if (probabilidad <= RPG_PROB + OBJETO_PROB + LUCHA_PROB) {
            procesarCasillaLucha();
        } else if (probabilidad <= RPG_PROB + OBJETO_PROB + LUCHA_PROB + MINIJEF_PROB) {
            procesarCasillaMiniJefe();
        } else {
            procesarCasillaJefe();
        }
    }

    // Lógica para procesar casilla de tipo RPG
    private void procesarCasillaRPG() {
        System.out.println("¡Has entrado en una casilla RPG!");
        // Crear enemigo aleatorio
        Enemigo enemigo = enemigoRepository.getRandomEnemigo(); // Obtener un enemigo aleatorio de la base de datos
        System.out.println("Te enfrentas a: " + enemigo.getNombre());

        // Lógica de combate a turnos
        while (personaje.getVida() > 0 && enemigo.getVida() > 0) {
            // Turno del jugador
            System.out.println("\nEs tu turno!");
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Atacar");
            System.out.println("2. Huir");
            System.out.println("3. Ver estadísticas actuales.");
            int accionJugador = scanner.nextInt();

            if (accionJugador == 1) {
                // El jugador ataca
                int danoJugador = calcularDano(personaje.getAtaque(), enemigo.getDefensa());
                enemigo.setVida(enemigo.getVida() - danoJugador);
                System.out.println("¡Has infligido " + danoJugador + " de daño a " + enemigo.getNombre() + "!");

                if (enemigo.getVida() <= 0) {
                    System.out.println(enemigo.getNombre() + " ha sido derrotado!");
                    break;
                }
            } else if (accionJugador == 2) {
                // El jugador huye
                System.out.println("Has decidido huir. Fin del combate.");
                break;
            } else if (accionJugador == 3) {
                // Mostrar estadísticas actuales
                System.out.println("\nTus estadísticas:");
                System.out.println("Vida: " + personaje.getVida() + " | Ataque: " + personaje.getAtaque() + " | Defensa: " + personaje.getDefensa());
                System.out.println("\nEstadísticas del enemigo (" + enemigo.getNombre() + "):");
                System.out.println("Vida: " + enemigo.getVida() + " | Ataque: " + enemigo.getAtaque() + " | Defensa: " + enemigo.getDefensa());
            } else {
                System.out.println("Acción no válida. Elige 1 para atacar o 2 para huir.");
                continue;
            }

            // Turno del enemigo
            System.out.println("\nEs el turno de " + enemigo.getNombre());
            int danoEnemigo = calcularDano(enemigo.getAtaque(), personaje.getDefensa());
            personaje.setVida(personaje.getVida() - danoEnemigo);
            System.out.println(enemigo.getNombre() + " te ha infligido " + danoEnemigo + " de daño!");

            if (personaje.getVida() <= 0) {
                System.out.println("¡Has sido derrotado por " + enemigo.getNombre() + "!");
                break;
            }
        }

        // Resultado del combate
        if (personaje.getVida() > 0) {
            System.out.println("¡Has ganado el combate!");
            // Aquí puedes agregar experiencia o recompensas al jugador
        } else {
            System.out.println("¡Has perdido el combate!");
            // Aquí puedes hacer lo que quieras cuando el jugador pierde (como restablecer su vida, etc.)
        }
    }
    // Método para calcular el daño, basándonos en ataque del atacante y defensa del defensor
    private int calcularDano(int ataque, int defensa) {
        int dano = ataque - defensa;
        if (dano < 0) dano = 0;  // El daño no puede ser negativo
        return dano;
    }

    // Lógica para procesar casilla de tipo Objeto
    private void procesarCasillaObjeto() {
        System.out.println("¡Has entrado en una casilla de objeto!");

        // Generar aleatoriamente qué tipo de mejora se obtiene
        Random random = new Random();
        int tipoMejora = random.nextInt(3); // 0 = ataque, 1 = vida, 2 = defensa
        int mejora = random.nextInt(6) + 5; // La mejora será entre 5 y 10 puntos

        if (tipoMejora == 0) {
            personaje.setAtaque(personaje.getAtaque() + mejora);
            System.out.println("¡Has encontrado un objeto que mejora tu ATAQUE en +" + mejora + " puntos!");
        } else if (tipoMejora == 1) {
            personaje.setVida(personaje.getVida() + mejora);
            System.out.println("¡Has encontrado un objeto que mejora tu VIDA en +" + mejora + " puntos!");
        } else {
            personaje.setDefensa(personaje.getDefensa() + mejora);
            System.out.println("¡Has encontrado un objeto que mejora tu DEFENSA en +" + mejora + " puntos!");
        }

        // Guardar los cambios en la base de datos
        personajeRepository.save(personaje);
        System.out.println("Tus nuevas estadísticas: Ataque " + personaje.getAtaque() + ", Vida " + personaje.getVida() + ", Defensa " + personaje.getDefensa());
    }

    // Lógica para procesar casilla de tipo Lucha
    private void procesarCasillaLucha() {
        System.out.println("¡Enfrentándote a un enemigo!");
        Enemigo enemigo = enemigoRepository.getRandomEnemigo(); // Método para obtener enemigo aleatorio
        lucharConEnemigo(enemigo);
    }

    // Lógica para procesar casilla de tipo MiniJefe
    private void procesarCasillaMiniJefe() {
        System.out.println("¡Te enfrentas a un Mini Jefe!");
        // Generamos un jefe aleatorio
        Jefe jefe = obtenerJefeAleatorio();
        // Creamos un mini jefe reduciendo las estadísticas del jefe
        Jefe miniJefe = new Jefe(
                jefe.getNombre(),
                jefe.getVida() - 30,  // Reducción de vida
                jefe.getAtaque() - 3,  // Reducción de ataque
                jefe.getDefensa() - 2, // Reducción de defensa
                jefe.getEfectoEspecial() // Efecto especial
        );
        lucharConJefe(miniJefe);
    }

    // Lógica para procesar casilla de tipo Jefe
    private void procesarCasillaJefe() {
        System.out.println("¡Te enfrentas a un Jefe!");
        Jefe jefe = obtenerJefeAleatorio(); // Obtener un jefe aleatorio
        lucharConJefe(jefe);
    }

    // Método para obtener un jefe aleatorio de la base de datos
    private Jefe obtenerJefeAleatorio() {
        // Obtener todos los jefes de la base de datos
        List<Jefe> jefes = jefeRepository.findAll();
        Random rand = new Random();
        return jefes.get(rand.nextInt(jefes.size())); // Seleccionar uno aleatorio
    }

    // Lógica para luchar con un enemigo (en este caso, con cualquier tipo)
    private void lucharConEnemigo(Enemigo enemigo) {
        System.out.println("¡Luchando contra: " + enemigo.getNombre());
        // Aquí iría la lógica de combate (comparar ataques, defensa, vida, etc.)
        if (personaje.getAtaque() > enemigo.getDefensa()) {
            System.out.println("¡Has ganado!");
            // Aquí puedes agregar experiencia o recompensas
        } else {
            System.out.println("¡Has perdido!");
            personaje.setVida(personaje.getVida() - enemigo.getAtaque());
        }
    }
    private void lucharConJefe(Jefe jefe) {
        System.out.println("¡Luchando contra: " + jefe.getNombre());
        // Aquí iría la lógica de combate (comparar ataques, defensa, vida, etc.)
        if (personaje.getAtaque() > jefe.getDefensa()) {
            System.out.println("¡Has ganado!");
            // Aquí puedes agregar experiencia o recompensas
        } else {
            System.out.println("¡Has perdido!");
            personaje.setVida(personaje.getVida() - jefe.getAtaque());
        }
    }
}
