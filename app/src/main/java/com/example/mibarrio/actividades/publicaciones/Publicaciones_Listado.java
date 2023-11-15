package com.example.mibarrio.actividades.publicaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mibarrio.enumerables.PUBLICACION_TIPO;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.CustomListAdapter;
import com.example.mibarrio.logica.filtros.FiltroPublicaciones;
import com.example.mibarrio.soporte.ListViewItem;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.R;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;

import java.util.ArrayList;

public class Publicaciones_Listado extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones_listado);

        TryGetDatos();

        ImageButton botonFiltroPublicaciones = (ImageButton) findViewById(R.id.botonFiltroPublicaciones);
        ImageButton botonNuevaPublicacion = (ImageButton) findViewById(R.id.botonNuevaPublicacion);

        if(Datos.getSesion().GetRol() == USUARIOS_ROLES.VECINO) { botonNuevaPublicacion.setVisibility(View.VISIBLE); }
        else { botonNuevaPublicacion.setVisibility(View.INVISIBLE); }

        botonFiltroPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Publicaciones_Listado.this, Publicaciones_Filtro.class)); }
        });
        botonNuevaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { startActivity(new Intent(Publicaciones_Listado.this, Publicaciones_Nuevo.class)); }
        });
    }
    @Override protected void onResume() {
        super.onResume();
        TryGetDatos();
    }

    private void TryGetDatos() {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);
        Datos.TryUpdatePublicaciones(
                new Accion() { @Override public void Ejecutar(Object in) { PoblarLista(); loadingScreen.setVisibility(View.GONE); } },
                new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); } }
        );
    }
    private void PoblarLista() {
        ArrayList<ListViewItem> items = new ArrayList<ListViewItem>();
        Publicacion[] publicaciones = Datos.getPublicaciones();
        FiltroPublicaciones filtro = Datos.getFiltroPublicaciones();

        for(int i = 0; i < publicaciones.length; i++)
        {
            if(filtro != null)
            {
                if(filtro.servicios || filtro.productos) {
                    if (!filtro.servicios && publicaciones[i].getCategoria() == PUBLICACION_TIPO.SERVICIO.toInt()) { continue; }
                    if (!filtro.productos && publicaciones[i].getCategoria() == PUBLICACION_TIPO.PRODUCTO.toInt()) { continue; }
                }
                if(publicaciones[i].isPrivada() && Datos.getSesion().GetRol() == USUARIOS_ROLES.INVITADO) { continue; }
                if(filtro.misPublicaciones && publicaciones[i].getUsuario().getId() != Datos.getSesion().GetId()) { continue; }
                if(!filtro.palabrasClave.isEmpty() && !publicaciones[i].getNombre().contains(filtro.palabrasClave)) { continue; }
                if(publicaciones[i].getUsuario().getId() != Datos.getSesion().GetId() && publicaciones[i].getEstado() == false) { continue; }
            }
            items.add(new ListViewItem(
                    publicaciones[i].getId(),
                    publicaciones[i].getNombre(),
                    publicaciones[i].getDetalle(),
                    publicaciones[i].GetFirstImage(),
                    Publicaciones_Detalle.class
            ));
        }
        ListView lista = (ListView) findViewById(R.id.lista);
        CustomListAdapter adaptador = new CustomListAdapter(
                this,
                R.layout.custom_list_view_item,
                items,
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) { Datos.setPublicacionActiva(in); } }
                );
        lista.setAdapter(adaptador);
    }
}