package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.editar.Edtiar_Registro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class SesionTerapeuta extends AppCompatActivity {

    Toolbar toolbar;
    private ListView menu_terapeuta;
    String opciones []={"Listar Pacientes", "Listar Actividades", "Listar Notas", "Información" };
    int iconos [] = {R.drawable.pacientes,R.drawable.juegos, R.drawable.notas, R.drawable.info};

    FirebaseAuth mAuth;
    FirebaseFirestore terapeuta;
    TextView iduser, correovista;
    String idprincipal;

    //Menu despegable Terapeuta
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_depegable_terapeuta, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Intent e= new Intent(SesionTerapeuta.this, Edtiar_Registro.class);
                startActivity(e);
                Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_cerrar:
                mAuth.signOut();
                Toast.makeText(this, "Ha cerrado sesión exitosamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SesionTerapeuta.this, MainActivity.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_terapeuta);
        toolbar= findViewById(R.id.toolbar_bienvenido);
        setSupportActionBar(toolbar);
        iduser= findViewById(R.id.IdUser);
        correovista= findViewById(R.id.CorreoTerapeuta);

        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();

        FirebaseUser user= mAuth.getCurrentUser();
        idprincipal = mAuth.getCurrentUser().getUid();

        //Obtener datos del correo  y ID
        DocumentReference documentReference = terapeuta.collection("terapeutas").document(idprincipal);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                        String corr= document.getString("Correo electronico");
                        String contr= document.getString("id");
                        correovista.setText(corr);
                        iduser.setText(contr);

                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        //Menu Terapeuta
        menu_terapeuta= findViewById(R.id.menu_terapeuta);
        MyAdapter adapter= new MyAdapter(SesionTerapeuta.this, R.layout.list_item, opciones, iconos);
        menu_terapeuta.setAdapter(adapter);
        menu_terapeuta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //click en Listar pacientes
                    String idenviar = iduser.getText().toString();
                    Intent intent= new Intent(SesionTerapeuta.this, Listarpacientes.class);
                    intent.putExtra("idprincial", idenviar);
                    startActivity(intent);
                } else if (position ==1){
                    //click en Listar Actividades

                } else if (position ==2){
                    //Click en Listar Notas
                    Intent intent= new Intent(SesionTerapeuta.this, Listar_notas.class);
                    startActivity(intent);

                } else if (position ==3){
                    //Click en Informacion

                }
            }
        });

    }
    
}