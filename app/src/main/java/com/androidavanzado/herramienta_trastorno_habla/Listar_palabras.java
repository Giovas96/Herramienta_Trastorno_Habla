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
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listacita;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_palabras;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Listar_palabras extends AppCompatActivity {
    RecyclerView listaviewpalabra;
    FirebaseFirestore firebaseFirestore;
    CollectionReference pacientereference;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<Palabra, ViewHolder_palabras> firestoreRecyclerAdapter;
    FirestoreRecyclerOptions<Palabra> options;
    ImageView back;
    String fonema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_palabras);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        fonema = bundle.getString("fonema");
        pacientereference = firebaseFirestore.collection(fonema);
         back = findViewById(R.id.back_pal);

         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent b = new Intent(Listar_palabras.this, Opciones_editar_palabras.class);
                 startActivity(b);
             }
         });

        listaviewpalabra= findViewById(R.id.listaviewpalabras);
        listaviewpalabra.setHasFixedSize(true);
        Listarpalabras();
    }


    private void Listarpalabras () {
        options = new FirestoreRecyclerOptions.Builder<Palabra>().setQuery(pacientereference, Palabra.class).build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Palabra, ViewHolder_palabras>(options) {

            protected void onBindViewHolder(@NonNull ViewHolder_palabras viewHolder_palabras, int position, @NonNull Palabra palabras) {
                viewHolder_palabras.SetearDatos(
                        getApplicationContext(),
                        palabras.getName(),
                        palabras.getSentence(),
                        palabras.getPosition(),
                        palabras.getImageUrl()

                );
            }


            public ViewHolder_palabras onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_palabra, parent, false);
                ViewHolder_palabras viewHolder_palabras= new ViewHolder_palabras(view);

                viewHolder_palabras.setOnClickListener(new ViewHolder_palabras.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String myId = firestoreRecyclerAdapter.getSnapshots().getSnapshot(position).getId();//recupera el ID del recycler correspondiente a firestore
                        Toast.makeText(Listar_palabras.this, "Accediste exitosamente a las citas ", Toast.LENGTH_SHORT).show();

                        //inicializar las vistas

                        Bundle bundle = new Bundle();
                        bundle.putString("fonema", fonema);
                        bundle.putString("id",myId);
                        Intent n = new Intent(Listar_palabras.this, Edit_palabras.class);
                        n.putExtras(bundle);
                        startActivity(n);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                return viewHolder_palabras;
            }
        };

        linearLayoutManager= new LinearLayoutManager(Listar_palabras.this,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        listaviewpalabra.setLayoutManager(linearLayoutManager);
        listaviewpalabra.setAdapter(firestoreRecyclerAdapter);
    }

    /*private void EliminarCita(String myId) {
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
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        if(firestoreRecyclerAdapter!=null){
            firestoreRecyclerAdapter.startListening();
        }
    }



}