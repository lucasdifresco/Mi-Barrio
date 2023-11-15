package com.example.mibarrio.logica;

import com.example.mibarrio.enumerables.USUARIOS_ROLES;

import java.io.Serializable;

public class Sesion implements Serializable {
    private int idUsuario;
    private USUARIOS_ROLES rol;
    private String nombre;

    public Sesion(int id, USUARIOS_ROLES rol, String nombre)
    {
        idUsuario = id;
        this.rol = rol;
        this.nombre = nombre;
    }

    public int GetId() { return idUsuario; }
    public USUARIOS_ROLES GetRol() { return rol; }
    public String GetNombre(){ return nombre; }
}
