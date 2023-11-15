package com.example.mibarrio.actividades.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mibarrio.R;

public class Inicio_Mensaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_mensaje);

        Button botonInicioMensajeVolver = (Button) findViewById(R.id.botonInicioMensajeVolver);

        botonInicioMensajeVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Inicio_Mensaje.this, Inicio_Home.class);
                //startActivity(intent);
                finish();
            }
        });
    }
}