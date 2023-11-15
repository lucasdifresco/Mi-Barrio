package com.example.mibarrio.actividades.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mibarrio.actividades.denuncias.Denuncias_Listado;
import com.example.mibarrio.actividades.instalaciones.Instalaciones_Listado;
import com.example.mibarrio.R;
import com.example.mibarrio.actividades.publicaciones.Publicaciones_Detalle;
import com.example.mibarrio.actividades.reclamos.Reclamos_Listado;
import com.example.mibarrio.actividades.reclamos.Reclamos_Nuevo;
import com.example.mibarrio.enumerables.PUBLICACION_TIPO;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Notificacion;
import com.example.mibarrio.logica.Publicacion;
import com.example.mibarrio.logica.Sesion;
import com.example.mibarrio.actividades.publicaciones.Publicaciones_Listado;
import com.example.mibarrio.logica.filtros.FiltroPublicaciones;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.CustomListAdapter;
import com.example.mibarrio.soporte.Helper;
import com.example.mibarrio.soporte.ListViewItem;
import com.example.mibarrio.soporte.NotificacionListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Inicio_Home extends AppCompatActivity {

    private ImageButton botonLogIn;
    private ImageButton botonLogOut;
    private ImageButton botonDenuncias;
    private ImageButton botonReclamos;
    private ImageButton botonInstalaciones;
    private ImageButton botonPublicaciones;
    private ConstraintLayout denucniasContainer;
    private ConstraintLayout reclamosContainer;
    private TextView titulo;
    public BroadcastReceiver receiver;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_home);

        receiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent intent) {
                if (!Helper.isWifiOn()) {
                    Datos.TryUploadPublicacionesPendientes();
                    Datos.TryUploadReclamosPendientes();
                    Datos.TryUploadDenunciasPendientes();
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));

        botonLogIn = (ImageButton) findViewById(R.id.botonLogIn);
        botonLogOut = (ImageButton) findViewById(R.id.botonLogOut);
        botonDenuncias = (ImageButton) findViewById(R.id.botonDenuncias);
        botonReclamos = (ImageButton) findViewById(R.id.botonReclamos);
        botonInstalaciones = (ImageButton) findViewById(R.id.botonInstalaciones);
        botonPublicaciones = (ImageButton) findViewById(R.id.botonPublicaciones);
        denucniasContainer = (ConstraintLayout) findViewById(R.id.BotonDenunciasContainer);
        reclamosContainer = (ConstraintLayout) findViewById(R.id.botonReclamosContainer);
        titulo = (TextView) findViewById(R.id.bienvenido);

        Actualizar();

        botonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Datos.setSesion(new Sesion(0, USUARIOS_ROLES.INVITADO, ""));
                Actualizar();
            }
        });
        botonLogIn.setOnClickListener(new View.OnClickListener()            { @Override public void onClick(View view) { startActivity(new Intent(Inicio_Home.this, Inicio_Sesion.class)); } });
        botonDenuncias.setOnClickListener(new View.OnClickListener()        { @Override public void onClick(View view) { startActivity(new Intent(Inicio_Home.this, Denuncias_Listado.class)); } });
        botonReclamos.setOnClickListener(new View.OnClickListener()         { @Override public void onClick(View view) { startActivity(new Intent(Inicio_Home.this, Reclamos_Listado.class)); } });
        botonInstalaciones.setOnClickListener(new View.OnClickListener()    { @Override public void onClick(View view) { startActivity(new Intent(Inicio_Home.this, Instalaciones_Listado.class)); } });
        botonPublicaciones.setOnClickListener(new View.OnClickListener()    { @Override public void onClick(View view) { startActivity(new Intent(Inicio_Home.this, Publicaciones_Listado.class)); } });
    }
    @Override protected void onResume() {
        super.onResume();
        Actualizar();
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void Actualizar() {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);


        Sesion sesion = Datos.getSesion();
        if (sesion.GetRol() == USUARIOS_ROLES.INVITADO)
        {
            titulo.setText("Bienvenido Invitado");
            botonLogIn.setVisibility(View.VISIBLE);
            botonLogOut.setVisibility(View.GONE);
            denucniasContainer.setVisibility(View.GONE);
            reclamosContainer.setVisibility(View.GONE);
            EmptyList();
        } else {
            if(sesion.GetRol() == USUARIOS_ROLES.VECINO) { titulo.setText("Bienvenido " + sesion.GetNombre()); }
            else { titulo.setText("Bienvenido " + sesion.GetNombre()); }
            botonLogIn.setVisibility(View.GONE);
            botonLogOut.setVisibility(View.VISIBLE);
            denucniasContainer.setVisibility(View.VISIBLE);
            reclamosContainer.setVisibility(View.VISIBLE);

            Datos.TryUpdateAll(
                    new Accion() { @Override public void Ejecutar(Object in) { PoblarLista(); loadingScreen.setVisibility(View.GONE); } },
                    new Accion() { @Override public void Ejecutar(Object in) { loadingScreen.setVisibility(View.GONE); } }
            );

        }

        loadingScreen.setVisibility(View.GONE);
    }

    private void PoblarLista() {
        ArrayList<Notificacion> items = new ArrayList<Notificacion>();
        Notificacion[] notificaciones = Datos.getNotificaciones();

        for(int i = 0; i < notificaciones.length; i++) { items.add(notificaciones[i]); }
        ListView lista = (ListView) findViewById(R.id.lista);
        NotificacionListAdapter adaptador = new NotificacionListAdapter(
                this,
                R.layout.notificaciones_list_view,
                items
        );
        lista.setAdapter(adaptador);
    }
    private void EmptyList() {
        ArrayList<Notificacion> items = new ArrayList<Notificacion>();
        ListView lista = (ListView) findViewById(R.id.lista);
        NotificacionListAdapter adaptador = new NotificacionListAdapter(
                this,
                R.layout.notificaciones_list_view,
                items
        );
        lista.setAdapter(adaptador);
    }
}