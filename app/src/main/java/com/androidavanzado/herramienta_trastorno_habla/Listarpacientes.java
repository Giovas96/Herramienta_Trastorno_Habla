package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listapaciente;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Listarpacientes extends AppCompatActivity {

    RecyclerView listaviewpaciente;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String idp, idpac;
    CollectionReference pacientereference;



    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<Pacientes, ViewHolder_listapaciente> firestoreRecyclerAdapter;

    FirestoreRecyclerOptions<Pacientes> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarpacientes);
        ImageView back = findViewById(R.id.back_secc);
        ImageView add = findViewById(R.id.add_paciente);
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        idp =mAuth.getCurrentUser().getUid();
        //idpa= getIntent().getStringExtra("Pid");

        pacientereference = firebaseFirestore.collection("terapeutas").document(idp).collection("paciente");

       // idpaciente = pacientereference.get();



        listaviewpaciente= findViewById(R.id.listaviewpaciente);
        listaviewpaciente.setHasFixedSize(true);

        Listarpacientes();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Listarpacientes.this, SesionTerapeuta.class);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Listarpacientes.this, RegistroPaciente.class);
                startActivity(i);
            }
        });
    }

    private void Listarpacientes (){
        options = new FirestoreRecyclerOptions.Builder<Pacientes>().setQuery(pacientereference, Pacientes.class).build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Pacientes, ViewHolder_listapaciente>(options) {

            protected void onBindViewHolder(@NonNull ViewHolder_listapaciente viewHolder_listapaciente, int position, @NonNull Pacientes pacientes) {
                viewHolder_listapaciente.SetearDatos(
                        getApplicationContext(),
                        pacientes.getNombre(),
                        pacientes.getApellidopat(),
                        pacientes.getApellidomat(),
                        pacientes.getFechanac(),
                        pacientes.getLugar(),
                        pacientes.getDireccion(),
                        pacientes.getTelefono(),
                        pacientes.getEscuela()

                );


            }


        public ViewHolder_listapaciente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcard_element, parent, false);
                ViewHolder_listapaciente viewHolder_listapaciente= new ViewHolder_listapaciente(view);
                viewHolder_listapaciente.setOnClickListener(new ViewHolder_listapaciente.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    String myId = firestoreRecyclerAdapter.getSnapshots().getSnapshot(position).getId();//recupera el ID del recycler correspondiente a firestore
                    Toast.makeText(Listarpacientes.this, "Accediste exitosamente al paciente ", Toast.LENGTH_SHORT).show();
                      Intent intent= new Intent(Listarpacientes.this, Paciente.class);
                        intent.putExtra("idpac",myId);
                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(Listarpacientes.this, "Listo para el menu", Toast.LENGTH_SHORT).show();
                    }
                });
                return viewHolder_listapaciente;
            }
        };

        linearLayoutManager= new LinearLayoutManager(Listarpacientes.this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        listaviewpaciente.setLayoutManager(linearLayoutManager);
        listaviewpaciente.setAdapter(firestoreRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firestoreRecyclerAdapter!=null){
            firestoreRecyclerAdapter.startListening();
        }
    }




    
}