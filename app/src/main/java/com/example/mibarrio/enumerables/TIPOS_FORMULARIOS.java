package com.example.mibarrio.enumerables;

public enum TIPOS_FORMULARIOS
{
    PUBLICACION(0),
    RECLAMO(1),
    DENUNCIA(2),
    INSTALACION(3);

    private int valor;
    TIPOS_FORMULARIOS(int valor) { this.valor = valor; }
    public int toInt(){ return valor; }
    public static TIPOS_FORMULARIOS getValueById(int id){
        switch(id){
            case 0: return TIPOS_FORMULARIOS.PUBLICACION;
            case 1: return TIPOS_FORMULARIOS.RECLAMO;
            case 2: return TIPOS_FORMULARIOS.DENUNCIA;
            case 3: return TIPOS_FORMULARIOS.INSTALACION;
            default: return TIPOS_FORMULARIOS.PUBLICACION;
        }
    }
}
