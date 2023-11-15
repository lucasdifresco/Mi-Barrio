package com.example.mibarrio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;

import com.example.mibarrio.actividades.inicio.Inicio_Home;
import com.example.mibarrio.actividades.reclamos.Reclamos_Nuevo;
import com.example.mibarrio.logica.Datos;
import com.example.mibarrio.soporte.Helper;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();


        Intent intent = new Intent(MainActivity.this, Inicio_Home.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() { public void run() { startActivity(intent); finish(); } }, 3000);
    }
    public static Context getContext(){ return context; }

}