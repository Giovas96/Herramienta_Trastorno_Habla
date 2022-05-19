package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listapaciente;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
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
    Dialog dialog;
    Button beliminar,bconsultar, beditar;

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
        dialog = new Dialog(Listarpacientes.this);
        dialog.setContentView(R.layout.dialogo_datos_paciente);
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
                      /*Intent intent= new Intent(Listarpacientes.this, Paciente.class);
                        intent.putExtra("idpac",myId);
                        startActivity(intent);*/

                        //inicializar las vistas

                        bconsultar= dialog.findViewById(R.id.Consultar);
                        beditar= dialog.findViewById(R.id.Editar);
                        beliminar= dialog.findViewById(R.id.Eliminar);


                        bconsultar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= new Intent(Listarpacientes.this, Paciente.class);
                                intent.putExtra("idpac",myId);
                                startActivity(intent);
                                Toast.makeText(Listarpacientes.this, "Accediste exitosamente al paciente ", Toast.LENGTH_SHORT).show();

                            }
                        });

                        beditar.setVisibility(View.GONE);

                        beliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EliminarPaciente(myId);
                            }
                        });
                        dialog.show();

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

    private void EliminarPaciente(String myId) {
        String pacienteid=myId;
        AlertDialog.Builder builder = new AlertDialog.Builder(Listarpacientes.this);
        builder.setTitle("Eliminar Paciente");
        builder.setMessage("¿Desea eliminar al paciente?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Eliminar paciente de la BD

                pacientereference.document(pacienteid).collection("datos").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    pacientereference.document(pacienteid).collection("datos").document(snapshot.getId()).delete();
                                }
                            }
                        });
                pacientereference.document(pacienteid).collection("citas").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    pacientereference.document(pacienteid).collection("citas").document(snapshot.getId()).delete();
                                }
                            }
                        });
                pacientereference.document(pacienteid).collection("evaluacion").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    pacientereference.document(pacienteid).collection("evaluacion").document(snapshot.getId()).delete();
                                }
                            }
                        });
                pacientereference.document(pacienteid).delete();
                Toast.makeText(Listarpacientes.this, "Paciente eliminado", Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Listarpacientes.this, "Cita no eliminada", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(firestoreRecyclerAdapter!=null){
            firestoreRecyclerAdapter.startListening();
        }
    }




    
}