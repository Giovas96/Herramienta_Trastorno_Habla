package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_AntecedentesM;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_DatosG;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Desarrollo;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_EvolucionM;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Habitos;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_HistorialE;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Lenguaje;
import com.androidavanzado.herramienta_trastorno_habla.consultas.consultar_datos_paciente;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_AntecedentesM;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_DatosG;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Desarrollo;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_EvolucionM;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Habitos;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_HistorialE;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Lenguaje;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_paciente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Historialclinico extends AppCompatActivity {

    private ListView menu_historial;
    String opciones []={"Datos generales", "Desarrollo", "Hábitos", "Evolución motriz", "Lenguaje", "Antecedentes médicos", "Historia escolar" };
    int iconos [] = {R.drawable.datosgeneralesh,R.drawable.desarrolloh, R.drawable.habitosh, R.drawable.motrizh, R.drawable.lenguajeh, R.drawable.antecedenteh, R.drawable.escolarh};
    CollectionReference historial;
    DocumentReference document;
    FirebaseAuth mAuth;
    Button agregar,bconsultar, beditar;
    FirebaseFirestore terapeuta;
    String idprincipal, idpaciente;
    Dialog dialog;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historialclinico);
        idpaciente =  getIntent().getStringExtra("idpa");
        ImageView back= findViewById(R.id.back_historial);
        TextView tittle= findViewById(R.id.title_historial);
        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        historial = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos");
        menu_historial = findViewById(R.id.menu_historial);
        MyAdapter adapter= new MyAdapter(Historialclinico.this, R.layout.list_item, opciones, iconos);
        menu_historial.setAdapter(adapter);
        dialog = new Dialog(Historialclinico.this);
        dialog.setContentView(R.layout.dialogo_historial);


        //inicializar las vistas
        agregar=dialog.findViewById(R.id.AgregarH);
        bconsultar= dialog.findViewById(R.id.ConsultarH);
        beditar= dialog.findViewById(R.id.EditarH);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(Historialclinico.this, Paciente.class);
                i.putExtra("idpac",idpaciente);
                startActivity(i);
            }
        });

        menu_historial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //click en Datos generales
                    document= historial.document("DatosGenerales");
                    document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {agregar.setVisibility(View.GONE);
                                } else {
                                    agregar.setVisibility(View.VISIBLE);
                                    bconsultar.setVisibility(View.GONE);
                                    beditar.setVisibility(View.GONE);
                                    agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            i= new Intent(Historialclinico.this, Editar_DatosG.class);
                                            i.putExtra("idpa",idpaciente);
                                            startActivity(i);
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(Historialclinico.this, "Failed with: "+ task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           Intent l= new Intent (Historialclinico.this, Consultar_DatosG.class );
                            l.putExtra("idpa", idpaciente);
                            startActivity(l);
                            Toast.makeText(Historialclinico.this, "Click en consultar", Toast.LENGTH_SHORT).show();
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Historialclinico.this, Editar_DatosG.class );
                            j.putExtra("idpa", idpaciente);
                            startActivity(j);
                            Toast.makeText(Historialclinico.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();

                } else if (position ==1){
                    //click en Desarrollo
                    document= historial.document("Desarrollo");
                    document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    agregar.setVisibility(View.GONE);
                                } else {
                                    agregar.setVisibility(View.VISIBLE);
                                    bconsultar.setVisibility(View.GONE);
                                    beditar.setVisibility(View.GONE);
                                    agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent i= new Intent (Historialclinico.this, Editar_Desarrollo.class );
                                            i.putExtra("idpa", idpaciente);
                                            startActivity(i);
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(Historialclinico.this, "Failed with: "+ task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent (Historialclinico.this, Consultar_Desarrollo.class );
                            i.putExtra("idpa", idpaciente);
                            startActivity(i);
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Historialclinico.this, Editar_Desarrollo.class );
                            j.putExtra("idpa", idpaciente);
                            startActivity(j);
                            Toast.makeText(Historialclinico.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();

                } else if (position ==2){
                    //Click en Habitos
                    document= historial.document("Habitos");
                    document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    agregar.setVisibility(View.GONE);
                                } else {
                                    agregar.setVisibility(View.VISIBLE);
                                    bconsultar.setVisibility(View.GONE);
                                    beditar.setVisibility(View.GONE);
                                    agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            i=new Intent(Historialclinico.this, Editar_Habitos.class);
                                            i.putExtra("idpa",idpaciente);
                                            startActivity(i);
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(Historialclinico.this, "Failed with: "+ task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent (Historialclinico.this, Consultar_Habitos.class );
                            i.putExtra("idpa", idpaciente);
                            startActivity(i);
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Historialclinico.this, Editar_Habitos.class );
                            j.putExtra("idpa", idpaciente);
                            startActivity(j);
                            Toast.makeText(Historialclinico.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();

                } else if (position ==3){
                    //Click en Evolucion motriz
                    document= historial.document("EvolucionMotriz");
                    document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    agregar.setVisibility(View.GONE);
                                } else {
                                    agregar.setVisibility(View.VISIBLE);
                                    bconsultar.setVisibility(View.GONE);
                                    beditar.setVisibility(View.GONE);
                                    agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            i=new Intent(Historialclinico.this, Editar_EvolucionM.class);
                                            i.putExtra("idpa",idpaciente);
                                            startActivity(i);
                                        }
                                    });
                                }
                            } else {Toast.makeText(Historialclinico.this, "Failed with: "+ task.getException(), Toast.LENGTH_SHORT).show(); }
                        }
                    });

                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent (Historialclinico.this, Consultar_EvolucionM.class );
                            i.putExtra("idpa", idpaciente);
                            startActivity(i);
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Historialclinico.this, Editar_EvolucionM.class );
                            j.putExtra("idpa", idpaciente);
                            startActivity(j);
                            Toast.makeText(Historialclinico.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }else if (position ==4){
                    //Click en lenguaje
                    document= historial.document("Lenguaje");
                    document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    agregar.setVisibility(View.GONE);
                                } else {
                                    agregar.setVisibility(View.VISIBLE);
                                    bconsultar.setVisibility(View.GONE);
                                    beditar.setVisibility(View.GONE);
                                    agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            i=new Intent(Historialclinico.this, Editar_Lenguaje.class);
                                            i.putExtra("idpa",idpaciente);
                                            startActivity(i);
                                        }
                                    });
                                }
                            } else {Toast.makeText(Historialclinico.this, "Failed with: "+ task.getException(), Toast.LENGTH_SHORT).show(); }
                        }
                    });

                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent (Historialclinico.this, Consultar_Lenguaje.class );
                            i.putExtra("idpa", idpaciente);
                            startActivity(i);
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Historialclinico.this, Editar_Lenguaje.class );
                            j.putExtra("idpa", idpaciente);
                            startActivity(j);
                            Toast.makeText(Historialclinico.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();

                }else if (position ==5){
                    //Click en Antecedentes Medicos
                    document= historial.document("AntecedentesMedicos");
                    document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    agregar.setVisibility(View.GONE);
                                } else {
                                    agregar.setVisibility(View.VISIBLE);
                                    bconsultar.setVisibility(View.GONE);
                                    beditar.setVisibility(View.GONE);
                                    agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            i=new Intent(Historialclinico.this, Editar_AntecedentesM.class);
                                            i.putExtra("idpa",idpaciente);
                                            startActivity(i);
                                        }
                                    });
                                }
                            } else {Toast.makeText(Historialclinico.this, "Failed with: "+ task.getException(), Toast.LENGTH_SHORT).show(); }
                        }
                    });

                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent (Historialclinico.this, Consultar_AntecedentesM.class );
                            i.putExtra("idpa", idpaciente);
                            startActivity(i);
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Historialclinico.this, Editar_AntecedentesM.class );
                            j.putExtra("idpa", idpaciente);
                            startActivity(j);
                            Toast.makeText(Historialclinico.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();

                }else if (position ==6){
                    //Click en Historia Escolar
                    document= historial.document("HistoriaEscolar");
                    document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    agregar.setVisibility(View.GONE);
                                } else {
                                    agregar.setVisibility(View.VISIBLE);
                                    bconsultar.setVisibility(View.GONE);
                                    beditar.setVisibility(View.GONE);
                                    agregar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            i=new Intent(Historialclinico.this, Editar_HistorialE.class);
                                            i.putExtra("idpa",idpaciente);
                                            startActivity(i);
                                        }
                                    });
                                }
                            } else {Toast.makeText(Historialclinico.this, "Failed with: "+ task.getException(), Toast.LENGTH_SHORT).show(); }
                        }
                    });

                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent (Historialclinico.this, Consultar_HistorialE.class );
                            i.putExtra("idpa", idpaciente);
                            startActivity(i);
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Historialclinico.this, Editar_HistorialE.class );
                            j.putExtra("idpa", idpaciente);
                            startActivity(j);
                            Toast.makeText(Historialclinico.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                }
            }
        });

    }
}