package org.samadesan.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "enemigo")
public class Enemigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private int ataque;
    private int vida;
    private int defensa;

    public Enemigo() {
    }

    @Enumerated(EnumType.STRING)
    private EfectoEspecial efectoEspecial;

    @ManyToOne
    @JoinColumn(name = "casilla_id")
    private Casilla casilla;

    @OneToMany(mappedBy = "esbirroEnemigo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Batalla> batallas = new HashSet<>();

    // Constructores, getters y setters

    public Enemigo(String nombre, int vida, int ataque, int defensa, EfectoEspecial efectoEspecial) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.efectoEspecial = efectoEspecial;
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

    public EfectoEspecial getEfectoEspecial() { return efectoEspecial; }

    public void setEfectoEspecial(EfectoEspecial efectoEspecial) { this.efectoEspecial = efectoEspecial; }

    public Casilla getCasilla() { return casilla; }

    public void setCasilla(Casilla casilla) { this.casilla = casilla; }

    public Set<Batalla> getBatallas() { return batallas; }

    public void setBatallas(Set<Batalla> batallas) { this.batallas = batallas; }

    public void añadirBatalla(Batalla batalla) {
        batallas.add(batalla);
        batalla.setEsbirroEnemigo(this);
    }

    @Override
    public String toString() {
        return "EsbirroEnemigo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ataque=" + ataque +
                ", vida=" + vida +
                ", defensa=" + defensa +
                ", efectoEspecial=" + (efectoEspecial != null ? efectoEspecial.name() : "Ninguno") +
                '}';
    }
}
