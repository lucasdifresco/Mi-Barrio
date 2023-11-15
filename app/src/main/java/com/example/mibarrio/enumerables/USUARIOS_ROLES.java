package com.example.mibarrio.enumerables;

public enum USUARIOS_ROLES {
    INVITADO(0),
    VECINO(1),
    INSPECTOR(2);

    private int valor;
    USUARIOS_ROLES(int valor) { this.valor = valor; }
    public int toInt(){ return valor; }
}
