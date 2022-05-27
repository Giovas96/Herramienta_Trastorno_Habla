package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Agregar_Cita extends AppCompatActivity {
    Spinner spinner;
    EditText fecha,hora, duracion,observacion;
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    ImageView back, save;
    int dia, mes, anio,horas,minutos,dia1, mes1, anio1,hora1,minute1;
    DocumentReference document,nombre;
    CollectionReference ecitas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__cita);
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        ecitas=document.collection("citas");
        nombre=nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);

        String [] motivos={"Avance","Entrevista","Evaluación","Terapia"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,motivos);
        spinner.setAdapter(adapter);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog= new DatePickerDialog(Agregar_Cita.this, new DatePickerDialog.OnDateSetListener() {
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
                        anio1=Anioseleccionado;
                         mes1=Messelecionado;
                         dia1=Diaselecionado;
                        fecha.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar horario=Calendar.getInstance();
                horas=horario.get(Calendar.HOUR_OF_DAY);
                minutos=horario.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(Agregar_Cita.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time;
                        if (hourOfDay < 12 && hourOfDay >= 0) {
                            if(minute<10){
                                time = hourOfDay + ":" +'0'+ minute + " AM";
                            }else{
                                time = hourOfDay + ":" + minute + " AM";
                            }

                        } else if (hourOfDay == 12) {
                            if(minute<10){
                                time = hourOfDay + ":" +'0'+ minute + " PM";
                            }else{
                                time = hourOfDay + ":" + minute + " PM";
                            }

                        } else  {
                            if(minute<10){
                                hourOfDay = hourOfDay - 12;
                                time = hourOfDay + ":" +'0'+ minute + " PM";
                            }else{
                                hourOfDay = hourOfDay - 12;
                                time = hourOfDay + ":" + minute + " PM";
                            }


                        }
                         hora1=hourOfDay;
                         minute1=minute;
                        hora.setText(time);
                    }
                }
                        ,horas,minutos,false);
                timePickerDialog.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Agregar_Cita.this, Listarcitas.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        fecha = findViewById(R.id.editTextfecha_cita_a);
        hora=findViewById(R.id.editTexthora_cita_a);
        duracion= findViewById(R.id.editTextduracion_cita_a);
        spinner= findViewById(R.id.spinner_cita_a);
        observacion =findViewById(R.id.editTextobservacion_cita_a);

    }

    public void registrar(){
        String fechas = fecha.getText().toString();
        String horasr= hora.getText().toString();
        String duraciones = duracion.getText().toString();
        String motivos= spinner.getSelectedItem().toString();
        String observaciones = observacion.getText().toString();

        Citas cita= new Citas(fechas,horasr,duraciones, motivos,observaciones);

        ecitas.add(cita);
        Toast.makeText(Agregar_Cita.this, "Registro de la cita exitoso ", Toast.LENGTH_SHORT).show();

        nombre.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Pacientes paciente=documentSnapshot.toObject(Pacientes.class);
                    String nombrepaciente=paciente.getNombre();
                    String apppaciente=paciente.getApellidopat();
                    String apmpaciente=paciente.getApellidomat();
                    Calendar inicio= Calendar.getInstance();
                    inicio.set(Calendar.YEAR,anio1);
                    inicio.set(Calendar.MONTH,mes1);
                    inicio.set(Calendar.DAY_OF_MONTH,dia1);

                    inicio.set(Calendar.HOUR_OF_DAY,hora1);
                    inicio.set(Calendar.MINUTE, minute1);
                    Intent intent= new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, nombrepaciente+ " " + apppaciente+" "+apmpaciente+"("+ motivos+")" );
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, inicio.getTimeInMillis());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, duraciones + "\n"+ observaciones);
                    startActivity(intent);
            }
        });
        Intent c=new Intent(Agregar_Cita.this, Listarcitas.class);
        c.putExtra("idpa", idpaciente);
        startActivity(c);
    }
}