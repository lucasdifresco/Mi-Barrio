package com.example.mibarrio.actividades.denuncias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Comentario;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Denuncia;
import com.example.mibarrio.logica.Imagen;
import com.example.mibarrio.soporte.Accion;
import com.example.mibarrio.soporte.ComentarioListAdapter;
import com.example.mibarrio.soporte.DocumentoListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Denuncias_Detalle extends AppCompatActivity {

    private Denuncia denuncia;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias_detalle);

        Actualizar();
    }

    @Override protected void onResume() {
        super.onResume();
        Actualizar();
    }

    private void Actualizar() {
        denuncia = Datos.getDenunciaActiva();
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormat = new SimpleDateFormat("hh:mm a");

        ConstraintLayout visorImagen = (ConstraintLayout) findViewById(R.id.visorImagen);
        visorImagen.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { visorImagen.setVisibility(View.GONE); } });

        TextView titulo = (TextView) findViewById(R.id.titulo);
        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView direccion = (TextView) findViewById(R.id.direccion);
        TextView fecha = (TextView) findViewById(R.id.fecha);
        TextView hora = (TextView) findViewById(R.id.hora);
        TextView descripcion = (TextView) findViewById(R.id.descripcion);

        titulo.setText("Denuncia #" + denuncia.getId());
        nombre.setText(denuncia.getNombre());
        direccion.setText(denuncia.getDireccion());
        fecha.setText(fechaFormat.format(denuncia.getHorario()));
        hora.setText(horaFormat.format(denuncia.getHorario()));
        descripcion.setText(denuncia.getDescripcion());

        PoblarLista();

        ArrayList<Comentario> items = new ArrayList<Comentario>();
        for (int i = 0; i < denuncia.getComentarios().length; i++) { items.add(denuncia.getComentarios()[i]); }
        ListView lista = (ListView) findViewById(R.id.lista);
        ComentarioListAdapter adaptador = new ComentarioListAdapter( this, R.layout.sample_comentario_view, items );
        lista.setAdapter(adaptador);
    }

    private void PoblarLista() {
        Imagen[] arreglo = denuncia.getImagenes();
        ArrayList<Imagen> imagenes = new ArrayList<Imagen>();
        for (int i = 0; i < arreglo.length; i++){ imagenes.add(arreglo[i]); }

        ListView listaDocs = (ListView) findViewById(R.id.listaDocs);
        DocumentoListAdapter adaptador = new DocumentoListAdapter(
                this,
                R.layout.documento_list_view,
                imagenes,
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) {
                    ConstraintLayout visorImagen = (ConstraintLayout) findViewById(R.id.visorImagen);
                    TextView titulo = (TextView) findViewById(R.id.tituloImagen);
                    titulo.setText(imagenes.get(in).getNombre());
                    ImageView imagen = (ImageView) findViewById(R.id.imagen);
                    imagen.setImageBitmap(imagenes.get(in).ToBitmap());
                    visorImagen.setVisibility(View.VISIBLE);

                } },
                new Accion<Integer>() { @Override public void Ejecutar(Integer in) { } },
                false
        );
        listaDocs.setAdapter(adaptador);
    }
}