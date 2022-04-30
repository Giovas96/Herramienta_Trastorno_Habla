package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HistoriaEscolar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_escolar);
    }

    public void irAntecedentemedicos(View view){
        Intent i = new Intent(HistoriaEscolar.this, Antecedentemedicos.class);
        startActivity(i);
    }

    public void irDatosGenerales (View view){
        Intent j = new Intent(HistoriaEscolar.this, DatosGenerales.class);
        startActivity(j);
    }

}