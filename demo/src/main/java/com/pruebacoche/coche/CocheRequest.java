package com.pruebacoche.coche;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CocheRequest {

    private Long id;
    @NotNull(message = "Direccion puede no puese estar vacio.")
    private String direccion;
    @NotNull(message = "Marca no puede estar vacio.")
    private String marca;
    @NotNull(message = "Coste no puede estar vacio.")
    private Double coste;
    private Date fechaVenta;
    private Date fechaIngreso;
    private Boolean vendido;
    private Double precioVenta;

    public CocheRequest() {

    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
