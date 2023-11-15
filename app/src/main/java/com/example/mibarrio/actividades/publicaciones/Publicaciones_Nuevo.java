package com.example.mibarrio.actividades.publicaciones;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mibarrio.actividades.reclamos.Reclamos_Nuevo;
import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.enumerables.PUBLICACION_TIPO;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Imagen;
import com.example.mibarrio.actividades.inicio.Inicio_Home;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.R;
import com.example.mibarrio.backend.Servicios;
import com.example.mibarrio.logica.Usuario;
import com.example.mibarrio.soporte.Helper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Publicaciones_Nuevo extends AppCompatActivity {

    private Publicacion publicacion;
    private ArrayList<Bitmap> imagenes;
    private int index;
    ImageView imagen;
    private int maxImageAmount = 5;
    private ActivityResultLauncher<Intent> cameraLucnher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");

                        if(index == imagenes.size() - 1){ imagenes.add(bitmap); }
                        else { imagenes.add(index, bitmap); }
                        imagen.setImageBitmap(imagenes.get(index));
                    }
                }
            });

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones_nuevo);
        imagenes = new ArrayList<Bitmap>();
        index = 0;

        Button botonGenerarPublicacion = (Button) findViewById(R.id.botonGenerarPublicacion);
        ImageButton botonPublicacionNuevoCapturarFoto = (ImageButton) findViewById(R.id.botonPublicacionNuevoCapturarFoto);
        ImageButton botonPublicacionNuevoEliminarFoto = (ImageButton) findViewById(R.id.botonPublicacionNuevoEliminarFoto);
        ImageButton botonPublicacionNuevoFotoAnterior = (ImageButton) findViewById(R.id.botonPublicacionNuevoFotoAnterior);
        ImageButton botonPublicacionNuevoFotoSiguiente = (ImageButton) findViewById(R.id.botonPublicacionNuevoFotoSiguiente);

        imagen = (ImageView) findViewById(R.id.imagen);
        EditText nombre = (EditText) findViewById(R.id.nombre);
        EditText direccion = (EditText) findViewById(R.id.direccion);
        EditText telefono = (EditText) findViewById(R.id.telefono);
        EditText descripcion = (EditText) findViewById(R.id.descripcion);
        RadioButton servicio = (RadioButton) findViewById(R.id.radioServicio);
        RadioButton producto = (RadioButton) findViewById(R.id.radioProducto);
        Switch privada = (Switch) findViewById(R.id.privada);

        if(getIntent().hasExtra("publicacion")) {
            publicacion = (Publicacion) getIntent().getSerializableExtra("publicacion");

            nombre.setText(publicacion.getNombre());
            direccion.setText(publicacion.getDireccion());
            telefono.setText(publicacion.getTelefono());
            descripcion.setText(publicacion.getDetalle());
            privada.setChecked(publicacion.isPrivada());

            if(publicacion.getCategoria() == PUBLICACION_TIPO.PRODUCTO.toInt()) { servicio.setChecked(false); producto.setChecked(true); }
            else{ servicio.setChecked(true); producto.setChecked(false); }

            imagenes = publicacion.GetBitmapArray();
            if(imagenes == null || imagenes.size() == 0) { imagenes = new ArrayList<Bitmap>(); }
            else { index = imagenes.size() - 1; imagen.setImageBitmap(imagenes.get(index)); }
        }

        botonGenerarPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                int tipo = 0;
                if (servicio.isChecked()) { tipo = PUBLICACION_TIPO.SERVICIO.toInt(); }
                else { tipo = PUBLICACION_TIPO.PRODUCTO.toInt(); }

                if (publicacion != null){
                    publicacion = new Publicacion(
                            publicacion.getId(),
                            nombre.getText().toString(),
                            direccion.getText().toString(),
                            telefono.getText().toString(),
                            descripcion.getText().toString(),
                            tipo,
                            new Usuario(Datos.getSesion().GetId()),
                            privada.isChecked(),
                            getImagenes()
                    );
                } else {
                    publicacion = new Publicacion(
                            nombre.getText().toString(),
                            direccion.getText().toString(),
                            telefono.getText().toString(),
                            descripcion.getText().toString(),
                            tipo,
                            new Usuario(Datos.getSesion().GetId()),
                            privada.isChecked(),
                            getImagenes()
                    );
                }
                if (Helper.isWifiOn()) { TryPublicar(); }
                else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE: TryPublicar(); break;
                                case DialogInterface.BUTTON_NEGATIVE: Datos.addPublicacionPendiente(publicacion); finish(); break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Publicaciones_Nuevo.this);
                    builder.setMessage("¡Ups! No dispones de una conexión wifi... ¿Deseas continuar con la carga del reclamo con la red de datos móviles?")
                            .setPositiveButton("Utilizar los datos móviles", dialogClickListener)
                            .setNegativeButton("Guardar y Generar con wifi", dialogClickListener).show();

                }
            }
        });
        botonPublicacionNuevoCapturarFoto.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(imagenes.size() < maxImageAmount) {
                    if (ContextCompat.checkSelfPermission(Publicaciones_Nuevo.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraLucnher.launch(intent);
                    } else {
                        ActivityCompat.requestPermissions(Publicaciones_Nuevo.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }
            }
        });
        botonPublicacionNuevoEliminarFoto.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(imagenes.size() > 0)
                {
                    imagenes.remove(index);
                    if(index > 0 ) { index--; }
                    if(imagenes.size() > 0) { imagen.setImageBitmap(imagenes.get(index)); }
                    else { imagen.setImageBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444)); }
                }
            }
        });
        botonPublicacionNuevoFotoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(index > 0)
                {
                    index--;
                    imagen.setImageBitmap(imagenes.get(index));
                }
            }
        });
        botonPublicacionNuevoFotoSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(index < maxImageAmount && index < imagenes.size() - 1)
                {
                    index++;
                    imagen.setImageBitmap(imagenes.get(index));
                }
            }
        });
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLucnher.launch(intent);
            } else { Toast.makeText(this, "Se necesita dar permiso a la aplicacion para utilizar la camara.", Toast.LENGTH_LONG).show(); }
        }
    }

    private void TryPublicar() {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);

        BackendController.uploadPublicacion(publicacion).enqueue(new Callback<Publicacion>() {
            @Override public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                if(response.body() != null){
                    startActivity(new Intent(Publicaciones_Nuevo.this, Publicaciones_Mensaje.class));
                    finish();
                } else {
                    loadingScreen.setVisibility(View.GONE);
                    Helper.Toast("empty body: " + response.toString() + " - Item: " + publicacion.getCategoria());
                }
            }
            @Override public void onFailure(Call<Publicacion> call, Throwable t) {
                loadingScreen.setVisibility(View.GONE);
                Helper.Toast("fail: " + t.toString());
            }
        });
    }
    private void TryGuardarImagen(Imagen imagen) {
        BackendController.uploadImagen(imagen).enqueue(new Callback<Imagen>() {
            @Override public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                if(response.body() != null) { Helper.Toast("Imagen Cargada: " + response.toString()); }
                else { Helper.Toast("Imagen sin cargar: " + response.toString()); }
            }
            @Override public void onFailure(Call<Imagen> call, Throwable t) { Helper.Toast("fail: " + t.toString()); }
        });
    }

    private Imagen[] getImagenes() {
        ArrayList<Imagen> arreglo = new ArrayList<Imagen>();
        for(int i = 0; i < imagenes.size(); i++) {
            if(publicacion != null) { arreglo.add(new Imagen(publicacion.getId(), publicacion.getUsuario().getId(), "publicacion.png", imagenes.get(i))); }
            else{ arreglo.add(new Imagen("publicacion.png", imagenes.get(i))); }
        }
        return arreglo.toArray(new Imagen[0]);
    }
}