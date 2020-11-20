package com.pruebacoche.coche;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MatriculaRequest {


    @NotBlank(message = "Direccion puede no puese estar vacio.")
    private String direccion;

    @NotBlank(message = "Matricula puede no puese estar vacio.")
    @Size(min = 7, max = 7)
    private String matricula;
    @NotBlank(message = "Marca no puede estar vacio.")
    private String marca;

    @NotNull
    private Double precioVenta;

    public MatriculaRequest(){}

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getMarca() {
        return marca;
    }
}
