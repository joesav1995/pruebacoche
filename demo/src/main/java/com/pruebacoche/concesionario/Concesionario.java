package com.pruebacoche.concesionario;

import com.pruebacoche.coche.Coche;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Concesionario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique=true)
    private String direccion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Coche> coches;

    public Concesionario() {}

    public Concesionario(String direccion) {
        this.direccion = direccion;
        this.coches = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Coche> getCoches() {
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }

    //para debugar con logger
    @Override
    public String toString() {
        return "Concesionario{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", coches=" + coches +
                '}';
    }
}
