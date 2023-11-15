package com.example.mibarrio.actividades.inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.R;
import com.example.mibarrio.enumerables.USUARIOS_ROLES;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.logica.Sesion;
import com.example.mibarrio.logica.Usuario;
import com.example.mibarrio.soporte.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inicio_Sesion extends AppCompatActivity {

    private TextView mensaje;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        Button botonIniciarSesion = (Button) findViewById(R.id.botonIniciarSesion);
        Button botonInvitado = (Button) findViewById(R.id.botonInvitado);
        Button botonCrearCuenta = (Button) findViewById(R.id.botonCrearCuenta);

        EditText documento = (EditText) findViewById(R.id.documentoInput);
        EditText pass = (EditText) findViewById(R.id.passInput);
        RadioButton vecino = (RadioButton) findViewById(R.id.radioVecino);
        mensaje = (TextView) findViewById(R.id.inicioSesionMensaje);

        mensaje.setVisibility(View.INVISIBLE);

        botonIniciarSesion.setOnClickListener(new View.OnClickListener()    { @Override public void onClick(View view) { TryIniciarSesion(documento.getText().toString(), pass.getText().toString(), vecino.isChecked()); } });
        botonInvitado.setOnClickListener(new View.OnClickListener()         { @Override public void onClick(View view) { finish(); } });
        botonCrearCuenta.setOnClickListener(new View.OnClickListener()      { @Override public void onClick(View view) { startActivity(new Intent(Inicio_Sesion.this, Inicio_Registracion.class)); } });
    }

    private void TryIniciarSesion(String documento, String pass, Boolean isVecino) {
        if(!documento.isEmpty() && !pass.isEmpty()) {
            ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
            loadingScreen.setVisibility(View.VISIBLE);

            BackendController.logIn(documento, pass, isVecino).enqueue(new Callback<Usuario>() {
                @Override public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.body() != null) {
                        Usuario usuario = response.body();
                        if (usuario.getEstado()) {
                            if (usuario.getIdrol() == 1) { Datos.setSesion(new Sesion((int) usuario.getId(), USUARIOS_ROLES.VECINO, usuario.getNombre())); }
                            else { Datos.setSesion(new Sesion((int) usuario.getId(), USUARIOS_ROLES.INSPECTOR, usuario.getNombre())); }
                            finish();
                        } else {
                            loadingScreen.setVisibility(View.GONE);
                            mensaje.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Helper.Toast("empty body: " + response.toString());
                        loadingScreen.setVisibility(View.GONE);
                        mensaje.setVisibility(View.VISIBLE);
                    }
                }
                @Override public void onFailure(Call<Usuario> call, Throwable t) {
                    Helper.Toast("fail: " + t.toString());
                    loadingScreen.setVisibility(View.GONE);
                    mensaje.setVisibility(View.VISIBLE);
                }
            });
        } else { mensaje.setVisibility(View.VISIBLE); }
    }
}