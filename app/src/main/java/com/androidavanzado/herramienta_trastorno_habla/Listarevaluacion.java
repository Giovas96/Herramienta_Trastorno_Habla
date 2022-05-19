package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Citas;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Evaluacion;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listacita;
import com.androidavanzado.herramienta_trastorno_habla.ViewHolder.ViewHolder_listaevaluacion;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Cita;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Evaluacion;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Evaluacion;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_EvolucionM;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Listarevaluacion extends AppCompatActivity {
    RecyclerView listaviewevaluacion;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String idp, idpaciente;
    CollectionReference pacientereference;
    Dialog dialog;
    LinearLayoutManager linearLayoutManager;
    Button agregar, bconsultar, beditar;
    FirestoreRecyclerAdapter<Evaluacion, ViewHolder_listaevaluacion> firestoreRecyclerAdapter;

    FirestoreRecyclerOptions<Evaluacion> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarevaluacion);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        idpaciente = getIntent().getStringExtra("idpa");
        idp = mAuth.getCurrentUser().getUid();
        pacientereference = firebaseFirestore.collection("terapeutas").document(idp).collection("paciente").document(idpaciente).collection("evaluacion");
        ImageView back = findViewById(R.id.back_list_add);
        ImageView add = findViewById(R.id.add_cit);

        listaviewevaluacion = findViewById(R.id.listaviewevaluacion);
        listaviewevaluacion.setHasFixedSize(true);
        Listarevaluacion();
        dialog = new Dialog(Listarevaluacion.this);
        dialog.setContentView(R.layout.dialogo_historial);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Listarevaluacion.this, Paciente.class);
                k.putExtra("idpac", idpaciente);
                startActivity(k);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Listarevaluacion.this, Agregar_Evaluacion.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });
    }
        private void Listarevaluacion() {
            options = new FirestoreRecyclerOptions.Builder<Evaluacion>().setQuery(pacientereference, Evaluacion.class).build();

            firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Evaluacion, ViewHolder_listaevaluacion>(options) {

                protected void onBindViewHolder(@NonNull ViewHolder_listaevaluacion viewHolder_listaevaluacion, int position, @NonNull Evaluacion evaluacion) {
                    viewHolder_listaevaluacion.SetearDatos(
                            getApplicationContext(),
                            evaluacion.getFonema(),
                            evaluacion.getInicial(),
                            evaluacion.getDirecta(),
                            evaluacion.getInversa(),
                            evaluacion.getIntervolica(),
                            evaluacion.getTrabada(),
                            evaluacion.getFina(),
                            evaluacion.getGr(),
                            evaluacion.getGl(),
                            evaluacion.getGs(),
                            evaluacion.getObservacion(),
                            evaluacion.getAdquirido()
                    );
                }

                public ViewHolder_listaevaluacion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluacion, parent, false);
                    ViewHolder_listaevaluacion viewHolder_listaevaluacion= new ViewHolder_listaevaluacion(view);

                    viewHolder_listaevaluacion.setOnClickListener(new ViewHolder_listaevaluacion.ClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String myId = firestoreRecyclerAdapter.getSnapshots().getSnapshot(position).getId();//recupera el ID del recycler correspondiente a firestore
                            Toast.makeText(Listarevaluacion.this, "Accediste exitosamente a las citas ", Toast.LENGTH_SHORT).show();

                            //inicializar las vistas
                            agregar=dialog.findViewById(R.id.AgregarH);
                            bconsultar= dialog.findViewById(R.id.ConsultarH);
                            beditar= dialog.findViewById(R.id.EditarH);

                            agregar.setVisibility(View.GONE);
                            bconsultar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent l= new Intent (Listarevaluacion.this, Consultar_Evaluacion.class);
                                    l.putExtra("idpa", idpaciente);
                                    l.putExtra("idpac",myId);
                                    startActivity(l);
                                    Toast.makeText(Listarevaluacion.this, "Click en consultar", Toast.LENGTH_SHORT).show();
                                }
                            });

                            beditar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent j= new Intent (Listarevaluacion.this, Editar_Evaluacion.class );
                                    j.putExtra("idpa", idpaciente);
                                    j.putExtra("idpac",myId);
                                    startActivity(j);
                                    Toast.makeText(Listarevaluacion.this, "Click en editar", Toast.LENGTH_SHORT).show();
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
                    return viewHolder_listaevaluacion;
                }
            };

            linearLayoutManager= new LinearLayoutManager(Listarevaluacion.this,LinearLayoutManager.VERTICAL,false);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);

            listaviewevaluacion.setLayoutManager(linearLayoutManager);
            listaviewevaluacion.setAdapter(firestoreRecyclerAdapter);
        }

        @Override
        protected void onStart() {
            super.onStart();
            if(firestoreRecyclerAdapter!=null){
                firestoreRecyclerAdapter.startListening();
            }
        }
    }