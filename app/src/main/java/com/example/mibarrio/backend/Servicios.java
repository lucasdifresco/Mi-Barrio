package com.example.mibarrio.backend;

import com.example.mibarrio.logica.Denuncia;
import com.example.mibarrio.logica.Imagen;
import com.example.mibarrio.logica.Instalacion;
import com.example.mibarrio.logica.Notificacion;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.logica.Reclamo;
import com.example.mibarrio.logica.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Servicios {

    @POST("usuario/login")
    Call<Usuario> login(@Body Usuario usuario);
    @POST("usuario/save")
    Call<Usuario> register(@Body Usuario usuario);

    @GET("promociones/getAll")
    Call<List<Publicacion>> getPublicaciones();
    @GET("instalaciones/getAll")
    Call<List<Instalacion>> getInstalaciones();
    @GET("reclamos/getAll")
    Call<List<Reclamo>> getReclamos();
    @GET("denuncia/getAll")
    Call<List<Denuncia>> getDenuncias();

    @POST("notificaciones/getByIdUsuario")
    Call<List<Notificacion>> getNotificaciones(@Body int idusuario);

    @POST("imagenes/save")
    Call<Imagen> uploadImagen(@Body Imagen imagen);
    @POST("promociones/save")
    Call<Publicacion> uploadPublicacion(@Body Publicacion publicacion);
    @POST("reclamos/save")
    Call<Reclamo> uploadReclamo(@Body Reclamo reclamo);
    @POST("denuncia/save")
    Call<Denuncia> uploadDenucnia(@Body Denuncia denuncia);
    @POST("notificaciones/save")
    Call<Notificacion> uploadNotificacion(@Body Notificacion notificacion);

}
