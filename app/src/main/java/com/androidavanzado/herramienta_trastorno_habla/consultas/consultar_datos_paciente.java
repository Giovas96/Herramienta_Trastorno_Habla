package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidavanzado.herramienta_trastorno_habla.DatosGenerales;
import com.androidavanzado.herramienta_trastorno_habla.Listarpacientes;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.Paciente;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.RegistroPaciente;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_paciente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class consultar_datos_paciente extends AppCompatActivity {
    TextView Nombre, Apellidopat, Apellidomat, Fechanac, Lugar, Direccion, Telefono, Escuela;
    Button DatosTutor;
    ImageView back,edit;
    FirebaseAuth mAuth;
    FirebaseFirestore terapeuta;
    String idprincipal, idpaciente;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_datos_paciente);
        Inicializarpaciente();
        idpaciente= getIntent().getStringExtra("idp");
        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        document= terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(consultar_datos_paciente.this, Paciente.class);
                i.putExtra("idpac",idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(consultar_datos_paciente.this, Editar_paciente.class);
                i.putExtra("idp",idpaciente);
                startActivity(i);
            }
        });


        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Pacientes paciente = documentSnapshot.toObject(Pacientes.class);
                SetearDatos(
                paciente.getNombre(),
                paciente.getApellidopat(),
                paciente.getApellidomat(),
                paciente.getFechanac(),
                paciente.getLugar(),
                paciente.getDireccion(),
                paciente.getEscuela(),
                paciente.getEscuela()
                );
            }
        });

        DatosTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(consultar_datos_paciente.this, Consultar_tutor.class);
                i.putExtra("idp",idpaciente);
                startActivity(i);
            }
        });
    }

    private void Inicializarpaciente(){
        Nombre = findViewById(R.id.editTextNombrespaci_c);
        Apellidopat=findViewById(R.id.editTextApellidoPpaci_c);
        Apellidomat=findViewById(R.id.editTextApellidoMpaci_c);
        Fechanac=findViewById(R.id.editTextFechanacpaci_c);
        Lugar=findViewById(R.id.editLugarpaci_c);
        Direccion=findViewById(R.id.editTextDireccionpaci_c);
        Telefono=findViewById(R.id.editTextTelpaci_c);
        Escuela=findViewById(R.id.editTextEscuelapaci_c);
        DatosTutor=findViewById(R.id.btn_DatosTutor_c);
        back = findViewById(R.id.back_regisp);
        edit=findViewById(R.id.edit_pac);

    }

    private void SetearDatos(String nombre, String apellidopat, String apellidomat, String fechanac, String lugar, String direccion, String telefono, String escuela){

        Nombre.setText(nombre);
        Apellidopat.setText(apellidopat);
        Apellidomat.setText(apellidomat);
        Fechanac.setText(fechanac);
        Lugar.setText(lugar);
        Direccion.setText(direccion);
        Telefono.setText(telefono);
        Escuela.setText(escuela);

    }
}