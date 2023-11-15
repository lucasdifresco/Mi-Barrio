package com.example.mibarrio.actividades.publicaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.soporte.Accion;

public class Publicaciones_Mensaje extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones_mensaje);

        Button botonPublicacionesMensaje = (Button) findViewById(R.id.botonPublicacionesMensaje);



        botonPublicacionesMensaje.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
                loadingScreen.setVisibility(View.VISIBLE);
                Datos.TryUpdatePublicaciones(
                        new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); finish(); } },
                        new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); } }
                        );
            }
        });
    }
}