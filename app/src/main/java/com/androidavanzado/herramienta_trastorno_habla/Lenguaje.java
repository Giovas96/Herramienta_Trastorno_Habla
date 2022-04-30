package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Lenguaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenguaje);
    }
    public void irEvolucionmotriz(View view){
        Intent i = new Intent(this, Evolucionmotriz.class);
        startActivity(i);
    }

    public void irAntecentemedicos (View view){
        Intent j = new Intent(this, Antecedentemedicos.class);
        startActivity(j);
    }

}