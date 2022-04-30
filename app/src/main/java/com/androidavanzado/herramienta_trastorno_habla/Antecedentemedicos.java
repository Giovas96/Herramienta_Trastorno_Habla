package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Antecedentemedicos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antecedentemedicos);
    }

    public void irLenguaje(View view){
        Intent i = new Intent(this, Lenguaje.class);
        startActivity(i);
    }

    public void irHistoriaEscolar (View view){
        Intent j = new Intent(this, HistoriaEscolar.class);
        startActivity(j);
    }

}