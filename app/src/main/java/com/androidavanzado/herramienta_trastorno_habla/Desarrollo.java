package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Desarrollo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Desarrollo extends AppCompatActivity {
    RadioButton siem,noem,nino,nina,simo,nomo,siab,noab;
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText previo, duracion, parto, incubadora, llanto, color;
    EditText transfuciones, ictericia, apgar, peso, talla, forceps;
    ImageView back, save;
    DocumentReference document,edesarrollo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        edesarrollo=document.collection("datos").document("Desarrollo");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Desarrollo.this, Historialclinico.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        edesarrollo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String prev = document.getString("Embarazo previo (incluir aborto)");
                        String dese = document.getString("Embarazo deseado");
                        String espe = document.getString("Esperaban");
                        String emba = document.getString("Duración del embarazo");
                        String movi = document.getString("El bebé se movía durante el embarazo");
                        String amen = document.getString("Amenaza o intento de aborto");
                        String part = document.getString("Parto");
                        String incu = document.getString("Incubadora");
                        String llant = document.getString("Llanto");
                        String colo = document.getString("Color");
                        String transfu = document.getString("Transfuciones");
                        String icteric = document.getString("Ictericia");
                        String apga = document.getString("Apgar");
                        String pesos = document.getString("Peso");
                        String tall = document.getString("Talla");
                        String force = document.getString("Forceps");

                        SetearDatos(prev,dese,espe,emba,movi,amen,part,incu,llant,colo,transfu,icteric,apga,pesos,tall,force);
                    } else {
                        Toast.makeText(Desarrollo.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Desarrollo.this, Desarrollo.class));
                    }
                } else {
                    Toast.makeText(Desarrollo.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public void irDatosGenerales(View view){
        Intent i = new Intent(Desarrollo.this, DatosGenerales.class);
        startActivity(i);
    }

    public void irHabitos (View view){
        Intent j = new Intent(Desarrollo.this, Habitos.class);
        startActivity(j);
    }
    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        previo=findViewById(R.id.editTextDembarazo);
        siem = findViewById(R.id.radioButtonDembarazosi);
        noem = findViewById(R.id.radioButtonDembarazono);
        nino=findViewById(R.id.radioButtonniño);
        nina =findViewById(R.id.radioButtonniña);
        duracion=findViewById(R.id.editTexDduracion);
        simo=findViewById(R.id.radioButtonDmoviasi);
        nomo=findViewById(R.id.radioButtonDmoviano);
        siab=findViewById(R.id.radioButtonDamenazasi);
        noab=findViewById(R.id.radioButtonDamanezano);
        parto=findViewById(R.id.editTextDparto);
        incubadora=findViewById(R.id.editTextincubadora);
        llanto=findViewById(R.id.editTextDllanto);
        color=findViewById(R.id.editTextDcolor);
        transfuciones=findViewById(R.id.editTextDtransfuciones);
        ictericia=findViewById(R.id.editTextDictericia);
        apgar=findViewById(R.id.editTextDapgar);
        peso=findViewById(R.id.editTextDpeso);
        talla=findViewById(R.id.editTextDtalla);
        forceps=findViewById(R.id.editTextDforceps);
    }

    public void registrar(){
        String prev = previo.getText().toString();
        String sie= siem.getText().toString();
        String noe = noem.getText().toString();
        String nio= nino.getText().toString();
        String nia = nina.getText().toString();
        String dur= duracion.getText().toString();
        String sim = simo.getText().toString();
        String nom = nomo.getText().toString();
        String sia = siab.getText().toString();
        String noa = noab.getText().toString();
        String part = parto.getText().toString();
        String inc = incubadora.getText().toString();
        String llan = llanto.getText().toString();
        String col = color.getText().toString();
        String trans = transfuciones.getText().toString();
        String ict = ictericia.getText().toString();
        String apg = apgar.getText().toString();
        String pes = peso.getText().toString();
        String tall = talla.getText().toString();
        String forc = forceps.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("Embarazo previo (incluir aborto)", prev);

        if (siem.isChecked()==true){
            map.put("Embarazo deseado",sie);
        }else if (noem.isChecked()==true) { map.put("Embarazo deseado",noe);  }

        if (nina.isChecked()==true){
            map.put("Esperaban",nia);
        }else if(nino.isChecked()==true) { map.put("Esperaban",nio); }

        map.put("Duración del embarazo", dur);

        if (simo.isChecked()==true){
            map.put("El bebé se movía durante el embarazo",sim);
        }else if (nomo.isChecked()==true) { map.put("El bebé se movía durante el embarazo",nom);  }

        if (siab.isChecked()==true){
            map.put("Amenaza o intento de aborto",sia);
        }else if(noab.isChecked()==true) { map.put("Amenaza o intento de aborto",noa); }

        map.put("Parto", part);
        map.put("Incubadora", inc);
        map.put("Llanto", llan);
        map.put("Color", col);
        map.put("Transfuciones", trans);
        map.put("Ictericia", ict);
        map.put("Apgar", apg);
        map.put("Peso", pes);
        map.put("Talla", tall);
        map.put("Forceps", forc);

        document.collection("datos").document("Desarrollo").set(map);
        Toast.makeText(Desarrollo.this, "Registro del desarrollo exitoso ", Toast.LENGTH_SHORT).show();
    }

    private void SetearDatos(String prev,String dese,String esper,String mov,String abor,String dur,String par,String inca,
                             String llan,String col,String trans,String icte,String apg,String pes,String tal,String forc){

        boolean check=true;
        
        previo.setText(prev);
        if(dese=="Sí") {
            siem.setChecked(check);
        }else if(dese=="No"){
            noem.setChecked(check);
        }

        if(esper=="Niña"){
            nina.setChecked(check);
        }else if(esper=="Niño"){
            nino.setChecked(check);
        }

        if(mov=="Sí"){
            simo.setChecked(check);
        }else if (mov=="No"){
            nomo.setChecked(check);
        }

        if(abor=="Sí"){
            siab.setChecked(check);
        }else if(abor=="No"){
            noab.setChecked(check);
        }

        duracion.setText(dur);
        parto.setText(par);
        incubadora.setText(inca);
        llanto.setText(llan);
        color.setText(col);
        transfuciones.setText(trans);
        ictericia.setText(icte);
        apgar.setText(apg);
        peso.setText(pes);
        talla.setText(tal);
        forceps.setText(forc);

    }

}