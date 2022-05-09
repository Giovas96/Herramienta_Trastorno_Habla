package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText correo, contrasena;
    Button btnregister;
    Button btnlogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correo=  findViewById(R.id.Correo_inicio);
        contrasena = findViewById(R.id.Contrasena_inicio);
        btnregister = findViewById(R.id.btn_registro);
        btnlogin = findViewById(R.id.btn_inicio);

        mAuth=FirebaseAuth.getInstance();


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrarseActivity.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = correo.getText().toString();
                String pass = contrasena.getText().toString();

                if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese los datos requeridos",Toast.LENGTH_SHORT).show();
                }else{
                    login(email, pass);
                }

            }
        });



    }


    public void login (String email, String pass){

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(MainActivity.this, SesionTerapeuta.class));
                    Toast.makeText(MainActivity.this, "Inicio exitoso", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(MainActivity.this, "El correo y la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });

    }



}