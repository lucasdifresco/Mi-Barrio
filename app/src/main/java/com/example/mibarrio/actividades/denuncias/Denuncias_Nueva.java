package com.example.mibarrio.actividades.denuncias;

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
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mibarrio.MainActivity;
import com.example.mibarrio.R;
import com.example.mibarrio.actividades.publicaciones.Publicaciones_Detalle;
import com.example.mibarrio.actividades.publicaciones.Publicaciones_Mensaje;
import com.example.mibarrio.actividades.publicaciones.Publicaciones_Nuevo;
import com.example.mibarrio.actividades.reclamos.Reclamos_Nuevo;
import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.enumerables.PUBLICACION_TIPO;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Denuncia;
import com.example.mibarrio.logica.Imagen;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.logica.Usuario;
import com.example.mibarrio.logica.filtros.FiltroPublicaciones;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.CustomListAdapter;
import com.example.mibarrio.soporte.DocumentoListAdapter;
import com.example.mibarrio.soporte.Helper;
import com.example.mibarrio.soporte.ListViewItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Denuncias_Nueva extends AppCompatActivity {

    private Denuncia denuncia;
    private ArrayList<Imagen> imagenes;

    private ActivityResultLauncher<Intent> cameraLucnher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");
                        imagenes.add(new Imagen("documento_"+imagenes.size()+".png", bitmap));
                        PoblarLista();
                    }
                }
            });

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias_nueva);
        imagenes = new ArrayList<Imagen>();

        ConstraintLayout visorImagen = (ConstraintLayout) findViewById(R.id.visorImagen);
        visorImagen.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { visorImagen.setVisibility(View.GONE); } });

        Button botonGenerarDenuncia = (Button) findViewById(R.id.botonGenerarDenuncia);
        ImageButton botonNuevaDenunciaAdjuntar = (ImageButton) findViewById(R.id.botonNuevaDenunciaAdjuntar);

        EditText nombre = (EditText) findViewById(R.id.nombre);
        EditText direccion = (EditText) findViewById(R.id.domicilio);
        EditText descripcion = (EditText) findViewById(R.id.descripcion);
        Switch declaracion = (Switch) findViewById(R.id.declaraciónJurada);

        botonGenerarDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (declaracion.isChecked()) {
                    denuncia = new Denuncia(
                            nombre.getText().toString(),
                            direccion.getText().toString(),
                            descripcion.getText().toString(),
                            declaracion.isChecked(),
                            imagenes.toArray(new Imagen[0]),
                            new Usuario(Datos.getSesion().GetId())
                    );
                    if (Helper.isWifiOn()) { TryPublicar(); }
                    else {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        TryPublicar();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        Datos.addDenunciaPendiente(denuncia);
                                        finish();
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(Denuncias_Nueva.this);
                        builder.setMessage("¡Ups! No dispones de una conexión wifi... ¿Deseas continuar con la carga del reclamo con la red de datos móviles?")
                                .setPositiveButton("Utilizar los datos móviles", dialogClickListener)
                                .setNegativeButton("Guardar y Generar con wifi", dialogClickListener).show();

                    }
                }else {
                    TextView tituloDeclaracion = (TextView) findViewById(R.id.tituloDeclaracion);
                    tituloDeclaracion.setTextColor(Color.RED);
                    Helper.Toast("Para generar una denuncia debe leer y aceptar la declaración jurada");
                }
            }
        });
        botonNuevaDenunciaAdjuntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(Denuncias_Nueva.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraLucnher.launch(intent);
                } else {
                    ActivityCompat.requestPermissions(Denuncias_Nueva.this, new String[]{Manifest.permission.CAMERA}, 1);
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

    public void EliminarDocumento(int index){
        if(imagenes != null && imagenes.size() > index ){ imagenes.remove(index); }
        PoblarLista();
    }

    private void TryPublicar() {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);

        BackendController.uploadDenuncia(denuncia).enqueue(new Callback<Denuncia>() {
            @Override public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                if(response.body() != null){
                    startActivity(new Intent(Denuncias_Nueva.this, Denuncias_Mensaje.class));
                    finish();
                } else {
                    loadingScreen.setVisibility(View.GONE);
                    Helper.Toast("empty body: " + response.toString());
                }
            }
            @Override public void onFailure(Call<Denuncia> call, Throwable t) {
                loadingScreen.setVisibility(View.GONE);
                Helper.Toast("fail: " + t.toString());
            }
        });
    }
    private void PoblarLista() {
        if(imagenes == null) { imagenes = new ArrayList<Imagen>(); }
        ListView lista = (ListView) findViewById(R.id.lista);
        DocumentoListAdapter adaptador = new DocumentoListAdapter(
                this,
                R.layout.documento_list_view,
                imagenes,
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) {
                    ConstraintLayout visorImagen = (ConstraintLayout) findViewById(R.id.visorImagen);
                    TextView titulo = (TextView) findViewById(R.id.titulo);
                    titulo.setText(imagenes.get(in).getNombre());
                    ImageView imagen = (ImageView) findViewById(R.id.imagen);
                    imagen.setImageBitmap(imagenes.get(in).ToBitmap());
                    visorImagen.setVisibility(View.VISIBLE);

                } },
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) {
                    EliminarDocumento(in);
                } }, true
        );
        lista.setAdapter(adaptador);
    }
}