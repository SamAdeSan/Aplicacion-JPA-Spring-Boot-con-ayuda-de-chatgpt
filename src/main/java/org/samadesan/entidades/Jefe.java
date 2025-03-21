package org.samadesan.entidades;

import jakarta.persistence.*;

@Entity
public class Jefe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private int ataque;
    private int vida;
    private int defensa;

    // Nuevo campo
    private String efectoEspecial; // fuego, hielo, veneno, espina, etc.

    @OneToOne
    @JoinColumn(name = "casilla_id", unique = true)
    private Casilla casilla;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    public String getEfectoEspecial() { return efectoEspecial; }
    public void setEfectoEspecial(String efectoEspecial) { this.efectoEspecial = efectoEspecial; }

    public Casilla getCasilla() { return casilla; }
    public void setCasilla(Casilla casilla) { this.casilla = casilla; }
}
