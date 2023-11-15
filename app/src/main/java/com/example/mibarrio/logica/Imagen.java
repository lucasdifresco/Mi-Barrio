package com.example.mibarrio.logica;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Imagen implements Serializable {

    //@SerializedName("idexterno")
    private long id;
    //@SerializedName("idusuario")
    private long idusuario;
    @SerializedName("imagen")
    private String imagen;
    @SerializedName("nombre")
    private String nombre;

    public Imagen(){}
    public Imagen(long id, long idusuario, String nombre, Bitmap imagen) {
        this.id = id;
        this.idusuario = idusuario;
        this.imagen = ToString(imagen);
        this.nombre = nombre;
    }
    public Imagen(long idusuario, String nombre, String imagen) {
        this.idusuario = idusuario;
        this.imagen = imagen;
        this.nombre = nombre;
    }
    public Imagen(String nombre, Bitmap imagen) {
        this.imagen = ToString(imagen);
        this.nombre = nombre;
    }

    public long getId() { return id; }
    public long getIdusuario() { return idusuario; }
    public String getImagen() { return imagen; }
    public String getNombre() { return nombre; }

    public void setId(long id) { this.id = id; }
    public void setIdusuario(long idusuario) { this.idusuario = idusuario; }
    public void setImagen(Bitmap imagen) { this.imagen = ToString(imagen); }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Bitmap ToBitmap() {
        Bitmap bmp = null;
        if (imagen != null){
            byte[] bytes = Base64.decode(imagen, Base64.DEFAULT);
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bmp;
    }
    public static ArrayList<Bitmap> GetBitmapArray(Imagen[] imagenes) {
        ArrayList<Bitmap> arreglo = new ArrayList<Bitmap>();
        for(int i = 0; i< imagenes.length; i++) { arreglo.add(imagenes[i].ToBitmap()); }
        return arreglo;
    }
    public static String ToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
