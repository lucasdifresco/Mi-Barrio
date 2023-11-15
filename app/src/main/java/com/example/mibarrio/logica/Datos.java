package com.example.mibarrio.logica;

import android.content.Context;

import com.example.mibarrio.MainActivity;
import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.enumerables.TIPOS_FORMULARIOS;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.filtros.FiltroDenuncias;
import com.example.mibarrio.logica.filtros.FiltroInstalaciones;
import com.example.mibarrio.logica.filtros.FiltroPublicaciones;
import com.example.mibarrio.logica.filtros.FiltroReclamos;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.Helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Datos {

    private static final String FILE_NAME = "localData.apd";
    private static LocalData localData;
    public static LocalData getLocalData() throws IOException {
        Context context = MainActivity.getContext();
        FileInputStream fileStream = null;
        try {
            fileStream = context.openFileInput(FILE_NAME);
            if(fileStream != null) {
                ObjectInputStream objectStream = new ObjectInputStream(fileStream);
                localData = (LocalData) objectStream.readObject();
            } else { localData = saveLocalData(); }
        }
        catch (FileNotFoundException | ClassNotFoundException e) { e.printStackTrace(); }

        return localData;
    }
    public static LocalData saveLocalData() {
        if (localData == null) { localData = new LocalData(); }
        Context context = MainActivity.getContext();
        FileOutputStream fileStream = null;
        try {
            fileStream = context.openFileOutput(FILE_NAME, context.MODE_PRIVATE);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(localData);
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }

        return localData;
    }
    public static Sesion getSesion() {
        if(localData == null) { try { getLocalData(); } catch (IOException e) { e.printStackTrace(); } }
        return localData.getSesion();
    }
    public static void setSesion(Sesion newSesion) { localData.setSesion(newSesion); }
    public static void addPublicacionPendiente(Publicacion publicacion) { localData.addPublicacionPendiente(publicacion); }
    public static void addReclamoPendiente(Reclamo reclamo) { localData.addReclamoPendiente(reclamo); }
    public static void addDenunciaPendiente(Denuncia denuncia) { localData.addDenunciaPendiente(denuncia); }
    public static void TryUploadReclamosPendientes() { localData.TryUploadReclamosPendientes(); }
    public static void TryUploadPublicacionesPendientes() { localData.TryUploadPublicacionesPendientes(); }
    public static void TryUploadDenunciasPendientes() { localData.TryUploadDenunciasPendientes(); }

    private static Publicacion[] publicaciones;
    private static int publicacionActiva = 0;
    private static FiltroPublicaciones filtroPublicaciones;

    public static Publicacion[] getPublicaciones() { return publicaciones; }
    public static int getPublicacion(int id) {
        for (int i = 0; i < publicaciones.length; i++) { if(publicaciones[i].getId() == id){ return i; } }
        return 0;
    }
    public static Publicacion getPublicacionActiva() { return publicaciones[publicacionActiva]; }
    public static FiltroPublicaciones getFiltroPublicaciones() { if(filtroPublicaciones == null) { filtroPublicaciones = new FiltroPublicaciones(); } return filtroPublicaciones; }
    public static void setPublicacionActiva(int posicion) { publicacionActiva = posicion; }
    public static void setPublicacionActiva(Publicacion publicacion) {
        if(publicacion == null){ publicacionActiva = 0; return; }
        int i = 0;
        boolean hit = false;
        while (i < publicaciones.length && !hit) {
            if(publicacion.getId() == publicaciones[i].getId()) { publicacionActiva = i; hit = true; }
            i++;
        }
    }
    public static void setFiltroPublicaciones(FiltroPublicaciones filtroPublicaciones) { Datos.filtroPublicaciones = filtroPublicaciones; }
    public static void TryUpdatePublicaciones(Accion onResult, Accion onError) {
        BackendController.getPublicaciones().enqueue(new Callback<List<Publicacion>>() {
            @Override public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                if(response.body() != null) { publicaciones = response.body().toArray(new Publicacion[0]); onResult.Ejecutar(0); }
                else { Helper.Toast("empty body: " + response.toString()); onError.Ejecutar(0);}
            }
            @Override public void onFailure(Call<List<Publicacion>> call, Throwable t) { Helper.Toast("fail: " + t.toString()); onError.Ejecutar(0); }
        });
    }

    private static Instalacion[] instalaciones;
    private static int instalacionActiva = 0;
    private static FiltroInstalaciones filtroInstalaciones;

    public static Instalacion[] getInstalaciones() { return instalaciones; }
    public static Instalacion getInstalacionActiva() { return instalaciones[instalacionActiva]; }
    public static FiltroInstalaciones getFiltroInstalaciones() { if(filtroInstalaciones == null) { filtroInstalaciones = new FiltroInstalaciones(); } return filtroInstalaciones; }
    public static void setInstalacionActiva(int posicion) { instalacionActiva = posicion; }
    public static void setInstalacionActiva(Instalacion instalacion) {
        if(instalacion == null){ instalacionActiva = 0; return; }
        int i = 0;
        boolean hit = false;
        while (i < instalaciones.length && !hit) {
            if(instalacion.getId() == instalaciones[i].getId()) { instalacionActiva = i; hit = true; }
            i++;
        }
    }
    public static void setFiltroInstalaciones(FiltroInstalaciones filtroInstalaciones) { Datos.filtroInstalaciones = filtroInstalaciones; }
    public static void TryUpdateInstalaciones(Accion onResult, Accion onError) {
        BackendController.getInstalaciones().enqueue(new Callback<List<Instalacion>>() {
            @Override public void onResponse(Call<List<Instalacion>> call, Response<List<Instalacion>> response) {
                if(response.body() != null) { instalaciones = response.body().toArray(new Instalacion[0]); onResult.Ejecutar(0); }
                else { Helper.Toast("empty body: " + response.toString()); onError.Ejecutar(0); }
            }
            @Override public void onFailure(Call<List<Instalacion>> call, Throwable t) { Helper.Toast("fail: " + t.toString()); onError.Ejecutar(0); }
        });
    }

    private static Reclamo[] reclamos;
    private static int reclamoActivo = 0;
    private static FiltroReclamos filtroReclamos;

    public static Reclamo[] getReclamos() { return reclamos; }
    public static int getReclamo(int id) {
        for (int i = 0; i < reclamos.length; i++) { if(reclamos[i].getId() == id){ return i; } }
        return 0;
    }
    public static Reclamo getReclamoActivo() { return reclamos[reclamoActivo]; }
    public static FiltroReclamos getFiltroReclamos() { if(filtroReclamos == null) { filtroReclamos = new FiltroReclamos(); } return filtroReclamos; }
    public static void setReclamoActivo(int posicion) { reclamoActivo = posicion; }
    public static void setReclamoActivo(Reclamo reclamo) {
        if(reclamo == null){ reclamoActivo = 0; return; }
        int i = 0;
        boolean hit = false;
        while (i < reclamos.length && !hit) {
            if(reclamo.getId() == reclamos[i].getId()) { reclamoActivo = i; hit = true; }
            i++;
        }
    }
    public static void setFiltroReclamos(FiltroReclamos filtroReclamos) { Datos.filtroReclamos = filtroReclamos; }
    public static void TryUpdateReclamos(Accion onResult, Accion onError) {
        BackendController.getReclamos().enqueue(new Callback<List<Reclamo>>() {
            @Override public void onResponse(Call<List<Reclamo>> call, Response<List<Reclamo>> response) {
                if(response.body() != null) { reclamos = response.body().toArray(new Reclamo[0]); onResult.Ejecutar(0); }
                else { Helper.Toast("empty body: " + response.toString()); onError.Ejecutar(0); }
            }
            @Override public void onFailure(Call<List<Reclamo>> call, Throwable t) { Helper.Toast("fail: " + t.toString()); onError.Ejecutar(0); }
        });
    }

    private static Denuncia[] denuncias;
    private static int denunciaActiva = 0;
    private static FiltroDenuncias filtroDenuncias;

    public static Denuncia[] getDenuncias() { return denuncias; }
    public static int getDenuncia(int id) {
        for (int i = 0; i < denuncias.length; i++) { if(denuncias[i].getId() == id){ return i; } }
        return 0;
    }
    public static Denuncia getDenunciaActiva() { return denuncias[denunciaActiva]; }
    public static FiltroDenuncias getFiltroDenuncias() { if(filtroDenuncias == null) { filtroDenuncias = new FiltroDenuncias(); } return filtroDenuncias; }
    public static void setDenunciaActiva(int posicion) { denunciaActiva = posicion; }
    public static void setDenunciaActiva(Denuncia denuncia) {
        if(denuncia == null){ denunciaActiva = 0; return; }
        int i = 0;
        boolean hit = false;
        while (i < denuncias.length && !hit) {
            if(denuncia.getId() == denuncias[i].getId()) { denunciaActiva = i; hit = true; }
            i++;
        }
    }
    public static void setFiltroDenuncias(FiltroDenuncias filtroDenuncias) { Datos.filtroDenuncias = filtroDenuncias; }
    public static void TryUpdateDenuncias(Accion onResult, Accion onError) {
        BackendController.getDenuncias().enqueue(new Callback<List<Denuncia>>() {
            @Override public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                if(response.body() != null) { denuncias = response.body().toArray(new Denuncia[0]); onResult.Ejecutar(0); }
                else { Helper.Toast("empty body: " + response.toString()); onError.Ejecutar(0); }
            }
            @Override public void onFailure(Call<List<Denuncia>> call, Throwable t) { Helper.Toast("fail: " + t.toString()); onError.Ejecutar(0); }
        });
    }

    private static Notificacion[] notificaciones;
    private static int notificacionActiva = 0;

    public static Notificacion[] getNotificaciones() { return notificaciones; }
    public static Notificacion getNotificacionActiva() { return notificaciones[notificacionActiva]; }
    public static void setNotificacionActiva(int posicion) { notificacionActiva = posicion; }
    public static void setNotificacionActiva(Notificacion notificacion) {
        if(notificaciones == null){ notificacionActiva = 0; return; }
        int i = 0;
        boolean hit = false;
        while (i < notificaciones.length && !hit) {
            if(notificacion.getId() == notificaciones[i].getId()) { notificacionActiva = i; hit = true; }
            i++;
        }
    }
    public static void TryUpdateNotificaciones(Accion onResult, Accion onError) {
        if(getSesion().GetRol() != USUARIOS_ROLES.INVITADO) {
            BackendController.getNotificaciones(getSesion().GetId()).enqueue(new Callback<List<Notificacion>>() {
                @Override public void onResponse(Call<List<Notificacion>> call, Response<List<Notificacion>> response) {
                    if (response.body() != null) { notificaciones = response.body().toArray(new Notificacion[0]); onResult.Ejecutar(0); }
                    else { Helper.Toast("empty body: " + response.toString()); onError.Ejecutar(0); }
                }
                @Override public void onFailure(Call<List<Notificacion>> call, Throwable t) { Helper.Toast("fail: " + t.toString()); onError.Ejecutar(0); }
            });
        } else { onError.Ejecutar(0); }
    }

    public static void TryUpdateAll(Accion onResult, Accion onError) {
        Accion updateNotificaciones = new Accion() { @Override public void Ejecutar(Object in) {
            TryUpdateNotificaciones(
                    new Accion() { @Override public void Ejecutar(Object in) { onResult.Ejecutar(0); } },
                    new Accion() { @Override public void Ejecutar(Object in) { onError.Ejecutar(0); } }
            );
        } };
        Accion updateDenuncias = new Accion() { @Override public void Ejecutar(Object in) {
            TryUpdateDenuncias(
                    new Accion() { @Override public void Ejecutar(Object in) { updateNotificaciones.Ejecutar(0); } },
                    new Accion() { @Override public void Ejecutar(Object in) { updateNotificaciones.Ejecutar(0); } }
            );
        } };
        Accion updateReclamos = new Accion() { @Override public void Ejecutar(Object in) {
            TryUpdateReclamos(
                    new Accion() { @Override public void Ejecutar(Object in) { updateDenuncias.Ejecutar(0); } },
                    new Accion() { @Override public void Ejecutar(Object in) { updateDenuncias.Ejecutar(0); } }
            );
        } };
        TryUpdatePublicaciones(
                new Accion() { @Override public void Ejecutar(Object in) { updateReclamos.Ejecutar(0); } },
                new Accion() { @Override public void Ejecutar(Object in) { updateReclamos.Ejecutar(0); } }
                );
    }

}
