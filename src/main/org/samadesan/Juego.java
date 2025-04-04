package org.samadesan;

import org.samadesan.entidades.EfectoEspecial;
import org.samadesan.entidades.Enemigo;
import org.samadesan.entidades.Jefe;
import org.samadesan.entidades.Personaje;
import org.samadesan.repositorios.EnemigoRepository;
import org.samadesan.repositorios.JefeRepository;
import org.samadesan.repositorios.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Juego {
    private static final double RPG_PROB = 0.50;
    private static final double OBJETO_PROB = 0.25;
    private static final double LUCHA_PROB = 0.15;
    private static final double MINIJEF_PROB = 0.09;

    private final Scanner scanner;
    private Personaje personaje;
    private final PersonajeRepository personajeRepository;
    private final EnemigoRepository enemigoRepository;
    private final JefeRepository jefeRepository;

    // Constructor que recibe los repositorios y el escáner
    public Juego(PersonajeRepository personajeRepository, EnemigoRepository enemigoRepository, JefeRepository jefeRepository) {
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
        Personaje personaje = personajeRepository.findByNombre(nombre);
        if (personaje == null) {
            System.out.println("Personaje no encontrado. Creando uno nuevo...");
            personaje = new Personaje(nombre, 100, 10, 5, 1); // Valores predeterminados
            personaje.setNombre(nombre);
            personaje.setNivel(1);  // Asignar nivel por defecto
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
        if (enemigo != null) {
            System.out.println("Enemigo encontrado: " + enemigo.getNombre());
        } else {
            System.out.println("ERROR: enemigo es null en procesarCasillaRPG.");
        }

        if (personaje == null) {
            System.out.println("ERROR: personaje es null en procesarCasillaRPG.");
        } else {
            System.out.println("Personaje con vida: " + personaje.getVida());
        }

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

        } else {
            System.out.println("¡Has perdido el combate!");
            personaje.setVida(10); // No muere, pero queda con poca vida
            System.out.println("¡Has sido derrotado por el Jefe! Pero sobrevives con 10 de vida...");
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
        if (this.personaje == null) {
            System.out.println("ERROR: El personaje no ha sido inicializado.");
            return;
        } else {
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
        System.out.println("¡Has entrado en una casilla de Minijefe!");

        // Generar Minijefe aleatorio con estadísticas reducidas
        List<Jefe> jefes = jefeRepository.findAll();
        if (jefes.isEmpty()) {
            System.out.println("ERROR: No hay jefes en la base de datos.");
            return; // Evita que el código continúe si no hay jefes
        }

        Random random = new Random();
        Jefe jefeAleatorio = jefes.get(random.nextInt(jefes.size())); // Seleccionar uno aleatorio
        jefes.set(random.nextInt(jefes.size()), jefeAleatorio);
        Jefe minijefe = new Jefe();
        minijefe.setNombre("Minijefe Feroz " + (random.nextInt(50) + 1));
        minijefe.setAtaque(random.nextInt(6) + 10); // Ataque entre 10 y 15
        minijefe.setVida(random.nextInt(15) + 30); // Vida entre 30 y 45
        minijefe.setDefensa(random.nextInt(4) + 3); // Defensa entre 3 y 6


        // Asignar efecto especial aleatorio
        EfectoEspecial[] efectos = EfectoEspecial.values();
        minijefe.setEfectoEspecial(efectos[random.nextInt(efectos.length)]);

        System.out.println("Ha aparecido " + minijefe.getNombre() + " con efecto " + minijefe.getEfectoEspecial());
        System.out.println("Estadísticas del Minijefe - Ataque: " + minijefe.getAtaque() + " | Vida: " + minijefe.getVida() + " | Defensa: " + minijefe.getDefensa());

        // Guardar el minijefe en la BD para futuras peleas
        jefeRepository.save(minijefe);

        // Combate por turnos
        while (personaje.getVida() > 0 && minijefe.getVida() > 0) {
            // Turno del jugador
            int danyoJugador = Math.max(1, personaje.getAtaque() - minijefe.getDefensa());
            minijefe.setVida(minijefe.getVida() - danyoJugador);
            System.out.println("Golpeas al Minijefe y le quitas " + danyoJugador + " de vida. Vida restante: " + minijefe.getVida());

            // Si el Minijefe muere, termina la pelea
            if (minijefe.getVida() <= 0) {
                System.out.println("¡Has derrotado al Minijefe! Subes 1 nivel.");
                personaje.setNivel(personaje.getNivel() + 1);
                personaje.setAtaque(personaje.getAtaque() + 2);
                personaje.setVida(personaje.getVida() + 5);
                personaje.setDefensa(personaje.getDefensa() + 1);
                personajeRepository.save(personaje);
                return;
            }

            // Turno del Minijefe (aplica su efecto especial)
            int danyoMinijefe = Math.max(1, minijefe.getAtaque() - personaje.getDefensa());

            switch (minijefe.getEfectoEspecial()) {
                case FUEGO:
                    danyoMinijefe += 2; // Más daño
                    System.out.println("El Minijefe usa FUEGO y te quema.");
                    break;
                case HIELO:
                    personaje.setAtaque(Math.max(1, personaje.getAtaque() - 1)); // Reduce ataque
                    System.out.println("El Minijefe usa HIELO y debilita tu ataque.");
                    break;
                case VENENO:
                    personaje.setVida(personaje.getVida() - 1); // Daño adicional
                    System.out.println("☠El Minijefe usa VENENO y te intoxica.");
                    break;
                case ESPINA:
                    personaje.setVida(personaje.getVida() - (danyoJugador / 3)); // Devuelve daño
                    System.out.println("El Minijefe usa ESPINA y te devuelve daño.");
                    break;
            }

            personaje.setVida(personaje.getVida() - danyoMinijefe);
            System.out.println("El Minijefe te ataca y te quita " + danyoMinijefe + " de vida. Vida restante: " + personaje.getVida());
        }

        // Si el jugador pierde
        if (personaje.getVida() <= 0) {
            personaje.setVida(10); // No muere, pero queda con poca vida
            System.out.println("¡Has sido derrotado por el Minijefe! Pero sobrevives con 10 de vida...");
        }

        // Guardar los cambios del personaje
        personajeRepository.save(personaje);
    }

    // Lógica para procesar casilla de tipo Jefe
    private void procesarCasillaJefe() {
        System.out.println("¡Has entrado en una casilla de Jefe!");

        // Generar Jefe aleatorio con efecto especial
        // Obtener todos los jefes de la base de datos
        List<Jefe> jefes = jefeRepository.findAll();
        Random random = new Random();
        jefes.get(random.nextInt(jefes.size())); // Seleccionar uno aleatorio
        Jefe jefe = new Jefe();
        jefe.setNombre("Jefe Mortal " + (random.nextInt(100) + 1));
        jefe.setAtaque(random.nextInt(10) + 15); // Ataque entre 15 y 25
        jefe.setVida(random.nextInt(20) + 50); // Vida entre 50 y 70
        jefe.setDefensa(random.nextInt(6) + 5); // Defensa entre 5 y 10
        // Asignar efecto especial aleatorio
        EfectoEspecial[] efectos = EfectoEspecial.values();
        jefe.setEfectoEspecial(efectos[random.nextInt(efectos.length)]);

        System.out.println("Ha aparecido " + jefe.getNombre() + " con efecto " + jefe.getEfectoEspecial());
        System.out.println("Estadísticas del jefe - Ataque: " + jefe.getAtaque() + " | Vida: " + jefe.getVida() + " | Defensa: " + jefe.getDefensa());

        // Guardar el jefe en la BD para futuras peleas
        jefeRepository.save(jefe);

        // Combate por turnos
        while (personaje.getVida() > 0 && jefe.getVida() > 0) {
            // Turno del jugador
            int danyoJugador = Math.max(1, personaje.getAtaque() - jefe.getDefensa());
            jefe.setVida(jefe.getVida() - danyoJugador);
            System.out.println("Golpeas al jefe y le quitas " + danyoJugador + " de vida. Vida restante del jefe: " + jefe.getVida());

            // Si el jefe muere, termina la pelea
            if (jefe.getVida() <= 0) {
                System.out.println("¡Has derrotado al Jefe! Subes 2 niveles.");
                personaje.setNivel(personaje.getNivel() + 2);
                personaje.setAtaque(personaje.getAtaque() + 3);
                personaje.setVida(personaje.getVida() + 10);
                personaje.setDefensa(personaje.getDefensa() + 2);
                personajeRepository.save(personaje);
                return;
            }

            // Turno del jefe (aplica su efecto especial)
            int danyoJefe = Math.max(1, jefe.getAtaque() - personaje.getDefensa());

            switch (jefe.getEfectoEspecial()) {
                case FUEGO:
                    danyoJefe += 3; // Más daño
                    System.out.println("El jefe usa FUEGO y te quema.");
                    break;
                case HIELO:
                    personaje.setAtaque(Math.max(1, personaje.getAtaque() - 1)); // Reduce ataque
                    System.out.println("El jefe usa HIELO y debilita tu ataque.");
                    break;
                case VENENO:
                    personaje.setVida(personaje.getVida() - 2); // Daño adicional
                    System.out.println("El jefe usa VENENO y te intoxica.");
                    break;
                case ESPINA:
                    personaje.setVida(personaje.getVida() - (danyoJugador / 2)); // Devuelve daño
                    System.out.println("El jefe usa ESPINA y te devuelve daño.");
                    break;
            }

            personaje.setVida(personaje.getVida() - danyoJefe);
            System.out.println("El jefe te ataca y te quita " + danyoJefe + " de vida. Vida restante: " + personaje.getVida());
        }

        // Si el jugador pierde
        if (personaje.getVida() <= 0) {
            personaje.setVida(10); // No muere, pero queda con poca vida
            System.out.println("¡Has sido derrotado por el Jefe! Pero sobrevives con 10 de vida...");
        }

        // Guardar los cambios del personaje
        personajeRepository.save(personaje);
    }

    // Método para obtener un jefe aleatorio de la base de datos

    // Lógica para luchar con un enemigo (en este caso, con cualquier tipo)
    private void lucharConEnemigo(Enemigo enemigo) {
        System.out.println("¡Luchando contra: " + enemigo.getNombre());
        // Aquí iría la lógica de combate (comparar ataques, defensa, vida, etc.)
        if (personaje.getAtaque() > enemigo.getDefensa()) {
            System.out.println("¡Has ganado!");
        } else {
            System.out.println("¡Has perdido!");
            personaje.setVida(personaje.getVida() - enemigo.getAtaque());
        }
    }
}
