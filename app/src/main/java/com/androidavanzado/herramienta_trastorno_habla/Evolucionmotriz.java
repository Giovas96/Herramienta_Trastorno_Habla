package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Evolucionmotriz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolucionmotriz);
    }
    public void irHabitos(View view){
        Intent i = new Intent(Evolucionmotriz.this, Habitos.class);
        startActivity(i);
    }

    public void irLenguaje (View view){
        Intent j = new Intent(Evolucionmotriz.this, Lenguaje.class);
        startActivity(j);
    }

}