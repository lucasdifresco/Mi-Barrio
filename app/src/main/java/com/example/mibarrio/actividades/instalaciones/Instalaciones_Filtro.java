package com.example.mibarrio.actividades.instalaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.filtros.FiltroInstalaciones;

public class Instalaciones_Filtro extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalaciones_filtro);

        CheckBox calles = (CheckBox) findViewById(R.id.calles);
        CheckBox plazas = (CheckBox) findViewById(R.id.plazas);
        CheckBox oficinasPublicas = (CheckBox) findViewById(R.id.oficinasPublicas);
        CheckBox escuelasPrimarias = (CheckBox) findViewById(R.id.escuelasPrimarias);
        CheckBox escuelasSecundarias = (CheckBox) findViewById(R.id.escuelasSecundarias);
        CheckBox comedoresEscolares = (CheckBox) findViewById(R.id.comedoresEscolares);
        CheckBox museos = (CheckBox) findViewById(R.id.museos);
        CheckBox campingMunicipal = (CheckBox) findViewById(R.id.campingMunicipal);
        CheckBox otros = (CheckBox) findViewById(R.id.otros);

        FiltroInstalaciones filtro = Datos.getFiltroInstalaciones();

        calles.setChecked(filtro.calles);
        plazas.setChecked(filtro.plazas);
        oficinasPublicas.setChecked(filtro.oficinasPublicas);
        escuelasPrimarias.setChecked(filtro.escuelasPrimarias);
        escuelasSecundarias.setChecked(filtro.escuelasSecundarias);
        comedoresEscolares.setChecked(filtro.comedoresEscolares);
        museos.setChecked(filtro.museos);
        campingMunicipal.setChecked(filtro.campingMunicipal);
        otros.setChecked(filtro.otros);

        Button botonAplicarFiltroInstalaciones = (Button) findViewById(R.id.botonAplicarFiltroInstalaciones);
        botonAplicarFiltroInstalaciones.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                filtro.calles = calles.isChecked();
                filtro.plazas = plazas.isChecked();
                filtro.oficinasPublicas = oficinasPublicas.isChecked();
                filtro.escuelasPrimarias = escuelasPrimarias.isChecked();
                filtro.escuelasSecundarias = escuelasSecundarias.isChecked();
                filtro.comedoresEscolares = comedoresEscolares.isChecked();
                filtro.museos = museos.isChecked();
                filtro.campingMunicipal = campingMunicipal.isChecked();
                filtro.otros = otros.isChecked();

                Datos.setFiltroInstalaciones(filtro);
                finish();
            }
        });
    }
}