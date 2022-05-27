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

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Notas;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listacita;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listanota;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_nota;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_nota;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Listar_notas extends AppCompatActivity {

    RecyclerView listaviewnota;
    ImageView back, add;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String idp;
    CollectionReference notareference;
    DocumentReference notaeleminada;
    Dialog dialog;

    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<Notas, ViewHolder_listanota> firestoreRecyclerAdapter;
    Button beliminar,bconsultar, beditar;
    FirestoreRecyclerOptions<Notas> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_notas);
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        idp =mAuth.getCurrentUser().getUid();
        notareference = firebaseFirestore.collection("terapeutas").document(idp).collection("Notas");
         back = findViewById(R.id.back_secc_not);
         add = findViewById(R.id.add_nota);

        listaviewnota= findViewById(R.id.listnotas);
        listaviewnota.setHasFixedSize(true);
        Listarnotas();
        dialog = new Dialog(Listar_notas.this);
        dialog.setContentView(R.layout.dialogo_datos_paciente);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Listar_notas.this, SesionTerapeuta.class);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Listar_notas.this, Agregar_nota.class);
                startActivity(i);
            }
        });
    }

    private void Listarnotas() {

        options = new FirestoreRecyclerOptions.Builder<Notas>().setQuery(notareference, Notas.class).build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Notas, ViewHolder_listanota>(options) {

            protected void onBindViewHolder(@NonNull ViewHolder_listanota viewHolder_listanota, int position, @NonNull Notas notas) {
                viewHolder_listanota.SetearDatos(
                        getApplicationContext(),
                        notas.getTitulo(),
                        notas.getFecha(),
                        notas.getAsunto(),
                        notas.getResumen(),
                        notas.getDescripcion()
                );
            }


            public ViewHolder_listanota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
                ViewHolder_listanota viewHolder_listanota= new ViewHolder_listanota(view);

                viewHolder_listanota.setOnClickListener(new ViewHolder_listanota.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String myId = firestoreRecyclerAdapter.getSnapshots().getSnapshot(position).getId();//recupera el ID del recycler correspondiente a firestore
                        Toast.makeText(Listar_notas.this, "Accediste exitosamente a las notas ", Toast.LENGTH_SHORT).show();

                        //inicializar las vistas

                        bconsultar= dialog.findViewById(R.id.Consultar);
                        beditar= dialog.findViewById(R.id.Editar);
                        beliminar= dialog.findViewById(R.id.Eliminar);


                        bconsultar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent l= new Intent (Listar_notas.this, Consultar_nota.class );
                                l.putExtra("idpac",myId);
                                startActivity(l);
                                Toast.makeText(Listar_notas.this, "Click en consultar", Toast.LENGTH_SHORT).show();
                            }
                        });

                        beditar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j= new Intent (Listar_notas.this, Editar_nota.class );
                                j.putExtra("idpac",myId);
                                startActivity(j);
                                Toast.makeText(Listar_notas.this, "Click en editar", Toast.LENGTH_SHORT).show();
                            }
                        });

                        beliminar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EliminarNota(myId);
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
                return viewHolder_listanota;
            }
        };

        linearLayoutManager= new LinearLayoutManager(Listar_notas.this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        listaviewnota.setLayoutManager(linearLayoutManager);
        listaviewnota.setAdapter(firestoreRecyclerAdapter);

        }
    private void EliminarNota(String myId) {
        String idcita= myId;
        AlertDialog.Builder builder = new AlertDialog.Builder(Listar_notas.this);
        builder.setTitle("Eliminar Cita");
        builder.setMessage("¿Desea eliminar la cita?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Eliminar cita de la BD
                notaeleminada=notareference.document(idcita);
                notaeleminada.delete();
                Toast.makeText(Listar_notas.this, "Cita eliminada", Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Listar_notas.this, "Cita no eliminada", Toast.LENGTH_SHORT).show();
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