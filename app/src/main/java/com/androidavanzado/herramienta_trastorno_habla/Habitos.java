package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Habitos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitos);
    }
    public void irDesarrollo(View view){
        Intent i = new Intent(Habitos.this, Desarrollo.class);
        startActivity(i);
    }

    public void irevolucionmotriz (View view){
        Intent j = new Intent(Habitos.this, Evolucionmotriz.class);
        startActivity(j);
    }

}