package org.samadesan.entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    // El tipo de nivel (por ejemplo: "lucha", "minijefe", "objeto", etc.)
    private String tipo;

    @OneToMany(mappedBy = "nivel", cascade = CascadeType.ALL)
    private List<Casilla> casillas;

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }

    public void setCasillas(List<Casilla> casillas) {
        this.casillas = casillas;
    }
}
