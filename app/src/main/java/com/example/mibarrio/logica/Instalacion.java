package com.example.mibarrio.logica;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Instalacion implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("titular")
    private String titular;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("ubicacion")
    private String ubicacion;
    @SerializedName("horarios")
    private String horarios;
    @SerializedName("categoria")
    private int categoria;

    public Instalacion(){}
    public Instalacion(int id, String nombre, String titular, String direccion, String ubicacion, String horarios, int categoria) {
        this.id = id;
        this.nombre = nombre;
        this.titular = titular;
        this.direccion = direccion;
        this.ubicacion = ubicacion;
        this.horarios = horarios;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTitular() { return titular; }
    public String getDireccion() { return direccion; }
    public String getUbicacion() { return ubicacion; }
    public String getHorarios() { return horarios; }
    public int getCategoria() { return categoria; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTitular(String titular) { this.titular = titular; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setHorarios(String horarios) { this.horarios = horarios; }
    public void setCategoria(int categoria) { this.categoria = categoria; }

}
