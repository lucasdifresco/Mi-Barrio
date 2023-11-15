package com.example.mibarrio.actividades.reclamos;

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
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mibarrio.R;
import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.enumerables.INSTALACION_CATEGORIA;
import com.example.mibarrio.enumerables.RECLAMOS_ESTADO;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Imagen;
import com.example.mibarrio.logica.Instalacion;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.logica.Reclamo;
import com.example.mibarrio.logica.Usuario;
import com.example.mibarrio.soporte.Helper;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reclamos_Nuevo extends AppCompatActivity {

    private Reclamo reclamo;
    private int cat = 0;
    private String ubicacion = "";
    private ArrayList<Bitmap> imagenes;
    private int index;
    private ImageView imagen;
    private int maxImageAmount = 5;
    private ActivityResultLauncher<Intent> cameraLucnher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override public void onActivityResult(ActivityResult result) {
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
        setContentView(R.layout.activity_reclamos_nuevo);
        imagenes = new ArrayList<Bitmap>();
        index = 0;

        imagen = (ImageView) findViewById(R.id.imagen);
        EditText titulo = (EditText) findViewById(R.id.titulo);
        EditText descripcion = (EditText) findViewById(R.id.descripcion);
        Spinner categoria = (Spinner) findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Instalaciones_Categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapter);
        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { cat = i; }
            @Override public void onNothingSelected(AdapterView<?> adapterView) { cat = 0; }
        });

        if(getIntent().hasExtra("instalacion")) {
            Instalacion instalacion = (Instalacion) getIntent().getSerializableExtra("instalacion");
            titulo.setText(instalacion.getNombre());
            cat = instalacion.getCategoria();
            categoria.setSelection(cat);
            ubicacion = instalacion.getUbicacion();
        }

        Button botonGenerarReclamo = (Button) findViewById(R.id.botonGenerarReclamo);
        ImageButton reclamosNuevoCamara = (ImageButton) findViewById(R.id.reclamosNuevoCamara);
        ImageButton reclamosNuevoEliminarFoto = (ImageButton) findViewById(R.id.reclamosNuevoEliminarFoto);
        ImageButton reclamosNuevoFotoAnterior = (ImageButton) findViewById(R.id.reclamosNuevoFotoAnterior);
        ImageButton reclamosNuevoFotoSiguiente = (ImageButton) findViewById(R.id.reclamosNuevoFotoSiguiente);

        botonGenerarReclamo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                reclamo = new Reclamo(
                        titulo.getText().toString(),
                        descripcion.getText().toString(),
                        ubicacion,
                        cat,
                        new Usuario(Datos.getSesion().GetId()),
                        getImagenes(),
                        RECLAMOS_ESTADO.SIN_REVISAR.toInt()
                );

                if (Helper.isWifiOn()) { TryPublicar(); }
                else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE: TryPublicar(); break;
                                case DialogInterface.BUTTON_NEGATIVE: Datos.addReclamoPendiente(reclamo); finish(); break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Reclamos_Nuevo.this);
                    builder.setMessage("¡Ups! No dispones de una conexión wifi... ¿Deseas continuar con la carga del reclamo con la red de datos móviles?")
                            .setPositiveButton("Utilizar los datos móviles", dialogClickListener)
                            .setNegativeButton("Guardar y Generar con wifi", dialogClickListener).show();

                }
            }
        });
        reclamosNuevoCamara.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(imagenes.size() < maxImageAmount) {
                    if (ContextCompat.checkSelfPermission(Reclamos_Nuevo.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraLucnher.launch(intent);
                    } else {
                        ActivityCompat.requestPermissions(Reclamos_Nuevo.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }
            }
        });
        reclamosNuevoEliminarFoto.setOnClickListener(new View.OnClickListener() {
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
        reclamosNuevoFotoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(index > 0)
                {
                    index--;
                    imagen.setImageBitmap(imagenes.get(index));
                }
            }
        });
        reclamosNuevoFotoSiguiente.setOnClickListener(new View.OnClickListener() {
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

        BackendController.uploadReclamo(reclamo).enqueue(new Callback<Reclamo>() {
            @Override
            public void onResponse(Call<Reclamo> call, Response<Reclamo> response) {
                if (response.body() != null) {
                    startActivity(new Intent(Reclamos_Nuevo.this, Reclamos_Mensaje.class));
                    finish();
                } else {
                    loadingScreen.setVisibility(View.GONE);
                    Helper.Toast("empty body: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Reclamo> call, Throwable t) {
                loadingScreen.setVisibility(View.GONE);
                Helper.Toast("fail: " + t.toString());
            }
        });
    }
    private Imagen[] getImagenes() {
        ArrayList<Imagen> arreglo = new ArrayList<Imagen>();
        for(int i = 0; i < imagenes.size(); i++) { arreglo.add(new Imagen( "publicacion.png",imagenes.get(i))); }
        return arreglo.toArray(new Imagen[0]);
    }
}