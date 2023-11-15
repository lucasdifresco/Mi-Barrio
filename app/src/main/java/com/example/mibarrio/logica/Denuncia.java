package com.example.mibarrio.logica;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Denuncia implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("fecha")
    private Date horario;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("aceptaresponsabilidad")
    private Boolean responsable;
    @SerializedName("imagenes")
    private Imagen[] imagenes;
    @SerializedName("comentarios")
    private Comentario[] comentarios;
    @SerializedName("usuario")
    private Usuario usuario;

    public Denuncia(){}
    public Denuncia(String nombre, String direccion, String descripcion, Boolean responsable, Imagen[] imagenes, Usuario usuario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.responsable = responsable;
        this.imagenes = imagenes;
        this.usuario = usuario;
    }
    public Denuncia(int id, String nombre, String dirección, Date horario, String descripcion, Boolean responsable,Imagen[] imagenes, Comentario[] comentarios, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = dirección;
        this.horario = horario;
        this.descripcion = descripcion;
        this.responsable = responsable;
        this.imagenes = imagenes;
        this.comentarios = comentarios;
        this.usuario = usuario;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public Date getHorario() { return horario; }
    public String getDescripcion() { return descripcion; }
    public Boolean getResponsable() { return responsable; }
    public Imagen[] getImagenes() { return imagenes; }
    public Comentario[] getComentarios() { return comentarios; }
    public Usuario getUsuario() { return usuario; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setHorario(Date horario) { this.horario = horario; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setResponsable(Boolean responsable) { this.responsable = responsable; }
    public void setImagenes(Imagen[] imagenes) { this.imagenes = imagenes; }
    public void setComentarios(Comentario[] comentarios) { this.comentarios = comentarios; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public ArrayList<Bitmap> GetBitmapArray() { return Imagen.GetBitmapArray(imagenes); }
    public Bitmap GetFirstImage() { if (imagenes.length>0) { return imagenes[0].ToBitmap(); } else { return null; } }

}
