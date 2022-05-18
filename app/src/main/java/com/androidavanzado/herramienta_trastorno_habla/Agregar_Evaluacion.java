package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Evaluacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Agregar_Evaluacion extends AppCompatActivity {
    Spinner spinner;
    EditText ini,dir, inv,inte, tra,fi, grur,grul, grus, obs, adq;
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    ImageView back, save;
    int dia, mes, anio;
    DocumentReference document;
    CollectionReference aevaluacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__evaluacion);
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        aevaluacion=document.collection("evaluacion");

        String [] fonemas={"/p/","/t/","/k/","/b/","/d/","/g/","/f/","/s/","/x/","/c^/","/m/","/n/","/ñ/","/l/","/ll/","/-r/","/r/"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fonemas);
        spinner.setAdapter(adapter);

        adq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(Agregar_Evaluacion.this, new DatePickerDialog.OnDateSetListener() {
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
                        adq.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b= new Intent(Agregar_Evaluacion.this,Listarevaluacion.class);
                startActivity(b);
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
        spinner=findViewById(R.id.spinner_evaluacion);
        ini=findViewById(R.id.editTextInicial);
        dir =findViewById(R.id.editTextDirecta);
        inv=findViewById(R.id.editTextInversa);
        inte= findViewById(R.id.editTextInter);
        tra=findViewById(R.id.editTextTrabada);
        fi =findViewById(R.id.editTextFinal);
        grur=findViewById(R.id.editTextGr);
        grul =findViewById(R.id.editTextGl);
        grus =findViewById(R.id.editTextGs);
        obs =findViewById(R.id.editTextObservacion_eval);
        adq=findViewById(R.id.editTextAdquirido);
    }

    public void registrar(){

        String fonema= spinner.getSelectedItem().toString();
        String inic = ini.getText().toString();
        String dire = dir.getText().toString();
        String inve = inv.getText().toString();
        String inter = inte.getText().toString();
        String trab = tra.getText().toString();
        String fin = fi.getText().toString();
        String grupr = grur.getText().toString();
        String grupl = grul.getText().toString();
        String grups = grus.getText().toString();
        String obser = obs.getText().toString();
        String adquir =adq.getText().toString();


        Evaluacion evaluacion= new Evaluacion(fonema,inic,dire,inve,inter,trab,fin,grupr,grupl,grups,obser,adquir);

        aevaluacion.document(fonema).set(evaluacion);
        Toast.makeText(Agregar_Evaluacion.this, "Registro de la evaluación exitosa", Toast.LENGTH_SHORT).show();
       /* Intent c=new Intent(Agregar_Evaluacion.this, ListarEvaluacion.class);
        c.putExtra("idpa", idpaciente);
        startActivity(c);*/
    }
}