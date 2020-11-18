package com.pruebacoche.coche;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Coche {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private Double coste;
    private String modelo;
    private Date fechaVenta;
    private Date fechaIngreso;
    private Boolean vendido;
    private String matricula;
    private Double preciVenta;




    public Coche(){

    }

    public Coche(String marca, Double coste, String modelo, Date fechaVenta, Date fechaIngreso, Boolean vendido, String matricula, Double preciVenta) {
        this.marca = marca;
        this.coste = coste;
        this.modelo = modelo;
        this.fechaVenta = fechaVenta;
        this.fechaIngreso = fechaIngreso;
        this.vendido = vendido;
        this.matricula = matricula;
        this.preciVenta = preciVenta;
    }

    public Long getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Double getPreciVenta() {
        return preciVenta;
    }

    public void setPreciVenta(Double preciVenta) {
        this.preciVenta = preciVenta;
    }
}
