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

import java.util.ArrayList;

public class CustomListAdapterSinImagen extends ArrayAdapter<ListViewItem> {

    Context context;
    int resource;
    ArrayList<ListViewItem> items;
    Accion<Integer> onClick;

    public CustomListAdapterSinImagen(@NonNull Context context, int resource, @NonNull ArrayList<ListViewItem> objects, Accion<Integer> onClick) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.items = objects;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        TextView titulo = (TextView) view.findViewById(R.id.titulo);
        TextView descripcion = (TextView) view.findViewById(R.id.descripcion);

        titulo.setText(items.get(position).GetTitulo());
        descripcion.setText(items.get(position).GetDescripcion());

        view.findViewById(R.id.boton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.Ejecutar(position);
                Intent intent = new Intent(context, items.get(position).GetClassType());
                context.startActivity(intent);
            }
        });

        return view;
    }
}
