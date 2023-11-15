package com.example.mibarrio.actividades.publicaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mibarrio.enumerables.PUBLICACION_TIPO;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.R;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;

import java.util.ArrayList;

public class Publicaciones_Detalle extends AppCompatActivity {

    private Publicacion publicacion;
    private ArrayList<Bitmap> imagenes;
    private int index;
    ImageView imagen;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones_detalle);

        ImageButton botonEditarPublicacion = (ImageButton) findViewById(R.id.botonEditarPublicacion);
        ImageButton botonPublicacionDetalleFotoAnterior = (ImageButton) findViewById(R.id.botonPublicacionDetalleFotoAnterior);
        ImageButton botonPublicacionDetalleFotoSiguiente = (ImageButton) findViewById(R.id.botonPublicacionDetalleFotoSiguiente);

        Actualizar();

        if (Datos.getSesion().GetRol() == USUARIOS_ROLES.VECINO && publicacion.getUsuario().getId() == Datos.getSesion().GetId()) { botonEditarPublicacion.setVisibility(View.VISIBLE); }
        else { botonEditarPublicacion.setVisibility(View.INVISIBLE); }

        botonEditarPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Publicaciones_Detalle.this, Publicaciones_Nuevo.class);
                intent.putExtra("publicacion", publicacion);
                startActivity(intent);
            }
        });
        botonPublicacionDetalleFotoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { if (index > 0) { index--; imagen.setImageBitmap(imagenes.get(index)); } }
        });
        botonPublicacionDetalleFotoSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { if (index < imagenes.size() - 1) { index++; imagen.setImageBitmap(imagenes.get(index)); } }
        });

        imagen = (ImageView) findViewById(R.id.imagen);
        index = 0;
        imagenes = publicacion.GetBitmapArray();
        if (imagenes == null || imagenes.size() == 0) {
            imagenes = new ArrayList<Bitmap>();
        } else { imagen.setImageBitmap(imagenes.get(0)); }

    }
    @Override protected void onResume() {
        super.onResume();
        Actualizar();
    }

    private void Actualizar(){

        publicacion = Datos.getPublicacionActiva();

        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView tipo = (TextView) findViewById(R.id.tipo);
        TextView direccion = (TextView) findViewById(R.id.direccion);
        TextView telefono = (TextView) findViewById(R.id.telefono);
        TextView descripcion = (TextView) findViewById(R.id.descripcion);

        nombre.setText(publicacion.getNombre());
        tipo.setText(PUBLICACION_TIPO.getValueById(publicacion.getCategoria()));
        direccion.setText(publicacion.getDireccion());
        telefono.setText(publicacion.getTelefono());
        descripcion.setText(publicacion.getDetalle());

    }
}