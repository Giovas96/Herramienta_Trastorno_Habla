package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidavanzado.herramienta_trastorno_habla.Listarcitas;
import com.androidavanzado.herramienta_trastorno_habla.Listarevaluacion;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Evaluacion;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Evaluacion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_Evaluacion extends AppCompatActivity {

    TextView spinner,ini,dir, inv,inte, tra,fi, grur,grul, grus, obs, adq;
    String idpaciente, idprincipal,myid;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    ImageView back, edit;
    DocumentReference document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__evaluacion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        idpaciente= getIntent().getStringExtra("idpa");
        myid=getIntent().getStringExtra("idpac");
        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("evaluacion").document(myid);

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
                Intent i = new Intent(Consultar_Evaluacion.this, Listarevaluacion.class);
                i.putExtra("idpa",idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_Evaluacion.this, Editar_Evaluacion.class);
                i.putExtra("idpa",idpaciente);
                i.putExtra("idpac",myid);
                startActivity(i);
            }
        });

    }
    private void inicializarDatos(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        spinner=findViewById(R.id.spinner_evaluacion_c);
        ini=findViewById(R.id.editTextInicial_c);
        dir =findViewById(R.id.editTextDirecta_c);
        inv=findViewById(R.id.editTextInversa_c);
        inte= findViewById(R.id.editTextInter_c);
        tra=findViewById(R.id.editTextTrabada_c);
        fi =findViewById(R.id.editTextFinal_c);
        grur=findViewById(R.id.editTextGr_c);
        grul =findViewById(R.id.editTextGl_c);
        grus =findViewById(R.id.editTextGs_c);
        obs =findViewById(R.id.editTextObservacion_eval_c);
        adq=findViewById(R.id.editTextAdquirido_c);
    }

    private void SetearDatos(String fonema, String inicial, String directa, String inversa,
                             String intervolica, String trabada, String fina, String gr, String gl, String gs,
                             String observacion, String adquirido){
        spinner.setText(fonema);
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