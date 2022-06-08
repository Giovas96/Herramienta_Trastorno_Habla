package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarseActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button btnregister;
    EditText nombre, apellidop, apellidom, titulo, especialidad, direccion, cedula, telefono, correo, contrasena;
    FirebaseFirestore nFirestore;
    CheckBox terminos;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        nombre = findViewById(R.id.nombreterapeuta);
        apellidop = findViewById(R.id.Apellidopt);
        apellidom = findViewById(R.id.Apellidomat);
        titulo = findViewById(R.id.text_titulot);
        especialidad = findViewById(R.id.Text_especialidad);
        direccion = findViewById(R.id.text_direcciont);
        cedula = findViewById(R.id.Text_cedula);
        telefono = findViewById(R.id.Text_telefonot);
        correo = findViewById(R.id.text_correot);
        contrasena = findViewById(R.id.text_contrasena);
        btnregister = findViewById(R.id.btn_registrarse);
        terminos= findViewById(R.id.Terminos_condiciones);
        dialog = new Dialog(RegistrarseActivity.this);
        dialog.setContentView(R.layout.dialogo_terminos);



        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registerUser();

            }
        });

        terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
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
                    if(terminos.isChecked()==true){
                    creatUser(nameuser, appuser, apmuser, tituser, espuser, direcuser, ceduser, teluser, correuser, contruser);
                        Intent i = new Intent(RegistrarseActivity.this, MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(RegistrarseActivity.this, "Por favor acepta los terminos y condiciones", Toast.LENGTH_SHORT).show();
                    }
                    } else {
                    Toast.makeText(RegistrarseActivity.this, "La contraseña debe contener 8 o mas caracteres", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegistrarseActivity.this, "el email es incorrecto", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RegistrarseActivity.this, "Complete los datos para continuar", Toast.LENGTH_SHORT).show();
        }


    }

    private void creatUser(final String nameuser, String appuser, String apmuser, String tituser, String espuser,
                           String direcuser, String ceduser,String teluser, final String correuser, String contruser) {
        mAuth.createUserWithEmailAndPassword(correuser, contruser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
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

                    nFirestore.collection("terapeutas").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()){
                               Toast.makeText(RegistrarseActivity.this, "El usuario se almaceno correctamente", Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(RegistrarseActivity.this, "Nose pudo almacenar en la base de datos", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                    Toast.makeText(RegistrarseActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RegistrarseActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isEmailValid (String email){
        String expression ="^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();

    }
}






