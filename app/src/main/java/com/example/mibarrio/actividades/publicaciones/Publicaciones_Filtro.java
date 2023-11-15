package com.example.mibarrio.actividades.publicaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.filtros.FiltroPublicaciones;
import com.example.mibarrio.R;

public class Publicaciones_Filtro extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones_filtro);

        EditText palabras = (EditText) findViewById(R.id.palabrasClave);
        Switch misPublicaciones = (Switch) findViewById(R.id.misPublicaciones);
        CheckBox servicios = (CheckBox) findViewById(R.id.servicios);
        CheckBox productos = (CheckBox) findViewById(R.id.productos);

        FiltroPublicaciones filtro = Datos.getFiltroPublicaciones();

        palabras.setText(filtro.palabrasClave);
        misPublicaciones.setChecked(filtro.misPublicaciones);
        servicios.setChecked(filtro.servicios);
        productos.setChecked(filtro.productos);

        Button botonAplicarFiltroPublicaciones = (Button) findViewById(R.id.botonAplicarFiltroPublicaciones);
        botonAplicarFiltroPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                filtro.palabrasClave = palabras.getText().toString();
                filtro.misPublicaciones = misPublicaciones.isChecked();
                filtro.servicios = servicios.isChecked();
                filtro.productos = productos.isChecked();

                Datos.setFiltroPublicaciones(filtro);

                finish();
            }
        });
    }
}