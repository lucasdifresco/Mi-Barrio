package com.example.mibarrio.actividades.inicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mibarrio.backend.BackendController;
import com.example.mibarrio.R;
import com.example.mibarrio.backend.Servicios;
import com.example.mibarrio.logica.Usuario;
import com.example.mibarrio.soporte.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inicio_Registracion extends AppCompatActivity {

    private TextView mensaje;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_registracion);

        Button botonEnviarNuevoUsuario = (Button) findViewById(R.id.botonEnviarNuevoUsuario);
        EditText nombre = (EditText) findViewById(R.id.nombre);
        EditText apellido = (EditText) findViewById(R.id.apellido);
        EditText documento = (EditText) findViewById(R.id.documento);
        EditText password = (EditText) findViewById(R.id.password);
        mensaje = (TextView) findViewById(R.id.mensaje);
        mensaje.setVisibility(View.GONE);

        botonEnviarNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { TryRegistrarUsuario(nombre.getText().toString(), apellido.getText().toString(), documento.getText().toString(), password.getText().toString()); }
        });
    }
    private void TryRegistrarUsuario(String nombre, String apellido, String documento, String password) {
        ConstraintLayout loadingScreen = (ConstraintLayout) findViewById(R.id.loadingScreen);
        loadingScreen.setVisibility(View.VISIBLE);

        if(!nombre.isEmpty() && !apellido.isEmpty() && !documento.isEmpty() && !password.isEmpty()){
            BackendController.logIn(documento,password, true).enqueue(new Callback<Usuario>() {
                @Override public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.body() != null){ mensaje.setVisibility(View.VISIBLE); }
                    else
                    {
                        BackendController.registrar(documento, nombre, apellido, password).enqueue(new Callback<Usuario>() {
                            @Override public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                if(response.body() != null){
                                    startActivity(new Intent(Inicio_Registracion.this, Inicio_Mensaje.class));
                                    finish();
                                }
                                else {
                                    loadingScreen.setVisibility(View.GONE);
                                    Helper.Toast("empty body: " + response.toString());
                                }
                            }
                            @Override public void onFailure(Call<Usuario> call, Throwable t) {
                                loadingScreen.setVisibility(View.GONE);
                                Helper.Toast("fail: " + t.toString());
                            }
                        });
                    }
                }
                @Override public void onFailure(Call<Usuario> call, Throwable t) {
                    loadingScreen.setVisibility(View.GONE);
                    Helper.Toast("fail: " + t.toString());
                }
            });


        }

    }
}