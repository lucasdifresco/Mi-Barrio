package com.example.mibarrio.actividades.instalaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mibarrio.R;
import com.example.mibarrio.actividades.reclamos.Reclamos_Nuevo;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Instalacion;

public class Instalaciones_Detalle extends AppCompatActivity {

    private Instalacion instalacion;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instalaciones_detalle);

        Actualizar();

        Button botonInstalacionesDetalleNuevoReclamo = (Button) findViewById(R.id.botonInstalacionesDetalleNuevoReclamo);
        botonInstalacionesDetalleNuevoReclamo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(Instalaciones_Detalle.this, Reclamos_Nuevo.class);
                intent.putExtra("instalacion", instalacion);
                startActivity(intent);
            }
        });

        botonInstalacionesDetalleNuevoReclamo.setVisibility(View.INVISIBLE);
//        if (Datos.getSesion().GetRol() == USUARIOS_ROLES.VECINO) { botonInstalacionesDetalleNuevoReclamo.setVisibility(View.VISIBLE); }
 //       else { botonInstalacionesDetalleNuevoReclamo.setVisibility(View.INVISIBLE); }

    }
    @Override protected void onResume() {
        super.onResume();
        Actualizar();
    }

    private void Actualizar() {

        instalacion = Datos.getInstalacionActiva();

        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView titular = (TextView) findViewById(R.id.titular);
        TextView direccion = (TextView) findViewById(R.id.direccion);
        TextView ubicacion = (TextView) findViewById(R.id.ubicacion);
        TextView horarios = (TextView) findViewById(R.id.horarios);

        nombre.setText(instalacion.getNombre());
        titular.setText(instalacion.getTitular());
        direccion.setText(instalacion.getDireccion());
        ubicacion.setText(instalacion.getUbicacion());
        horarios.setText(instalacion.getHorarios());

    }

}