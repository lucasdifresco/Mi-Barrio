package com.example.mibarrio.soporte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Comentario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ComentarioListAdapter extends ArrayAdapter<Comentario> {

    Context context;
    int resource;
    ArrayList<Comentario> items;

    public ComentarioListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comentario> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");

        TextView autor = (TextView) view.findViewById(R.id.autor);
        TextView fecha = (TextView) view.findViewById(R.id.fecha);
        TextView comentario = (TextView) view.findViewById(R.id.comentario);

        autor.setText(items.get(position).getAutor());
        fecha.setText(fechaFormat.format(items.get(position).getFecha()));
        comentario.setText(items.get(position).getComentario());

        return view;
    }
}
