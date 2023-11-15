package com.example.mibarrio.backend;

import android.util.Log;

import com.example.mibarrio.logica.Denuncia;
import com.example.mibarrio.logica.Imagen;
import com.example.mibarrio.logica.Instalacion;
import com.example.mibarrio.logica.Notificacion;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.logica.Reclamo;
import com.example.mibarrio.logica.Usuario;
import com.example.mibarrio.soporte.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendController {

    public static final String IP = "192.168.0.10";
    public static final String BASE_URL = "http://" + IP + ":8080/apd/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) { retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build(); }
        return retrofit;
    }
    public static Call<Usuario> logIn(String documento, String pass, Boolean isVecino) { return getClient().create(Servicios.class).login(new Usuario(documento, pass, isVecino)); }
    public static Call<Usuario> registrar(String documento, String nombre, String apellido,String password) { return getClient().create(Servicios.class).register(new Usuario(documento, nombre, apellido, password)); }
    public static Call<List<Publicacion>> getPublicaciones() { return getClient().create(Servicios.class).getPublicaciones(); }
    public static Call<List<Instalacion>> getInstalaciones() { return getClient().create(Servicios.class).getInstalaciones(); }
    public static Call<List<Reclamo>> getReclamos() { return getClient().create(Servicios.class).getReclamos(); }
    public static Call<List<Denuncia>> getDenuncias() { return getClient().create(Servicios.class).getDenuncias(); }
    public static Call<List<Notificacion>> getNotificaciones(int idUsuario) { return getClient().create(Servicios.class).getNotificaciones(idUsuario); }
    public static Call<Publicacion> uploadPublicacion(Publicacion publicacion) { return getClient().create(Servicios.class).uploadPublicacion(publicacion); }
    public static Call<Reclamo> uploadReclamo(Reclamo reclamo) { return getClient().create(Servicios.class).uploadReclamo(reclamo); }
    public static Call<Denuncia> uploadDenuncia(Denuncia denuncia) { return getClient().create(Servicios.class).uploadDenucnia(denuncia); }
    public static Call<Notificacion> uploadNotificacion(Notificacion notificacion) { return getClient().create(Servicios.class).uploadNotificacion(notificacion); }
    public static Call<Imagen> uploadImagen(Imagen imagen) { return getClient().create(Servicios.class).uploadImagen(imagen); }
}
