package com.pruebacoche.coche;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Coche {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String direccion;

    private String marca;
    private Double coste;

    private Date fechaVenta;
    private Date fechaIngreso;
    private Boolean vendido;
    private String matricula;
    private Double precioVenta;




    public Coche(){

    }

    public Coche(String direccion, String marca, Double coste, Date fechaIngreso, Boolean vendido) {
        this.direccion = direccion;
        this.marca = marca;
        this.coste = coste;

        this.fechaVenta = null;
        this.fechaIngreso = fechaIngreso;
        this.vendido = vendido;
        this.precioVenta=null;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Long getId() {
        return id;
    }
}
