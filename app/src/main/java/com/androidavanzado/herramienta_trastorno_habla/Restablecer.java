package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Restablecer extends AppCompatActivity {
    Button enviar;
    EditText correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        enviar= findViewById(R.id.btn_enviar);
        correo=findViewById(R.id.Correo_registrado);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
    }

    public void validar(){
        String email=correo.getText().toString().trim();

        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            correo.setError("Correo invalido");
            return;
        }

        sendEmail(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(Restablecer.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void sendEmail(String email){
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String emailAddres=email;

        auth.sendPasswordResetEmail(emailAddres)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Restablecer.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Restablecer.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(Restablecer.this, "Correo fue invalido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}