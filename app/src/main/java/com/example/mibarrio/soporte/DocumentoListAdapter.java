package com.example.mibarrio.soporte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Imagen;

import java.util.ArrayList;

public class DocumentoListAdapter extends ArrayAdapter<Imagen> {

    Context context;
    int resource;
    ArrayList<Imagen> items;
    Accion<Integer> onAbrir;
    Accion<Integer> onEliminar;
    Boolean conDelete;

    public DocumentoListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Imagen> objects, Accion<Integer> onAbrir, Accion<Integer> onEliminar, Boolean conDelete) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.items = objects;
        this.onAbrir = onAbrir;
        this.onEliminar = onEliminar;
        this.conDelete = conDelete;
    }

    @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        Button abrir = (Button) view.findViewById(R.id.abrir);
        Button eliminar = (Button) view.findViewById(R.id.eliminar);

        if(!conDelete) { eliminar.setVisibility(View.GONE); }
        abrir.setText(items.get(position).getNombre());

        abrir.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { onAbrir.Ejecutar(position); } });
        eliminar.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { onEliminar.Ejecutar(position); } });

        return view;
    }
}
