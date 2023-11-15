package com.example.mibarrio.soporte;

import android.graphics.Bitmap;

public class ListViewItem<T> {

    private int id;
    private String titulo;
    private String descripcion;
    private Bitmap imagen;
    private Class<T> classType;

    public ListViewItem(int id, String titulo, String descripcion, Bitmap imagen, Class<T> classType) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.classType = classType;
    }

    public int GetId() { return id; }
    public String GetTitulo() { return titulo; }
    public String GetDescripcion() { return descripcion; }
    public Bitmap GetImagen() { return imagen; }
    public Class<T> GetClassType() { return classType; }
}
