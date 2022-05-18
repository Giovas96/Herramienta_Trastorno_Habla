package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidavanzado.herramienta_trastorno_habla.Listarpacientes;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.Paciente;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.RegistroPaciente;
import com.androidavanzado.herramienta_trastorno_habla.consultas.consultar_datos_paciente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Editar_paciente extends AppCompatActivity {
    EditText Nombre, Apellidopat, Apellidomat, Fechanac, Lugar, Direccion, Telefono, Escuela;
    Button DatosTutor;
    ImageView back,save;
    FirebaseAuth mAuth;
    FirebaseFirestore terapeuta;
    String idprincipal, idpaciente;
    int dia, mes, anio;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_paciente);
        Inicializarpaciente();
        idpaciente= getIntent().getStringExtra("idp");
        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        document= terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Editar_paciente.this, Paciente.class);
                i.putExtra("idpac",idpaciente);
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

        Fechanac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_paciente.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Anioseleccionado, int Messelecionado, int Diaselecionado) {
                        String diaformateado, mesformateado;
                        //Obtener dia
                        if(Diaselecionado < 10){
                            diaformateado='0'+ String.valueOf(Diaselecionado);
                        }else{
                            diaformateado= String.valueOf(Diaselecionado);
                        }
                        //Obtener mes
                        int Mes = Messelecionado+1;
                        if(Mes<10){
                            mesformateado= '0'+ String.valueOf(Mes);
                        }else{
                            mesformateado= String.valueOf(Mes);
                        }
                        Fechanac.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomb = Nombre.getText().toString();
                String apep = Apellidopat.getText().toString();
                String apem = Apellidomat.getText().toString();
                String fec = Fechanac.getText().toString();
                String lug = Lugar.getText().toString();
                String dir = Direccion.getText().toString();
                String tel = Telefono.getText().toString();
                String esc = Escuela.getText().toString();

                Pacientes paci= new Pacientes(nomb, apep, apem, fec, lug, dir, tel, esc);
                document.set(paci);

                Intent i = new Intent(Editar_paciente.this, consultar_datos_paciente.class);
                i.putExtra("idp",idpaciente);
                startActivity(i);

            }
        });


    }

    private void Inicializarpaciente(){
        Nombre = findViewById(R.id.editTextNombrespaci_e);
        Apellidopat=findViewById(R.id.editTextApellidoPpaci_e);
        Apellidomat=findViewById(R.id.editTextApellidoMpaci_e);
        Fechanac=findViewById(R.id.editTextFechanacpaci_e);
        Lugar=findViewById(R.id.editLugarpaci_e);
        Direccion=findViewById(R.id.editTextDireccionpaci_e);
        Telefono=findViewById(R.id.editTextTelpaci_e);
        Escuela=findViewById(R.id.editTextEscuelapaci_e);
        DatosTutor=findViewById(R.id.btn_DatosTutor);
        back = findViewById(R.id.back_regisp);
        save= findViewById(R.id.save_pac);

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