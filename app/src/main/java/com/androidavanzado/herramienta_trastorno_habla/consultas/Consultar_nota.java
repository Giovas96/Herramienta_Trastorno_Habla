package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidavanzado.herramienta_trastorno_habla.Listar_notas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Notas;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_nota;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_nota extends AppCompatActivity {
    TextView titulo, fecha,motivo, resumen,descripcion;
    String  idprincipal,myid;
    FirebaseAuth mAuth;
    FirebaseFirestore nota;
    ImageView back, edit;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_nota);
        Inicializarnota();
        myid=getIntent().getStringExtra("idpac");
        mAuth=FirebaseAuth.getInstance();
        nota= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        document= nota.collection("terapeutas").document(idprincipal).collection("Notas").document(myid);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_nota.this, Listar_notas.class);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_nota.this, Editar_nota.class);
                i.putExtra("idpac",myid);
                startActivity(i);
            }
        });

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Notas nota = documentSnapshot.toObject(Notas.class);
                SetearDatos(
                        nota.getTitulo(),
                        nota.getFecha(),
                        nota.getAsunto(),
                        nota.getResumen(),
                        nota.getDescripcion()
                );
            }
        });

    }

    private void Inicializarnota(){
        back= findViewById(R.id.back_consuln);
        edit= findViewById(R.id.edit_nota);
        fecha = findViewById(R.id.editTextfecha_nota_c);
        titulo=findViewById(R.id.editTextTituloN_c);
        motivo= findViewById(R.id.editTextasunto_nota_c);
        resumen= findViewById(R.id.editTextresumen_nota_c);
        descripcion =findViewById(R.id.editTextodescripcion_nota_c);
    }

    public void SetearDatos( String titulo_c, String fecha_c, String asunto_c, String resumen_c, String descripcion_c) {

        titulo.setText(titulo_c);
        fecha.setText(fecha_c);
        motivo.setText(asunto_c);
        resumen.setText(resumen_c);
        descripcion.setText(descripcion_c);

    }
}