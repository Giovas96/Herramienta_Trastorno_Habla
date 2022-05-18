package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.RegistrarseActivity;
import com.androidavanzado.herramienta_trastorno_habla.SesionTerapeuta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edtiar_Registro extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button btnregister;
    EditText nombre, apellidop, apellidom, titulo, especialidad, direccion, cedula, telefono, correo, contrasena;
    FirebaseFirestore nFirestore;
    DocumentReference terapeuta;
    ImageView back;
    String idp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edtiar__registro);
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        back= findViewById(R.id.back_tera);
        nombre = findViewById(R.id.nombreterapeuta_c);
        apellidop = findViewById(R.id.Apellidopt_c);
        apellidom = findViewById(R.id.Apellidomat_c);
        titulo = findViewById(R.id.text_titulot_c);
        especialidad = findViewById(R.id.Text_especialidad_c);
        direccion = findViewById(R.id.text_direcciont_c);
        cedula = findViewById(R.id.Text_cedula_c);
        telefono = findViewById(R.id.Text_telefonot_c);
        correo = findViewById(R.id.text_correot_c);
        contrasena = findViewById(R.id.text_contrasena_c);
        btnregister = findViewById(R.id.btn_registrarse_c);
        idp=mAuth.getCurrentUser().getUid();
        terapeuta=nFirestore.collection("terapeutas").document(idp);

        terapeuta.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String nom= document.getString("Nombre()s");
                        String app= document.getString("Apellido Paterno");
                        String apm= document.getString("Apellido Materno");
                        String tit= document.getString("Titulo");
                        String esp= document.getString("Especialidad");
                        String dir= document.getString("Dirección");
                        String ced= document.getString("Cedula Profesional");
                        String tel= document.getString("Telefono");
                        String corr= document.getString("Correo electronico");
                        String contr= document.getString("Contraseña");



                        SetearDatos(nom,app,apm,tit,esp,dir,ced,tel,corr,contr);
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b=new Intent(Edtiar_Registro.this, SesionTerapeuta.class);
                startActivity(b);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    private void registerUser() {
        String nameuser = nombre.getText().toString();
        String appuser = apellidop.getText().toString();
        String apmuser = apellidom.getText().toString();
        String tituser = titulo.getText().toString();
        String espuser = especialidad.getText().toString();
        String direcuser = direccion.getText().toString();
        String ceduser = cedula.getText().toString();
        String teluser = telefono.getText().toString();
        String correuser = correo.getText().toString();
        String contruser = contrasena.getText().toString();
        if (!nameuser.isEmpty() && !appuser.isEmpty() && !apmuser.isEmpty() && !tituser.isEmpty() && !espuser.isEmpty() &&
                !direcuser.isEmpty() && !ceduser.isEmpty() && !teluser.isEmpty() && !correuser.isEmpty() && !contruser.isEmpty()) {
            if (isEmailValid(correuser)) {

                if (contruser.length() >= 8) {
                    creatUser(nameuser, appuser, apmuser, tituser, espuser, direcuser, ceduser, teluser, correuser, contruser);
                } else {
                    Toast.makeText(Edtiar_Registro.this, "Completaste los datos con un email invalido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Edtiar_Registro.this, "Completaste los datos y el email es incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Edtiar_Registro.this, "Complete los datos para continuar", Toast.LENGTH_SHORT).show();
        }


    }
    private void creatUser(final String nameuser, String appuser, String apmuser, String tituser, String espuser,
                           String direcuser, String ceduser,String teluser, final String correuser, String contruser) {
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("Nombre", nameuser);
        map.put("Apellido Paterno", appuser);
        map.put("Apellido Materno", apmuser);
        map.put("Titulo", tituser);
        map.put("Especialidad", espuser);
        map.put("Dirección", direcuser);
        map.put("Cedula Profesional", ceduser);
        map.put("Telefono", teluser);
        map.put("Correo electronico", correuser);
        map.put("Contraseña", contruser);
        terapeuta.set(map);
    }
    private void SetearDatos(String nomb,String apellidopat,String apellidomat,String titu,String especi,
                             String direcci,String cedu,String tele,String corre,String contra){

        nombre.setText(nomb);
        apellidop.setText(apellidopat);
        apellidom.setText(apellidomat);
        titulo.setText(titu);
        especialidad.setText(especi);
        direccion.setText(direcci);
        cedula.setText(cedu);
        telefono.setText(tele);
        correo.setText(corre);
        contrasena.setText(contra);
    }

    public boolean isEmailValid (String email){
        String expression ="^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();

    }
}