package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DatosGenerales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);
    }
    public void irAntecedentemedicos(View view){
        Intent i = new Intent(this, Antecedentemedicos.class);
        startActivity(i);
    }

    public void irDesarrollo (View view){
        Intent j = new Intent(this, Desarrollo.class);
        startActivity(j);
    }

}