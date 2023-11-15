package com.example.mibarrio.actividades.denuncias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.mibarrio.R;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.filtros.FiltroDenuncias;

import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Denuncias_Filtro extends AppCompatActivity {

    private Boolean selesccionarDesde;
    private String strDesde;
    private String strHasta;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias_filtro);

        Button botonDesde = (Button) findViewById(R.id.desde);
        Button botonHasta = (Button) findViewById(R.id.hasta);
        ConstraintLayout seleccionador = (ConstraintLayout) findViewById(R.id.selecionador);
        CalendarView calendario = (CalendarView) findViewById(R.id.calendario);
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

        FiltroDenuncias filtro = Datos.getFiltroDenuncias();

        if(filtro.desde != null){ botonDesde.setText(formater.format(filtro.desde)); }
        if(filtro.hasta != null){ botonHasta.setText(formater.format(filtro.hasta)); }

        botonDesde.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                selesccionarDesde = true;
                if(filtro.desde != null) { calendario.setDate(filtro.desde.getTime()); }
                seleccionador.setVisibility(View.VISIBLE);
            }
        });
        botonHasta.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                selesccionarDesde = false;
                if(filtro.hasta != null) { calendario.setDate(filtro.hasta.getTime()); }
                seleccionador.setVisibility(View.VISIBLE);
            }
        });
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month++;
                try {
                    if(selesccionarDesde) {
                        strDesde = day + "/" + month + "/" + year;
                        filtro.desde = formater.parse(strDesde);
                        if(filtro.hasta != null && filtro.desde.getTime() > filtro.hasta.getTime())
                        {
                            filtro.desde = filtro.hasta;
                            strDesde = strHasta;
                        }
                        botonDesde.setText(strDesde);
                    }
                    else {
                        strHasta = day + "/" + month + "/" + year;
                        filtro.hasta = formater.parse(strHasta);
                        if(filtro.desde != null && filtro.desde.getTime() > filtro.hasta.getTime())
                        {
                            filtro.hasta = filtro.desde;
                            strHasta = strDesde;
                        }
                        botonHasta.setText(strHasta);
                    }
                    seleccionador.setVisibility(View.GONE);
                }
                catch (ParseException e) { e.printStackTrace(); }
            }
        });

        Button botonAplicarFiltroDenuncias = (Button) findViewById(R.id.botonAplicarFiltroDenuncias);
        botonAplicarFiltroDenuncias.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Datos.setFiltroDenuncias(filtro);
                finish();
            }
        });
    }
}