package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.Paciente;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.androidavanzado.herramienta_trastorno_habla.consultas.consultar_datos_paciente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Editar_Citas extends AppCompatActivity {
    Spinner spinner;
    EditText fecha,hora, duracion,observacion;
    String idpaciente, idprincipal,myid;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    ImageView back, save;
    int dia, mes, anio,horas,minutos;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__citas);
        idpaciente= getIntent().getStringExtra("idpa");
        myid=getIntent().getStringExtra("idpac");
        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("citas").document(myid);


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

                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_Citas.this, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog timePickerDialog= new TimePickerDialog(Editar_Citas.this, new TimePickerDialog.OnTimeSetListener() {
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
                        hora.setText(time);
                    }
                }
                ,horas,minutos,false);
                timePickerDialog.show();
            }
        });

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Citas citas = documentSnapshot.toObject(Citas.class);
                SetearDatos(
                        citas.getFechacita(),
                        citas.getHoracita(),
                        citas.getDuracion(),
                        citas.getMotivo(),
                        citas.getObservacion()
                );
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Editar_Citas.this, Paciente.class);
                i.putExtra("idpac", idpaciente);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomb = fecha.getText().toString();
                String hr = hora.getText().toString();
                String dur = duracion.getText().toString();
                String spin = spinner.getSelectedItem().toString();
                String obs = observacion.getText().toString();


                Citas cit= new Citas(nomb, hr,dur,spin,obs);
                document.set(cit);

                Intent i = new Intent(Editar_Citas.this, Consultar_Cita.class);
                i.putExtra("idp",idpaciente);
                startActivity(i);

            }
        });
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        fecha = findViewById(R.id.editTextfecha_cita);
        hora=findViewById(R.id.editTexthora_cita);
        duracion= findViewById(R.id.editTextduracion_cita);
        spinner= findViewById(R.id.spinner_cita);
        observacion =findViewById(R.id.editTextobservacion_cita);

    }

    public void SetearDatos(String fechacita, String horacita, String duracions, String motivo, String observacions){
        String a="Avance",e="Entrevista",ev="Evaluación",t="Terapia";

        fecha.setText(fechacita);
        hora.setText(horacita);
        duracion.setText(duracions);
        if(motivo.equals(a)){
            int avance =0;
            spinner.setSelection(avance);
        }else if (motivo.equals(e)){
            int entrevista=1;
            spinner.setSelection(entrevista);
        }else if (motivo.equals(ev)){
            int evaluacion=2;
            spinner.setSelection(evaluacion);
        }else if (motivo.equals(t)){
            int terapia=3;
            spinner.setSelection(terapia);
        }

        observacion.setText(observacions);
    }
}