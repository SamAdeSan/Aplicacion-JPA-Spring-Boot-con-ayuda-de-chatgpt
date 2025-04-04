package org.samadesan.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "personaje")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int ataque;
    private int vida;
    private int defensa;
    private Integer nivel;

    @ManyToMany
    @JoinColumn(name = "casilla_id")
    private List<Casilla> casilla;

    @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Batalla> batallas = new HashSet<>();

    // Constructores, getters y setters

    public Personaje() {}

    public Personaje(String nombre, int ataque, int vida, int defensa, Integer nivel) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.vida = vida;
        this.defensa = defensa;
        this.nivel = nivel;
    }

    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getAtaque() { return ataque; }

    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getVida() { return vida; }

    public void setVida(int vida) { this.vida = vida; }

    public int getDefensa() { return defensa; }

    public void setDefensa(int defensa) { this.defensa = defensa; }

    public Integer getNivel() { return nivel; }

    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public List<Casilla> getCasilla() { return casilla; }

    public void setCasilla(List<Casilla> casilla) { this.casilla = casilla; }

    public Set<Batalla> getBatallas() { return batallas; }

    public void setBatallas(Set<Batalla> batallas) { this.batallas = batallas; }

    public void anyadirBatalla(Batalla batalla) {
        batallas.add(batalla);
        batalla.setPersonaje(this);
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ataque=" + ataque +
                ", vida=" + vida +
                ", defensa=" + defensa +
                '}';
    }
}
