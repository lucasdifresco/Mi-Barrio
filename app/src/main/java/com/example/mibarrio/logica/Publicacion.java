package com.example.mibarrio.logica;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Publicacion implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("telefono")
    private String telefono;
    @SerializedName("descripcion")
    private String detalle;
    @SerializedName("privado")
    private Boolean isPrivada;
    @SerializedName("imagenes")
    private Imagen[] imagenes;
    @SerializedName("usuarioDTO")
    private Usuario usuario;
    @SerializedName("idcategoriapromocion")
    private int categoria;
    @SerializedName("estado")
    private Boolean estado;

    public  Publicacion() {}
    public Publicacion(int id, String nombre, String direccion, String telefono, String detalle, Boolean isPrivada, Imagen[] imagenes, Usuario usuario, int categoria, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.detalle = detalle;
        this.isPrivada = isPrivada;
        this.imagenes = imagenes;
        this.usuario = usuario;
        this.categoria = categoria;
        this.estado = estado;
    }
    public Publicacion(String nombre, String direccion, String telefono, String detalle, int idCategoria, Usuario usuario, Boolean isPrivada, Imagen[] imagenes) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.detalle = detalle;
        this.categoria = idCategoria;
        this.isPrivada = isPrivada;
        this.usuario = usuario;
        this.imagenes = imagenes;
    }
    public Publicacion(int id, String nombre, String direccion, String telefono, String detalle, int idCategoria, Usuario usuario, Boolean isPrivada, Imagen[] imagenes) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.detalle = detalle;
        this.categoria = idCategoria;
        this.isPrivada = isPrivada;
        this.usuario = usuario;
        this.imagenes = imagenes;
    }

    public int getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getDireccion(){ return direccion; }
    public String getTelefono(){ return telefono; }
    public String getDetalle(){ return detalle; }
    public Boolean isPrivada(){ return isPrivada; }
    public Imagen[] getImagenes() { return imagenes; }
    public Usuario getUsuario(){ return usuario; }
    public int getCategoria() { return categoria; }
    public Boolean getEstado() { return estado; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public void setPrivada(Boolean privada) { isPrivada = privada; }
    public void setImagenes(Imagen[] imagenes) { this.imagenes = imagenes; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setCategoria(int categoria) { this.categoria = categoria; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public ArrayList<Bitmap> GetBitmapArray() { return Imagen.GetBitmapArray(imagenes); }
    public Bitmap GetFirstImage() { if (imagenes.length>0) { return imagenes[0].ToBitmap(); } else { return null; } }
}
