package org.samadesan.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "batalla")
public class Batalla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "personaje_id")
    private Personaje personaje;

    @ManyToOne
    @JoinColumn(name = "enemigo_id")
    private Enemigo esbirroEnemigo;

    private String resultado;

    // Constructores, getters y setters

    public Batalla() {}

    public Batalla(String resultado) {
        this.resultado = resultado;
    }

    public int getId() { return id; }

    public Personaje getPersonaje() { return personaje; }

    public void setPersonaje(Personaje personaje) { this.personaje = personaje; }

    public Enemigo getEsbirroEnemigo() { return esbirroEnemigo; }

    public void setEsbirroEnemigo(Enemigo esbirroEnemigo) { this.esbirroEnemigo = esbirroEnemigo; }

    public String getResultado() { return resultado; }

    public void setResultado(String resultado) { this.resultado = resultado; }
}
