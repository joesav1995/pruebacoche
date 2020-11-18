package com.pruebacoche.coche;

public class CocheRequest {


    private String marca;

    private String modelo;

    //caballos coche
    private Integer cv;

    public CocheRequest(){

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

    public Integer getCv() {
        return cv;
    }

    public void setCv(Integer cv) {
        this.cv = cv;
    }
}
