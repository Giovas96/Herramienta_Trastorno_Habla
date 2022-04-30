package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Desarrollo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo);
    }
    public void irDatosGenerales(View view){
        Intent i = new Intent(Desarrollo.this, DatosGenerales.class);
        startActivity(i);
    }

    public void irHabitos (View view){
        Intent j = new Intent(Desarrollo.this, Habitos.class);
        startActivity(j);
    }

}