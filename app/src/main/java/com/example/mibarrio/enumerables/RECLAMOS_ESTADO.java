package com.example.mibarrio.enumerables;

public enum RECLAMOS_ESTADO {
    ACEPTADO(0),
    RECHAZADO(1),
    SIN_REVISAR(2);

    private int valor;
    RECLAMOS_ESTADO(int valor) { this.valor = valor; }
    public int toInt(){ return valor; }
    public static String getValueById(int id){
        switch(id){
            case 0: return RECLAMOS_ESTADO.ACEPTADO.toString();
            case 1: return RECLAMOS_ESTADO.RECHAZADO.toString();
            case 2: return RECLAMOS_ESTADO.SIN_REVISAR.toString();
            default: return "Invalido";
        }
    }
}
