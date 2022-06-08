package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Listar_notas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Notas;
import com.androidavanzado.herramienta_trastorno_habla.Paciente;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_nota;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Editar_nota extends AppCompatActivity {
    EditText titulo, asunto, resumen, descripcion;
    TextView fecha;
    String idprincipal,myid;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    ImageView back, save;
    DocumentReference document;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        myid=getIntent().getStringExtra("idpac");
        Inicializarnota();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal).collection("Notas").document(myid);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Editar_nota.this, Listar_notas.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha_actual= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(System.currentTimeMillis());
                fecha.setText(fecha_actual);
                String titulo_n = titulo.getText().toString();
                String fecha_n = fecha.getText().toString();
                String asunto_n = asunto.getText().toString();
                String resumen_n = resumen.getText().toString();
                String description_n = descripcion.getText().toString();

                if (!titulo_n.equals("") && !fecha_n.equals("") && !asunto_n.equals("") && !resumen_n.equals("") && !description_n.equals("")) {

                    Notas nota = new Notas(
                            titulo_n,
                            fecha_n,
                            asunto_n,
                            resumen_n,
                            description_n
                    );
                    document.set(nota);
                    Toast.makeText(Editar_nota.this, "Nota editada con exito", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Editar_nota.this, Consultar_nota.class);
                    i.putExtra("idpac",myid);
                    startActivity(i);

                } else {
                    Toast.makeText(Editar_nota.this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    private void Inicializarnota(){
        back= findViewById(R.id.back_editnot);
        save= findViewById(R.id.save_editnota);
        fecha = findViewById(R.id.editTextfecha_nota_e);
        titulo=findViewById(R.id.editTextTituloN_e);
        asunto= findViewById(R.id.editTextasunto_nota_e);
        resumen= findViewById(R.id.editTextresumen_nota_e);
        descripcion =findViewById(R.id.editTextodescripcion_nota_e);
    }
    public void SetearDatos( String titulo_c, String fecha_c, String asunto_c, String resumen_c, String descripcion_c) {

        titulo.setText(titulo_c);
        fecha.setText(fecha_c);
        asunto.setText(asunto_c);
        resumen.setText(resumen_c);
        descripcion.setText(descripcion_c);

    }
}