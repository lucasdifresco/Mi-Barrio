package com.example.mibarrio.logica.filtros;

import java.io.Serializable;

public class FiltroPublicaciones implements Serializable {
    public String palabrasClave;
    public Boolean misPublicaciones;
    public Boolean servicios;
    public Boolean productos;

    public FiltroPublicaciones() {
        palabrasClave = "";
        misPublicaciones = false;
        servicios = false;
        productos = false;
    }
}
