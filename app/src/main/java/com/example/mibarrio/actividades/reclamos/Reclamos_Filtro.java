package com.example.mibarrio.actividades.reclamos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.filtros.FiltroPublicaciones;
import com.example.mibarrio.logica.filtros.FiltroReclamos;

public class Reclamos_Filtro extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamos_filtro);

        Switch misReclamos = (Switch) findViewById(R.id.misReclamos);
        CheckBox aceptados = (CheckBox) findViewById(R.id.aceptados);
        CheckBox rechazados = (CheckBox) findViewById(R.id.rechazados);
        CheckBox sinRevisar = (CheckBox) findViewById(R.id.sinRevisar);
        CheckBox calles = (CheckBox) findViewById(R.id.calles);
        CheckBox plazas = (CheckBox) findViewById(R.id.plazas);
        CheckBox oficinasPublicas = (CheckBox) findViewById(R.id.oficinasPublicas);
        CheckBox escuelasPrimarias = (CheckBox) findViewById(R.id.escuelasPrimarias);
        CheckBox escuelasSecundarias = (CheckBox) findViewById(R.id.escuelasSecundarias);
        CheckBox comedoresEscolares = (CheckBox) findViewById(R.id.comedoresEscolares);
        CheckBox museos = (CheckBox) findViewById(R.id.museos);
        CheckBox campingMunicipal = (CheckBox) findViewById(R.id.campingMunicipal);
        CheckBox otros = (CheckBox) findViewById(R.id.otros);

        FiltroReclamos filtro = Datos.getFiltroReclamos();

        misReclamos.setChecked(filtro.misReclamos);
        aceptados.setChecked(filtro.aceptado);
        rechazados.setChecked(filtro.rechazado);
        sinRevisar.setChecked(filtro.sinRevisar);
        calles.setChecked(filtro.calles);
        plazas.setChecked(filtro.plazas);
        oficinasPublicas.setChecked(filtro.oficinasPublicas);
        escuelasPrimarias.setChecked(filtro.escuelasPrimarias);
        escuelasSecundarias.setChecked(filtro.escuelasSecundarias);
        comedoresEscolares.setChecked(filtro.comedoresEscolares);
        museos.setChecked(filtro.museos);
        campingMunicipal.setChecked(filtro.campingMunicipal);
        otros.setChecked(filtro.otros);

        Button botonAplicarFiltroReclamos = (Button) findViewById(R.id.botonAplicarFiltroReclamos);
        botonAplicarFiltroReclamos.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                filtro.misReclamos = misReclamos.isChecked();
                filtro.aceptado = aceptados.isChecked();
                filtro.rechazado = rechazados.isChecked();
                filtro.sinRevisar = sinRevisar.isChecked();
                filtro.calles = calles.isChecked();
                filtro.plazas = plazas.isChecked();
                filtro.oficinasPublicas = oficinasPublicas.isChecked();
                filtro.escuelasPrimarias = escuelasPrimarias.isChecked();
                filtro.escuelasSecundarias = escuelasSecundarias.isChecked();
                filtro.comedoresEscolares = comedoresEscolares.isChecked();
                filtro.museos = museos.isChecked();
                filtro.campingMunicipal = campingMunicipal.isChecked();
                filtro.otros = otros.isChecked();

                Datos.setFiltroReclamos(filtro);
                finish();
            }
        });
    }
}