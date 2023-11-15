package com.example.mibarrio.logica.filtros;

public class FiltroInstalaciones {
    public Boolean calles;
    public Boolean plazas;
    public Boolean oficinasPublicas;
    public Boolean escuelasPrimarias;
    public Boolean escuelasSecundarias;
    public Boolean comedoresEscolares;
    public Boolean museos;
    public Boolean campingMunicipal;
    public Boolean otros;

    public FiltroInstalaciones() {
        this.calles = false;
        this.plazas = false;
        this.oficinasPublicas = false;
        this.escuelasPrimarias = false;
        this.escuelasSecundarias = false;
        this.comedoresEscolares = false;
        this.museos = false;
        this.campingMunicipal = false;
        this.otros = false;
    }
}
