package com.example.mibarrio.logica;

import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.soporte.Helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalData implements Serializable {

    private Sesion sesion;
    public Sesion getSesion() {
        if(sesion == null) {setSesion(new Sesion(0, USUARIOS_ROLES.INVITADO, ""));}
        return sesion;
    }
    public void setSesion(Sesion newSesion) { sesion = newSesion; Datos.saveLocalData(); }


    private List<Publicacion> publicacionesPendientes;
    private static int indexPublicacionPendiente = 0;
    public void addPublicacionPendiente(Publicacion publicacion) {
        if(publicacionesPendientes == null) { publicacionesPendientes = new ArrayList<Publicacion>(); }
        publicacionesPendientes.add(publicacion);
        Datos.saveLocalData();
    }
    public void TryUploadPublicacionesPendientes() {
        if(publicacionesPendientes != null && publicacionesPendientes.size() != 0) {
            indexPublicacionPendiente = publicacionesPendientes.size() - 1;
            while(indexPublicacionPendiente > 0 && publicacionesPendientes.size() != 0) {
                BackendController.uploadPublicacion(publicacionesPendientes.get(indexPublicacionPendiente)).enqueue(new Callback<Publicacion>() {
                    @Override public void onResponse(Call<Publicacion> call, Response<Publicacion> response)
                    {
                        if (response.body() != null) { publicacionesPendientes.remove(indexPublicacionPendiente); Datos.saveLocalData(); }
                        else { Helper.Toast("empty body: " + response.toString()); }
                    }
                    @Override public void onFailure(Call<Publicacion> call, Throwable t) { Helper.Toast("fail: " + t.toString()); }
                });
                indexPublicacionPendiente--;
            }
        }
    }

    private List<Reclamo> reclamosPendientes;
    private static int indexReclamoPendiente = 0;
    public void addReclamoPendiente(Reclamo reclamo) {
        if(reclamosPendientes == null) { reclamosPendientes = new ArrayList<Reclamo>(); }
        reclamosPendientes.add(reclamo);
        Datos.saveLocalData();
    }
    public void TryUploadReclamosPendientes() {
        if(reclamosPendientes != null && reclamosPendientes.size() != 0) {
            indexReclamoPendiente = reclamosPendientes.size() - 1;
            while(indexReclamoPendiente > 0 && reclamosPendientes.size() != 0) {
                BackendController.uploadReclamo(reclamosPendientes.get(indexReclamoPendiente)).enqueue(new Callback<Reclamo>() {
                    @Override public void onResponse(Call<Reclamo> call, Response<Reclamo> response)
                    {
                        if (response.body() != null) { reclamosPendientes.remove(indexReclamoPendiente); Datos.saveLocalData(); }
                        else { Helper.Toast("empty body: " + response.toString()); }
                    }
                    @Override public void onFailure(Call<Reclamo> call, Throwable t) { Helper.Toast("fail: " + t.toString()); }
                });
                indexReclamoPendiente--;
            }
        }
    }

    private List<Denuncia> denunciasPendientes;
    private static int indexDenunciaPendiente = 0;
    public void addDenunciaPendiente(Denuncia denuncia) {
        if(denunciasPendientes == null) { denunciasPendientes = new ArrayList<Denuncia>(); }
        denunciasPendientes.add(denuncia);
        Datos.saveLocalData();
    }
    public void TryUploadDenunciasPendientes() {
        if(denunciasPendientes != null && denunciasPendientes.size() != 0) {
            indexDenunciaPendiente = denunciasPendientes.size() - 1;
            while(indexDenunciaPendiente > 0 && denunciasPendientes.size() != 0) {
                BackendController.uploadDenuncia(denunciasPendientes.get(indexDenunciaPendiente)).enqueue(new Callback<Denuncia>() {
                    @Override public void onResponse(Call<Denuncia> call, Response<Denuncia> response)
                    {
                        if (response.body() != null) { denunciasPendientes.remove(indexDenunciaPendiente); Datos.saveLocalData(); }
                        else { Helper.Toast("empty body: " + response.toString()); }
                    }
                    @Override public void onFailure(Call<Denuncia> call, Throwable t) { Helper.Toast("fail: " + t.toString()); }
                });
                indexDenunciaPendiente--;
            }
        }
    }
}
