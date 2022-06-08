package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Pacientes;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listacita;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listapaciente;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_DatosG;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_DatosG;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Listarcitas extends AppCompatActivity {
    RecyclerView listaviewcita;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String idp, idpaciente;
    CollectionReference pacientereference;
    DocumentReference citaeleminada;
    Dialog dialog;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<Citas, ViewHolder_listacita> firestoreRecyclerAdapter;
    Button beliminar,bconsultar, beditar;
    FirestoreRecyclerOptions<Citas> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarcitas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        idpaciente= getIntent().getStringExtra("idpa");
        idp =mAuth.getCurrentUser().getUid();
        pacientereference = firebaseFirestore.collection("terapeutas").document(idp).collection("paciente").document(idpaciente).collection("citas");
        ImageView back = findViewById(R.id.back_list_add);
        ImageView add = findViewById(R.id.add_cit);

        listaviewcita= findViewById(R.id.listaviewcitas);
        listaviewcita.setHasFixedSize(true);
        Listarcitas();
        dialog = new Dialog(Listarcitas.this);
        dialog.setContentView(R.layout.dialogo_datos_paciente);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Listarcitas.this, Paciente.class);
                i.putExtra("idpac",idpaciente);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Listarcitas.this, Agregar_Cita.class);
                i.putExtra("idpa",idpaciente);
                startActivity(i);
            }
        });
    }


    private void Listarcitas () {
        options = new FirestoreRecyclerOptions.Builder<Citas>().setQuery(pacientereference, Citas.class).build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Citas, ViewHolder_listacita>(options) {

            protected void onBindViewHolder(@NonNull ViewHolder_listacita viewHolder_listacita, int position, @NonNull Citas cita) {
                viewHolder_listacita.SetearDatos(
                        getApplicationContext(),
                        cita.getFechacita(),
                        cita.getHoracita(),
                        cita.getDuracion(),
                        cita.getMotivo(),
                        cita.getObservacion()
                );
            }


            public ViewHolder_listacita onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                ViewHolder_listacita viewHolder_listacita= new ViewHolder_listacita(view);

                viewHolder_listacita.setOnClickListener(new ViewHolder_listacita.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String myId = firestoreRecyclerAdapter.getSnapshots().getSnapshot(position).getId();//recupera el ID del recycler correspondiente a firestore
                        Toast.makeText(Listarcitas.this, "Accediste exitosamente a las citas ", Toast.LENGTH_SHORT).show();

                        //inicializar las vistas

                        bconsultar= dialog.findViewById(R.id.Consultar);
                        beditar= dialog.findViewById(R.id.Editar);
                        beliminar= dialog.findViewById(R.id.Eliminar);


                        bconsultar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent l= new Intent (Listarcitas.this, Consultar_Cita.class );
                                l.putExtra("idpa", idpaciente);
                                l.putExtra("idpac",myId);
                                startActivity(l);
                                Toast.makeText(Listarcitas.this, "Click en consultar", Toast.LENGTH_SHORT).show();
                            }
                        });

                        beditar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j= new Intent (Listarcitas.this, Editar_Citas.class );
                                j.putExtra("idpa", idpaciente);
                                j.putExtra("idpac",myId);
                                startActivity(j);
                                Toast.makeText(Listarcitas.this, "Click en editar", Toast.LENGTH_SHORT).show();
                            }
                        });

                        beliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            EliminarCita(myId);
                            }
                        });
                        dialog.show();

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //String myId = firestoreRecyclerAdapter.getSnapshots().getSnapshot(position).getId();//recupera el ID del recycler correspondiente a firestore
                        //Toast.makeText(Listarcitas.this, "Listo para el menu", Toast.LENGTH_SHORT).show();

                    }
                });
                return viewHolder_listacita;
            }
        };

        linearLayoutManager= new LinearLayoutManager(Listarcitas.this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        listaviewcita.setLayoutManager(linearLayoutManager);
        listaviewcita.setAdapter(firestoreRecyclerAdapter);
    }

    private void EliminarCita(String myId) {
        String idcita= myId;
        AlertDialog.Builder builder = new AlertDialog.Builder(Listarcitas.this);
        builder.setTitle("Eliminar Cita");
        builder.setMessage("¿Desea eliminar la cita?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Eliminar cita de la BD
                citaeleminada=pacientereference.document(idcita);
                citaeleminada.delete();
                Toast.makeText(Listarcitas.this, "Cita eliminada", Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Listarcitas.this, "Cita no eliminada", Toast.LENGTH_SHORT).show();
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