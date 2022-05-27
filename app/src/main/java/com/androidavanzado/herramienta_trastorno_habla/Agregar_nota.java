package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Notas;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_nota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Agregar_nota extends AppCompatActivity {

    EditText titulo, asunto, resumen, descripcion;
    TextView fecha;
    String idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    ImageView back, save;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal);
        String fecha_actual= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(System.currentTimeMillis());
        fecha.setText(fecha_actual);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Agregar_nota.this, Listar_notas.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agregarnota();
                Intent i = new Intent(Agregar_nota.this, Listar_notas.class);
                startActivity(i);

            }
        });

    }

    private void inicializarDatos(){
       back= findViewById(R.id.back_listn);
        save= findViewById(R.id.save_regisn);
        fecha = findViewById(R.id.editTextfecha_nota);
        titulo=findViewById(R.id.editTextTituloN);
        asunto= findViewById(R.id.editTextasunto_nota);
        resumen= findViewById(R.id.editTextresumen_nota);
        descripcion =findViewById(R.id.editTextodescripcion_nota);

    }

    private void Agregarnota(){


            String titulo_n=titulo.getText().toString();
            String fecha_n= fecha.getText().toString();
            String asunto_n= asunto.getText().toString();
            String resumen_n= resumen.getText().toString();
            String description_n=descripcion.getText().toString();

            if(!titulo_n.equals("") && !fecha_n.equals("") && !asunto_n.equals("") && !resumen_n.equals("") && !description_n.equals("")){

                Notas nota= new Notas(
                        titulo_n,
                        fecha_n,
                        asunto_n,
                        resumen_n,
                        description_n
                );
                document.collection("Notas").add(nota);

                Toast.makeText(Agregar_nota.this, "Registro de la nota exitoso ", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
            }


    }
}