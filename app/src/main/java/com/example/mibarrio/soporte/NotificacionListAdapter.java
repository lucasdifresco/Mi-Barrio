package com.example.mibarrio.soporte;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mibarrio.R;
import com.example.mibarrio.actividades.denuncias.Denuncias_Detalle;
import com.example.mibarrio.actividades.publicaciones.Publicaciones_Detalle;
import com.example.mibarrio.actividades.reclamos.Reclamo_Detalle;
import com.example.mibarrio.enumerables.TIPOS_FORMULARIOS;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Notificacion;

import java.util.ArrayList;

public class NotificacionListAdapter extends ArrayAdapter<Notificacion> {

    Context context;
    int resource;
    ArrayList<Notificacion> items;

    public NotificacionListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Notificacion> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.items = objects;
    }

    @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        TextView titulo = (TextView) view.findViewById(R.id.titulo);
        titulo.setText(items.get(position).getMensaje());

        view.findViewById(R.id.boton).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(context, getClase(items.get(position).getTipo()));
                setActivo(items.get(position).getTipo(), items.get(position).getIdDestino());
                context.startActivity(intent);
            }
        });

        return view;
    }

    private Class getClase(int tipo) {
        switch (TIPOS_FORMULARIOS.getValueById(tipo)) {
            case PUBLICACION: return Publicaciones_Detalle.class;
            case RECLAMO: return Reclamo_Detalle.class;
            case DENUNCIA: return Denuncias_Detalle.class;
        }
        return Publicaciones_Detalle.class;
    }
    private void setActivo(int tipo, int id) {
        switch (TIPOS_FORMULARIOS.getValueById(tipo)) {
            case PUBLICACION: Datos.setPublicacionActiva(Datos.getPublicacion(id)); break;
            case RECLAMO: Datos.setReclamoActivo(Datos.getReclamo(id)); break;
            case DENUNCIA: Datos.setDenunciaActiva(Datos.getDenuncia(id)); break;
        }
    }
}
