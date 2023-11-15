package com.example.mibarrio.enumerables;

public enum PUBLICACION_TIPO {
    SERVICIO(1),
    PRODUCTO(2);

    private int valor;
    PUBLICACION_TIPO(int valor) { this.valor = valor; }
    public int toInt(){ return valor; }
    public static String getValueById(int id){
        switch(id){
            case 1: return PUBLICACION_TIPO.SERVICIO.toString();
            case 2: return PUBLICACION_TIPO.PRODUCTO.toString();
            default: return "Invalido";
        }
    }
}
