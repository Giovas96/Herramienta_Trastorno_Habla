package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class RegistroPaciente extends AppCompatActivity {

    EditText Nombre, Apellidopat, Apellidomat, Fechanac, Lugar, Direccion, Telefono, Escuela;
    Button DatosTutor;
    ImageView back;
    int dia, mes, anio;
    FirebaseAuth mAuth;
    FirebaseFirestore terapeuta;
    String idprincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);
        Inicializarpaciente();


        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        //FirebaseUser user= mAuth.getCurrentUser();
        idprincipal = mAuth.getCurrentUser().getUid();
        //idpaciente = mAuth.getCurrentUser().getUid();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroPaciente.this, Listarpacientes.class);
                startActivity(i);
            }
        });
        DatosTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = Nombre.getText().toString();
                String apellidop = Apellidopat.getText().toString();
                String apellidom = Apellidomat.getText().toString();
                String fecha = Fechanac.getText().toString();
                String lugar = Lugar.getText().toString();
                String direccion = Direccion.getText().toString();
                String telefono = Telefono.getText().toString();
                String escuela = Escuela.getText().toString();


                if (!nombre.isEmpty() && !apellidop.isEmpty() && !apellidom.isEmpty() && !fecha.isEmpty() && !lugar.isEmpty() &&
                        !direccion.isEmpty() && !telefono.isEmpty() && !escuela.isEmpty()){


                    Pacientes paciente= new Pacientes(nombre, apellidop, apellidom, fecha, lugar, direccion, telefono, escuela);
                    //String id = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).getId();
                    DocumentReference documentReference = terapeuta.collection("terapeutas").document(idprincipal);
                    documentReference.collection("paciente").add(paciente).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegistroPaciente.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                            String idpaciente= documentReference.getId();

                            Intent i = new Intent(RegistroPaciente.this, RegistroTutor.class);
                            i.putExtra("Pid",idpaciente);
                            startActivity(i);

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure( Exception e) {

                                }
                            });




                } else{
                    Toast.makeText(RegistroPaciente.this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Fechanac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(RegistroPaciente.this, new DatePickerDialog.OnDateSetListener() {
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


    }

        private void Inicializarpaciente(){
            Nombre = findViewById(R.id.editTextNombrespaci);
            Apellidopat=findViewById(R.id.editTextApellidoPpaci);
            Apellidomat=findViewById(R.id.editTextApellidoMpaci);
            Fechanac=findViewById(R.id.editTextFechanacpaci);
            Lugar=findViewById(R.id.editLugarpaci);
            Direccion=findViewById(R.id.editTextDireccionpaci);
            Telefono=findViewById(R.id.editTextTelpaci);
            Escuela=findViewById(R.id.editTextEscuelapaci);
            DatosTutor=findViewById(R.id.btn_DatosTutor);
            back = findViewById(R.id.back_regisp);

        }

       }