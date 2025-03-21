package org.samadesan.entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "casilla")
public class Casilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "nivel_id")
    private Nivel nivel;

    @ManyToMany(mappedBy = "casilla")
    private List<Personaje> personajes;

    @OneToMany(mappedBy = "casilla")
    private List<Enemigo> enemigos;

    @OneToOne(mappedBy = "casilla")
    private Jefe jefe;

    // Constructores

    public Casilla() {}

    public Casilla(String tipo, Nivel nivel) {
        this.tipo = tipo;
        this.nivel = nivel;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void setEnemigos(List<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

    public Jefe getJefe() {
        return jefe;
    }

    public void setJefe(Jefe jefe) {
        this.jefe = jefe;
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", nivel=" + (nivel != null ? nivel.getId() : "null") +
                '}';
    }
}
