package com.androidavanzado.herramienta_trastorno_habla.editar;

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

import com.androidavanzado.herramienta_trastorno_habla.Agregar_Evaluacion;
import com.androidavanzado.herramienta_trastorno_habla.Listarevaluacion;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Evaluacion;
import com.androidavanzado.herramienta_trastorno_habla.Paciente;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Editar_Evaluacion extends AppCompatActivity {
    Spinner spinner;
    EditText ini,dir, inv,inte, tra,fi, grur,grul, grus, obs, adq;
    String idpaciente, idprincipal,myid;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    ImageView back, save;
    int dia, mes, anio;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__evaluacion);
        idpaciente= getIntent().getStringExtra("idpa");
        myid=getIntent().getStringExtra("idpac");
        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("evaluacion").document(myid);


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

                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_Evaluacion.this, new DatePickerDialog.OnDateSetListener() {
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

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Evaluacion evaluacion = documentSnapshot.toObject(Evaluacion.class);
                SetearDatos(
                        evaluacion.getFonema(),
                        evaluacion.getInicial(),
                        evaluacion.getDirecta(),
                        evaluacion.getInversa(),
                        evaluacion.getIntervolica(),
                        evaluacion.getTrabada(),
                        evaluacion.getFina(),
                        evaluacion.getGr(),
                        evaluacion.getGl(),
                        evaluacion.getGs(),
                        evaluacion.getObservacion(),
                        evaluacion.getAdquirido()
                );
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b= new Intent(Editar_Evaluacion.this, Listarevaluacion.class);
                b.putExtra("idpa",idpaciente);
                startActivity(b);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                document.set(evaluacion);

                Toast.makeText(Editar_Evaluacion.this, "Edicion de la evaluación exitosa", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Editar_Evaluacion.this, Listarevaluacion.class);
                i.putExtra("idpa",idpaciente);
                startActivity(i);
            }
        });
    }
    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        spinner=findViewById(R.id.spinner_evaluacion_e);
        ini=findViewById(R.id.editTextInicial_e);
        dir =findViewById(R.id.editTextDirecta_e);
        inv=findViewById(R.id.editTextInversa_e);
        inte= findViewById(R.id.editTextInter_e);
        tra=findViewById(R.id.editTextTrabada_e);
        fi =findViewById(R.id.editTextFinal_e);
        grur=findViewById(R.id.editTextGr_e);
        grul =findViewById(R.id.editTextGl_e);
        grus =findViewById(R.id.editTextGs_e);
        obs =findViewById(R.id.editTextObservacion_eval_e);
        adq=findViewById(R.id.editTextAdquirido_e);
    }

    private void SetearDatos(String fonema, String inicial, String directa, String inversa,
                             String intervolica, String trabada, String fina, String gr, String gl, String gs,
                             String observacion, String adquirido){

       String p= "/p/",t="/t/",k="/k/",b="/b/",d="/d/",g="/g/",f="/f/",s="/s/",x="/x/",c="/c^/",m="/m/",n="/n/",ñ="/ñ/",l="/l/",ll="/ll/",rr="/-r/",r="/r/";
        int fon;
        if(fonema.equals(p)){
             fon =0;
            spinner.setSelection(fon);
        }else if (fonema.equals(t)){
             fon=1;
            spinner.setSelection(fon);
        }else if (fonema.equals(k)){
             fon=2;
            spinner.setSelection(fon);
        }else if (fonema.equals(b)){
             fon=3;
            spinner.setSelection(fon);
        }else if (fonema.equals(d)){
            fon=4;
            spinner.setSelection(fon);
        }else if (fonema.equals(g)){
            fon=5;
            spinner.setSelection(fon);
        }else if (fonema.equals(f)){
            fon=6;
            spinner.setSelection(fon);
        }else if (fonema.equals(s)){
            fon=7;
            spinner.setSelection(fon);
        }else if (fonema.equals(x)){
            fon=8;
            spinner.setSelection(fon);
        }else if (fonema.equals(c)){
            fon=9;
            spinner.setSelection(fon);
        }else if (fonema.equals(m)){
            fon=10;
            spinner.setSelection(fon);
        }else if (fonema.equals(n)){
            fon=11;
            spinner.setSelection(fon);
        }else if (fonema.equals(ñ)){
            fon=12;
            spinner.setSelection(fon);
        }else if (fonema.equals(l)){
            fon=13;
            spinner.setSelection(fon);
        }else if (fonema.equals(ll)){
            fon=14;
            spinner.setSelection(fon);
        }else if (fonema.equals(rr)){
            fon=15;
            spinner.setSelection(fon);
        }else if (fonema.equals(r)){
            fon=16;
            spinner.setSelection(fon);
        }

        ini.setText(inicial);
        dir.setText(directa);
        inv.setText(inversa);
        inte.setText(intervolica);
        tra.setText(trabada);
        fi.setText(fina);
        grur.setText(gr);
        grul.setText(gl);
        grus.setText(gs);
        obs.setText(observacion);
        adq.setText(adquirido);
    }
}