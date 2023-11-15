package com.example.mibarrio.actividades.reclamos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mibarrio.R;
import com.example.mibarrio.enumerables.INSTALACION_CATEGORIA;
import com.example.mibarrio.enumerables.RECLAMOS_ESTADO;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Reclamo;
import com.example.mibarrio.logica.filtros.FiltroReclamos;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.CustomListAdapter;
import com.example.mibarrio.soporte.ListViewItem;

import java.util.ArrayList;

public class Reclamos_Listado extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamos_listado);

        TryGetDatos();

        ImageButton botonFiltroReclamos = (ImageButton) findViewById(R.id.botonFiltroReclamos);
        ImageButton botonReclamosListadoNuevoReclamo = (ImageButton) findViewById(R.id.botonReclamosListadoNuevoReclamo);

        if (Datos.getSesion().GetRol() == USUARIOS_ROLES.VECINO) { botonReclamosListadoNuevoReclamo.setVisibility(View.VISIBLE); }
        else { botonReclamosListadoNuevoReclamo.setVisibility(View.INVISIBLE); }

        botonFiltroReclamos.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Reclamos_Listado.this, Reclamos_Filtro.class)); }
        });
        botonReclamosListadoNuevoReclamo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Reclamos_Listado.this, Reclamos_Nuevo.class)); }
        });
    }
    @Override protected void onResume() {
        super.onResume();
        TryGetDatos();
    }

    private void TryGetDatos() {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);
        Datos.TryUpdateReclamos(
                new Accion() { @Override public void Ejecutar(Object in) { PoblarLista(); loadingScreen.setVisibility(View.GONE); } },
                new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); } }
        );
    }
    private void PoblarLista() {
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
        FiltroReclamos filtro = Datos.getFiltroReclamos();
        Reclamo[] reclamos = Datos.getReclamos();
        for(int i = 0; i < reclamos.length; i++)
        {
            if(filtro != null)
            {
                if(filtro.calles || filtro.plazas || filtro.oficinasPublicas || filtro.escuelasPrimarias || filtro.escuelasSecundarias || filtro.comedoresEscolares || filtro.museos || filtro.campingMunicipal || filtro.otros) {
                    if (!filtro.calles && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.CALLES.toInt()) { continue; }
                    if (!filtro.plazas && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.PLAZAS.toInt()) { continue; }
                    if (!filtro.oficinasPublicas && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.OFICINAS_PUBLICAS.toInt()) { continue; }
                    if (!filtro.escuelasPrimarias && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.ESCUELAS_PRIMARIAS.toInt()) { continue; }
                    if (!filtro.escuelasSecundarias && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.ESCUELAS_SECUNDARIAS.toInt()) { continue; }
                    if (!filtro.comedoresEscolares && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.COMEDORES_ESCOLARES.toInt()) { continue; }
                    if (!filtro.museos && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.MUSEOS.toInt()) { continue; }
                    if (!filtro.campingMunicipal && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.CAMPING_MUNICIPALES.toInt()) { continue; }
                    if (!filtro.otros && reclamos[i].getCategoria() == INSTALACION_CATEGORIA.OTROS.toInt()) { continue; }
                }
                if(filtro.aceptado || filtro.rechazado || filtro.sinRevisar) {
                    if (!filtro.aceptado && reclamos[i].getEstado() == RECLAMOS_ESTADO.ACEPTADO.toInt()) { continue; }
                    if (!filtro.rechazado && reclamos[i].getEstado() == RECLAMOS_ESTADO.RECHAZADO.toInt()) { continue; }
                    if (!filtro.sinRevisar && reclamos[i].getEstado() == RECLAMOS_ESTADO.SIN_REVISAR.toInt()) { continue; }
                }
                if(filtro.misReclamos && reclamos[i].getUsuario().getId() != Datos.getSesion().GetId()) { continue; }
            }
            items.add(new ListViewItem(
                    reclamos[i].getId(),
                    reclamos[i].getTitulo(),
                    reclamos[i].getDescripcion(),
                    reclamos[i].GetFirstImage(),
                    Reclamo_Detalle.class
            ));
        }
        ListView lista = (ListView) findViewById(R.id.lista);
        CustomListAdapter adaptador = new CustomListAdapter(
                this,
                R.layout.custom_list_view_item,
                items,
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) { Datos.setReclamoActivo(in);} }
        );
        lista.setAdapter(adaptador);
    }
}