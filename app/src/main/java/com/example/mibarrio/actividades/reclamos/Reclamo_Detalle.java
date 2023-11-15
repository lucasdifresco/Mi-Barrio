package com.example.mibarrio.actividades.reclamos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mibarrio.R;
import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.enumerables.INSTALACION_CATEGORIA;
import com.example.mibarrio.enumerables.PUBLICACION_TIPO;
import com.example.mibarrio.enumerables.RECLAMOS_ESTADO;
import com.example.mibarrio.enumerables.TIPOS_FORMULARIOS;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Notificacion;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.logica.Reclamo;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.Helper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reclamo_Detalle extends AppCompatActivity {

    private Reclamo reclamo;
    private ArrayList<Bitmap> imagenes;
    private int index;
    private ImageView imagen;
    private Button aceptar;
    private Button rechazar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo_detalle);

        aceptar = (Button) findViewById(R.id.aceptar);
        rechazar = (Button) findViewById(R.id.rechazar);

        Actualizar();

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
                loadingScreen.setVisibility(View.VISIBLE);

                reclamo.setEstado(RECLAMOS_ESTADO.ACEPTADO.toInt());
                BackendController.uploadReclamo(reclamo).enqueue(new Callback<Reclamo>() {
                    @Override public void onResponse(Call<Reclamo> call, Response<Reclamo> response) {
                        if(response != null){
                            Datos.TryUpdateReclamos(new Accion() {
                                @Override public void Ejecutar(Object in) {
                                    Actualizar();
                                    BackendController.uploadNotificacion(new Notificacion((int)reclamo.getUsuario().getId(), reclamo.getId(), TIPOS_FORMULARIOS.RECLAMO.toInt(), "Reclamo #" + reclamo.getId() + " rechazado.")).enqueue(new Callback<Notificacion>() {
                                        @Override public void onResponse(Call<Notificacion> call, Response<Notificacion> response) {
                                            if(response == null){ Helper.Toast("empty body: " + response.toString()); }
                                            loadingScreen.setVisibility(View.GONE);
                                        }

                                        @Override public void onFailure(Call<Notificacion> call, Throwable t) {
                                            Helper.Toast("fail: " + t.toString());
                                            loadingScreen.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }, new Accion() {
                                @Override public void Ejecutar(Object in) {
                                    loadingScreen.setVisibility(View.GONE);
                                }
                            });
                        }else{
                            Helper.Toast("empty body: " + response.toString());
                            loadingScreen.setVisibility(View.GONE);
                        }
                    }

                    @Override public void onFailure(Call<Reclamo> call, Throwable t) {
                        Helper.Toast("fail: " + t.toString());
                        loadingScreen.setVisibility(View.GONE);
                    }
                });
            }
        });
        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
                loadingScreen.setVisibility(View.VISIBLE);

                reclamo.setEstado(RECLAMOS_ESTADO.RECHAZADO.toInt());
                BackendController.uploadReclamo(reclamo).enqueue(new Callback<Reclamo>() {
                    @Override public void onResponse(Call<Reclamo> call, Response<Reclamo> response) {
                        if(response != null){
                            Datos.TryUpdateReclamos(new Accion() {
                                @Override public void Ejecutar(Object in) {
                                    Actualizar();
                                    BackendController.uploadNotificacion(new Notificacion((int)reclamo.getUsuario().getId(), reclamo.getId(), TIPOS_FORMULARIOS.RECLAMO.toInt(), "Reclamo #" + reclamo.getId() + " rechazado.")).enqueue(new Callback<Notificacion>() {
                                        @Override public void onResponse(Call<Notificacion> call, Response<Notificacion> response) {
                                            if(response == null){ Helper.Toast("empty body: " + response.toString()); }
                                            loadingScreen.setVisibility(View.GONE);
                                        }

                                        @Override public void onFailure(Call<Notificacion> call, Throwable t) {
                                            Helper.Toast("fail: " + t.toString());
                                            loadingScreen.setVisibility(View.GONE);
                                        }
                                    });

                                }
                            }, new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); }
                            });
                        }else{
                            Helper.Toast("empty body: " + response.toString());
                            loadingScreen.setVisibility(View.GONE);
                        }
                    }

                    @Override public void onFailure(Call<Reclamo> call, Throwable t) {
                        Helper.Toast("fail: " + t.toString());
                        loadingScreen.setVisibility(View.GONE);
                    }
                });
            }
        });

        ImageButton reclamosDetallesFotoAnterior = (ImageButton) findViewById(R.id.reclamosDetallesFotoAnterior);
        ImageButton reclamosDetallesFotoSiguiente = (ImageButton) findViewById(R.id.reclamosDetallesFotoSiguiente);
        reclamosDetallesFotoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { if (index > 0) { index--; imagen.setImageBitmap(imagenes.get(index)); } }
        });
        reclamosDetallesFotoSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { if (index < imagenes.size() - 1) { index++; imagen.setImageBitmap(imagenes.get(index)); } }
        });
        imagen = (ImageView) findViewById(R.id.imagen);
        index = 0;
        imagenes = reclamo.GetBitmapArray();
        if (imagenes == null || imagenes.size() == 0) { imagenes = new ArrayList<Bitmap>(); }
        else { imagen.setImageBitmap(imagenes.get(0)); }
    }
    @Override protected void onResume() {
        super.onResume();
        Actualizar();
    }

    private void Actualizar(){

        reclamo = Datos.getReclamoActivo();

        aceptar.setVisibility(View.GONE);
        rechazar.setVisibility(View.GONE);

        TextView tituloReclamo = (TextView) findViewById(R.id.tituloReclamosDetalle);
        TextView estado = (TextView) findViewById(R.id.estado);
        TextView titulo = (TextView) findViewById(R.id.titulo);
        TextView categoria = (TextView) findViewById(R.id.categoria);
        TextView descripcion = (TextView) findViewById(R.id.descripcion);
        TextView tituloUbicacion = (TextView) findViewById(R.id.tituloUbicacion);
        TextView ubicacion = (TextView) findViewById(R.id.ubicacion);

        ubicacion.setVisibility(View.GONE);
        tituloUbicacion.setVisibility(View.GONE);

        tituloReclamo.setText("Reclamo #" + reclamo.getId());
        estado.setText(RECLAMOS_ESTADO.getValueById(reclamo.getEstado()));
        titulo.setText(reclamo.getTitulo());
        categoria.setText(INSTALACION_CATEGORIA.getValueById(reclamo.getCategoria()));
        descripcion.setText(reclamo.getDescripcion());

        if(reclamo.getUbicacion() != ""){
            ubicacion.setText(reclamo.getUbicacion());
            ubicacion.setVisibility(View.VISIBLE);
            tituloUbicacion.setVisibility(View.VISIBLE);
        }

        if(reclamo.getEstado() == RECLAMOS_ESTADO.SIN_REVISAR.toInt() && Datos.getSesion().GetRol() == USUARIOS_ROLES.INSPECTOR){
            aceptar.setVisibility(View.VISIBLE);
            rechazar.setVisibility(View.VISIBLE);
        }

    }
}