package com.example.mibarrio.actividades.instalaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mibarrio.R;
import com.example.mibarrio.actividades.reclamos.Reclamos_Nuevo;
import com.example.mibarrio.enumerables.INSTALACION_CATEGORIA;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Instalacion;
import com.example.mibarrio.logica.filtros.FiltroInstalaciones;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.CustomListAdapter;
import com.example.mibarrio.soporte.CustomListAdapterSinImagen;
import com.example.mibarrio.soporte.Helper;
import com.example.mibarrio.soporte.ListViewItem;

import java.util.ArrayList;

public class Instalaciones_Listado extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalaciones_listado);

        TryGetDatos();

        ImageButton botonFiltroInstalaciones = (ImageButton) findViewById(R.id.botonFiltroInstalaciones);
        ImageButton botonInstalacionesNuevoReclamo = (ImageButton) findViewById(R.id.botonInstalacionesNuevoReclamo);

        if (Datos.getSesion().GetRol() == USUARIOS_ROLES.VECINO) { botonInstalacionesNuevoReclamo.setVisibility(View.VISIBLE); }
        else { botonInstalacionesNuevoReclamo.setVisibility(View.INVISIBLE); }

        botonFiltroInstalaciones.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Instalaciones_Listado.this, Instalaciones_Filtro.class)); }
        });
        botonInstalacionesNuevoReclamo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Instalaciones_Listado.this, Reclamos_Nuevo.class)); }
        });
    }
    @Override protected void onResume() {
        super.onResume();
        TryGetDatos();
    }

    private void TryGetDatos() {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);
        Datos.TryUpdateInstalaciones(
                new Accion() { @Override public void Ejecutar(Object in) { PoblarLista(); loadingScreen.setVisibility(View.GONE); } },
                new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); } }
                );
    }
    private void PoblarLista() {
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
        FiltroInstalaciones filtro = Datos.getFiltroInstalaciones();
        Instalacion[] instalaciones = Datos.getInstalaciones();
        for(int i = 0; i < instalaciones.length; i++)
        {
            if(filtro != null)
            {
                if(filtro.calles || filtro.plazas || filtro.oficinasPublicas || filtro.escuelasPrimarias || filtro.escuelasSecundarias || filtro.comedoresEscolares || filtro.museos || filtro.campingMunicipal || filtro.otros) {
                    if (!filtro.calles && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.CALLES.toInt()) { continue; }
                    if (!filtro.plazas && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.PLAZAS.toInt()) { continue; }
                    if (!filtro.oficinasPublicas && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.OFICINAS_PUBLICAS.toInt()) { continue; }
                    if (!filtro.escuelasPrimarias && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.ESCUELAS_PRIMARIAS.toInt()) { continue; }
                    if (!filtro.escuelasSecundarias && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.ESCUELAS_SECUNDARIAS.toInt()) { continue; }
                    if (!filtro.comedoresEscolares && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.COMEDORES_ESCOLARES.toInt()) { continue; }
                    if (!filtro.museos && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.MUSEOS.toInt()) { continue; }
                    if (!filtro.campingMunicipal && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.CAMPING_MUNICIPALES.toInt()) { continue; }
                    if (!filtro.otros && instalaciones[i].getCategoria() == INSTALACION_CATEGORIA.OTROS.toInt()) { continue; }
                }
            }
            items.add(new ListViewItem(
                    instalaciones[i].getId(),
                    instalaciones[i].getNombre(),
                    instalaciones[i].getTitular(),
                    null,
                    Instalaciones_Detalle.class
            ));
        }
        ListView lista = (ListView) findViewById(R.id.lista);
        CustomListAdapterSinImagen adaptador = new CustomListAdapterSinImagen(
                this,
                R.layout.custom_list_view_item_sin_imagen,
                items,
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) { Datos.setInstalacionActiva(in);} }
        );
        lista.setAdapter(adaptador);
    }
}