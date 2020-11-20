package com.pruebacoche.concesionario;

import com.pruebacoche.coche.Coche;

import javax.persistence.*;
import java.util.List;

@Entity
public class Concesionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<Coche> coches;


}
