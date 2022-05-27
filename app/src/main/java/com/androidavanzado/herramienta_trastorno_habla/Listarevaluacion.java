package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Listarevaluacion extends AppCompatActivity {
    RecyclerView listaviewevaluacion;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String idp, idpaciente;
    CollectionReference pacientereference;
    DocumentReference evaluacionp;
    Dialog dialog;
    LinearLayoutManager linearLayoutManager;
    Button agregar, bconsultar, beditar,descargar;
    FirestoreRecyclerAdapter<Evaluacion, ViewHolder_listaevaluacion> firestoreRecyclerAdapter;

    FirestoreRecyclerOptions<Evaluacion> options;
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listarevaluacion);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        idpaciente = getIntent().getStringExtra("idpa");
        idp = mAuth.getCurrentUser().getUid();
        pacientereference = firebaseFirestore.collection("terapeutas").document(idp).collection("paciente").document(idpaciente).collection("evaluacion");
        ImageView back = findViewById(R.id.back_list_eval);
        ImageView add = findViewById(R.id.add_eval);

        listaviewevaluacion = findViewById(R.id.listaviewevaluacion);
        listaviewevaluacion.setHasFixedSize(true);
        Listarevaluacion();
        dialog = new Dialog(Listarevaluacion.this);
        dialog.setContentView(R.layout.dialogo_historial);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

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
                            descargar=dialog.findViewById(R.id.DescargarH);

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

                            descargar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    evaluacionpdf(myId);
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

        public void evaluacionpdf( String idprin){
                String ide=idprin;
                evaluacionp=firebaseFirestore.collection("terapeutas").document(idp).collection("paciente").document(idpaciente).collection("evaluacion").document(ide);
                evaluacionp.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Evaluacion eval = document.toObject(Evaluacion.class);
                                descargarpdf(
                                        eval.getFonema(),
                                        eval.getInicial(),
                                        eval.getDirecta(),
                                        eval.getInversa(),
                                        eval.getIntervolica(),
                                        eval.getTrabada(),
                                        eval.getFina(),
                                        eval.getGr(),
                                        eval.getGl(),
                                        eval.getGs(),
                                        eval.getObservacion(),
                                        eval.getAdquirido(),
                                        ide);
                            }
                        }
                    }
                });
                        /*.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Evaluacion evaluacion = documentSnapshot.toObject(Evaluacion.class);

                    }
            });*/
        }

        private void descargarpdf(String fonema, String inicial, String directa, String inversa,
                                  String intervolica, String trabada, String fina, String gr, String gl, String gs,
                                  String observacion, String adquirido, String idfone){


            //Creación del pdf
            PdfDocument eval = new PdfDocument();
            Paint prupaint = new Paint();

            PdfDocument.PageInfo pagina1 = new PdfDocument.PageInfo.Builder(250, 400, 1).create();

            PdfDocument.Page pag1 = eval.startPage(pagina1);
            Canvas canva = pag1.getCanvas();

            //Titulo del pdf centrado
            prupaint.setTextAlign(Paint.Align.CENTER);
            prupaint.setTextSize(14f);
            canva.drawText("Evaluación", pagina1.getPageWidth() / 2, 30, prupaint);
            //subtitulo de la Evaluación
            prupaint.setTextAlign(Paint.Align.CENTER);
            prupaint.setTextSize(10f);
            canva.drawText("Fonema", pagina1.getPageWidth() / 2, 40, prupaint);
            //Datos de la parte de Evaluación
            prupaint.setTextSize(8f);
            prupaint.setTextAlign(Paint.Align.LEFT);
            canva.drawText("Fonema: " + fonema, 20, 50, prupaint);
            canva.drawText("Inicial: " + inicial, 20, 60, prupaint);
            canva.drawText("Directa: " + directa, 20, 70, prupaint);
            canva.drawText("Inversa: " + inversa, 20, 80, prupaint);
            canva.drawText("Intervocalica: " + intervolica, 20, 90, prupaint);
            canva.drawText("Trabada: " + trabada, 20, 100, prupaint);
            canva.drawText("Final: " + fina, 20, 110, prupaint);
            canva.drawText("Grupo r: " + gr, 20, 120, prupaint);
            canva.drawText("Grupo l: " + gl, 20, 130, prupaint);
            canva.drawText("Grupo s: " + gs, 20, 140, prupaint);
            canva.drawText("Observaciones: " + observacion, 20, 150, prupaint);
            canva.drawText("Fecha de adquirido: " + adquirido, 20, 160, prupaint);

            //fin de la pagina 1
            eval.finishPage(pag1);
            File desc = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Evaluación fonema "+idfone+".pdf");


            try {
                eval.writeTo(new FileOutputStream(desc));
                Toast.makeText(Listarevaluacion.this, "La evaluación se ha generado exitosamente en su carpeta de descargas", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();

                Toast.makeText(Listarevaluacion.this, "No se genero el pdf.", Toast.LENGTH_SHORT).show();
            }
            eval.close();


        }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}