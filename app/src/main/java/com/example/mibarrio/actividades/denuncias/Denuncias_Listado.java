package com.example.mibarrio.actividades.denuncias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Denuncia;
import com.example.mibarrio.logica.filtros.FiltroDenuncias;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.CustomListAdapterSinImagen;
import com.example.mibarrio.soporte.ListViewItem;

import java.util.ArrayList;

public class Denuncias_Listado extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias_listado);

        TryGetDatos();

        ImageButton botonFiltroDenuncias = (ImageButton) findViewById(R.id.botonFiltroDenuncias);
        ImageButton botonDenunciasListadoNueva = (ImageButton) findViewById(R.id.botonDenunciasListadoNueva);

        botonFiltroDenuncias.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Denuncias_Listado.this, Denuncias_Filtro.class)); }
        });
        botonDenunciasListadoNueva.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Denuncias_Listado.this, Denuncias_Nueva.class)); }
        });
    }
    @Override protected void onResume() {
        super.onResume();
        TryGetDatos();
    }

    private void TryGetDatos() {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);
        Datos.TryUpdateDenuncias(
                new Accion() { @Override public void Ejecutar(Object in) { PoblarLista(); loadingScreen.setVisibility(View.GONE); } },
                new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); } }
        );
    }
    private void PoblarLista() {
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
        Denuncia[] denuncias = Datos.getDenuncias();
        FiltroDenuncias filtro = Datos.getFiltroDenuncias();

        for(int i = 0; i < denuncias.length; i++) {
            if(filtro != null)
            {
                if(filtro.desde != null && filtro.desde.getTime() > denuncias[i].getHorario().getTime()) { continue; }
                if(filtro.hasta != null && filtro.hasta.getTime() < denuncias[i].getHorario().getTime()) { continue; }
            }
            if(denuncias[i].getUsuario().getId() != Datos.getSesion().GetId()){ continue; }
            items.add(new ListViewItem(
                    denuncias[i].getId(),
                    denuncias[i].getNombre(),
                    denuncias[i].getDescripcion(),
                    null,
                    Denuncias_Detalle.class
            ));
        }

        ListView lista = (ListView) findViewById(R.id.lista);
        CustomListAdapterSinImagen adaptador = new CustomListAdapterSinImagen(
                this,
                R.layout.custom_list_view_item_sin_imagen,
                items,
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) { Datos.setDenunciaActiva(in); } }
        );
        lista.setAdapter(adaptador);
    }
}