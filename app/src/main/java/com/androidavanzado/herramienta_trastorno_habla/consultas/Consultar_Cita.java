package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Listarcitas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.Paciente;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_paciente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_Cita extends AppCompatActivity {
    TextView fecha,hora, duracion,spinner,observacion;
    String idpaciente, idprincipal,myid;
    FirebaseAuth mAuth;
    FirebaseFirestore terapeuta;
    ImageView back, edit;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__cita);
        Inicializarpaciente();
        idpaciente= getIntent().getStringExtra("idpa");
        myid=getIntent().getStringExtra("idpac");
        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        document= terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("citas").document(myid);

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Citas cita = documentSnapshot.toObject(Citas.class);
                SetearDatos(
                        cita.getFechacita(),
                        cita.getHoracita(),
                        cita.getDuracion(),
                        cita.getMotivo(),
                        cita.getObservacion()
                );
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_Cita.this, Listarcitas.class);
                i.putExtra("idpa",idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_Cita.this, Editar_Citas.class);
                i.putExtra("idpa",idpaciente);
                i.putExtra("idpac",myid);
                startActivity(i);
            }
        });
    }
    private void Inicializarpaciente(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        fecha = findViewById(R.id.editTextfecha_cita_c);
        hora=findViewById(R.id.editTexthora_cita_c);
        duracion= findViewById(R.id.editTextduracion_cita_c);
        spinner= findViewById(R.id.spinner_cita_c);
        observacion =findViewById(R.id.editTextobservacion_cita_c);

    }

    public void SetearDatos(String fechacita, String horacita, String duracions, String motivo, String observacions){

        fecha.setText(fechacita);
        hora.setText(horacita);
        duracion.setText(duracions);
        spinner.setText(motivo);
        observacion.setText(observacions);
    }
}