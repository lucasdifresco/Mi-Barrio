package com.example.mibarrio.logica.filtros;

public class FiltroReclamos {

    public Boolean misReclamos;

    public Boolean aceptado;
    public Boolean rechazado;
    public Boolean sinRevisar;

    public Boolean otros;
    public Boolean calles;
    public Boolean plazas;
    public Boolean oficinasPublicas;
    public Boolean escuelasPrimarias;
    public Boolean escuelasSecundarias;
    public Boolean comedoresEscolares;
    public Boolean museos;
    public Boolean campingMunicipal;

    public FiltroReclamos() {
        this.misReclamos = false;
        this.aceptado = false;
        this.rechazado = false;
        this.sinRevisar = false;
        this.otros = false;
        this.calles = false;
        this.plazas = false;
        this.oficinasPublicas = false;
        this.escuelasPrimarias = false;
        this.escuelasSecundarias = false;
        this.comedoresEscolares = false;
        this.museos = false;
        this.campingMunicipal = false;
    }
}
