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
    String opciones []={"Listar Pacientes", "Listar Actividades", "Calendario", "Información" };
    int iconos [] = {R.drawable.pacientes,R.drawable.juegos, R.drawable.calendario, R.drawable.info};

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
                Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_cerrar:
                Toast.makeText(this, "Cerrar", Toast.LENGTH_SHORT).show();
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
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                correovista.setText(documentSnapshot.getString("Correo electronico"));
                iduser.setText(documentSnapshot.getString("id"));
            }
        });

        //Menu Terapeuta
        menu_terapeuta= findViewById(R.id.menu_terapeuta);


        //ArrayAdapter <String> adapter= new ArrayAdapter<String>(SesionTerapeuta.this, android.R.layout.simple_list_item_1, opciones);
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
                    //Click en Calendario

                } else if (position ==3){
                    //Click en Informacion

                }
            }
        });

    }

    private void CargarDatos(){

    }

}