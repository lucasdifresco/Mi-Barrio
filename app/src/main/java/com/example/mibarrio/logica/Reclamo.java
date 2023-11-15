package com.example.mibarrio.logica;

import android.graphics.Bitmap;

import com.example.mibarrio.soporte.Helper;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Reclamo implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("titulo")
    private String titulo;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("ubicacion")
    private String ubicacion;
    @SerializedName("categoria")
    private int categoria;
    @SerializedName("usuario")
    private Usuario usuario;
    @SerializedName("imagenes")
    private Imagen[] imagenes;
    @SerializedName("estado")
    private int estado;

    public Reclamo(){}
    public Reclamo(String titulo, String descripcion, String ubicacion, int categoria, Usuario usuario, Imagen[] imagenes, int estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.usuario = usuario;
        this.imagenes = imagenes;
        this.estado = estado;
    }
    public Reclamo(String titulo, String descripcion, int categoria, Usuario usuario, Imagen[] imagenes, int estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.usuario = usuario;
        this.imagenes = imagenes;
        this.estado = estado;
    }
    public Reclamo(int id, String titulo, String descripcion, int categoria, Usuario usuario, Imagen[] imagenes, int estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.usuario = usuario;
        this.imagenes = imagenes;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getUbicacion() { return ubicacion; }
    public int getCategoria() { return categoria; }
    public Usuario getUsuario() { return usuario; }
    public Imagen[] getImagenes() { return imagenes; }
    public int getEstado() { return estado; }

    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setCategoria(int categoria) { this.categoria = categoria; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setImagenes(Imagen[] imagenes) { this.imagenes = imagenes; }
    public void setEstado(int estado) { this.estado = estado; }

    public ArrayList<Bitmap> GetBitmapArray() { return Imagen.GetBitmapArray(imagenes); }
    public Bitmap GetFirstImage() { if (imagenes.length>0) { return imagenes[0].ToBitmap(); } else { return null; } }

}
