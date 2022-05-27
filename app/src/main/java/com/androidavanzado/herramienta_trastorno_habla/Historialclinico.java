package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Historialclinico extends AppCompatActivity {

    private ListView menu_historial;
    String opciones []={"Datos generales", "Desarrollo", "Hábitos", "Evolución motriz", "Lenguaje", "Antecedentes médicos", "Historia escolar" };
    int iconos [] = {R.drawable.datosgeneralesh,R.drawable.desarrolloh, R.drawable.habitosh, R.drawable.motrizh, R.drawable.lenguajeh, R.drawable.antecedenteh, R.drawable.escolarh};
    CollectionReference historial;
    DocumentReference document,datos, desarrollo,habitos,evolucion,lenguaje,antecedentespdf,escolarpdf;
    FirebaseAuth mAuth;
    Button agregar,bconsultar, beditar, descargar;
    FirebaseFirestore terapeuta;
    String idprincipal, idpaciente;
    Dialog dialog;
    Intent i;


    private static final int PERMISSION_REQUEST_CODE = 200;
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
        descargar=findViewById(R.id.descargarhistorial);
        dialog = new Dialog(Historialclinico.this);
        dialog.setContentView(R.layout.dialogo_historial);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }


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

        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargarpdf();

            }


        });


    }

   public void descargarpdf() {

       datos= terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("DatosGenerales");

       datos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {

                       String fe = document.getString("Fecha");
                       String entre = document.getString("Nombre del entrevistado");
                       String rela = document.getString("Relacion con el paciente");
                       String refe = document.getString("Referido por");
                       String paci = document.getString("Nombre del paciente");
                       String luga = document.getString("Lugar de nacimiento");
                       String fecnaci = document.getString("Fecha de nacimiento");
                        DatosgHC(fe,entre,rela,refe,paci,luga,fecnaci);

                   } else {
                       Toast.makeText(Historialclinico.this,"El documento no existe, favor de registrar todas las categorias", Toast.LENGTH_SHORT).show();
                   }

               }else{
                   Toast.makeText(Historialclinico.this,"Error al entrar: "+task.getException(), Toast.LENGTH_SHORT).show();

               }
           }
       });

    }

    public void DatosgHC(String fe,String entre,String rela,String refe,String paci,String luga,String fecnaci){

        desarrollo=  terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("Desarrollo");
        desarrollo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String prev = document.getString("Embarazo previo (incluir aborto)");
                        String dese = document.getString("Embarazo deseado");
                        String espe = document.getString("Esperaban");
                        String emba = document.getString("Duración del embarazo");
                        String movi = document.getString("El bebé se movía durante el embarazo");
                        String amen = document.getString("Amenaza o intento de aborto");
                        String part = document.getString("Parto");
                        String incu = document.getString("Incubadora");
                        String llant = document.getString("Llanto");
                        String colo = document.getString("Color");
                        String transfu = document.getString("Transfuciones");
                        String icteric = document.getString("Ictericia");
                        String apga = document.getString("Apgar");
                        String pesos = document.getString("Peso");
                        String tall = document.getString("Talla");
                        String force = document.getString("Forceps");

                        DesarrolloHC(fe,entre,rela,refe,paci,luga,fecnaci,prev,dese,espe,emba,movi,amen,part,incu,llant,colo,transfu,icteric,apga,pesos,tall,force);
                    } else {
                        Toast.makeText(Historialclinico.this,"El documento no existe, favor de registrar todas las categorias", Toast.LENGTH_SHORT).show();

                        //startActivity(new Intent(Consultar_Desarrollo.this, Desarrollo.class));
                    }
                } else {
                    Toast.makeText(Historialclinico.this,"Error al entrar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void DesarrolloHC(String feDG,String entreDG,String relaDG,String refeDG,String paciDG,String lugaDG,String fecnaciDG,
                             String prevD,String deseD,String esperD,String movD,String aborD,String durD,String parD,String incaD,
                             String llanD,String colD,String transD,String icteD,String apgD,String pesD,String talD,String forcD) {

        habitos = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("Habitos");
        habitos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String lec = document.getString("Tomó leche materna ¿Cuánto tiempo?");
                        String mamil = document.getString("Tomó mamila ¿Cuánto tiempo?");
                        String chup = document.getString("Usó chupón");
                        String ded = document.getString("Se chupa el dedo");
                        String alimen = document.getString("Hubo problemas para alimentarlo");
                        String durm = document.getString("Edad en la que durmio toda la noche");
                        String suen = document.getString("¿Cómo fue su sueño en la noche?");

                        habitospdf(feDG, entreDG, relaDG, refeDG, paciDG, lugaDG,fecnaciDG,
                                 prevD, deseD, esperD, movD, aborD, durD, parD, incaD, llanD, colD, transD, icteD, apgD, pesD, talD, forcD,
                                lec, mamil, chup, ded, alimen, durm, suen);

                    } else {
                        Toast.makeText(Historialclinico.this,"El documento no existe, favor de registrar todas las categorias", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Historialclinico.this, "Error al consultar: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        public void habitospdf (String feDG, String entreDG, String relaDG, String refeDG, String paciDG, String lugaDG, String fecnaciDG,
                                String prevD, String deseD, String esperD, String movD, String aborD, String durD, String parD, String incaD,
                                String llanD, String colD, String transD, String icteD, String apgD, String pesD, String talD, String forcD,
                                String lecH,String mamH,String chuH,String deH,String aliH,String dorH,String sueH){
        evolucion = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("EvolucionMotriz");
        evolucion.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String cab = document.getString("A qué edad sostuvo la cabeza");
                        String sent = document.getString("Se sentó sin apoyo");
                        String par = document.getString("Se paró por si mismo");
                        String gat = document.getString("Gateó");
                        String eda = document.getString("a qué edad");
                        String tiem = document.getString("Durante cuánto tiempo");
                        String cam = document.getString("Caminó");
                        String obj = document.getString("Chocaba con los objetos");
                        String diu = document.getString("Diurno");
                        String noc = document.getString("Nocturno");
                        String pref = document.getString("Mano y pie de preferencia");


                       evolucionpdf( feDG,  entreDG,  relaDG,  refeDG, paciDG,  lugaDG,  fecnaciDG,
                                prevD,  deseD,  esperD,  movD,  aborD,  durD,  parD,  incaD,
                                llanD,  colD, transD,  icteD,  apgD,  pesD,  talD,  forcD,
                                lecH, mamH, chuH, deH, aliH, dorH, sueH,cab,sent,par,gat,eda,tiem,cam,obj,diu,noc,pref);

                    } else {
                        Toast.makeText(Historialclinico.this,"El documento no existe, favor de registrar todas las categorias", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Historialclinico.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
         });
    }

    public void evolucionpdf(String feDG, String entreDG, String relaDG, String refeDG, String paciDG, String lugaDG, String fecnaciDG,
                             String prevD, String deseD, String esperD, String movD, String aborD, String durD, String parD, String incaD,
                             String llanD, String colD, String transD, String icteD, String apgD, String pesD, String talD, String forcD,
                             String lecH,String mamH,String chuH,String deH,String aliH,String dorH,String sueH,
                             String cabE,String sentE,String parE,String gatE,String edaE,String tiemE,String camE,String objE,String diuE,String nocE,String prefE){

        lenguaje = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("Lenguaje");
        lenguaje.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String balb = document.getString("Balbuceó a los");
                        String prim = document.getString("Primeras palabras");
                        String jun = document.getString("Dos palabras juntas");
                        String cor = document.getString("Habló de corrido");
                        String pron = document.getString("Dificultad para pronunciar algún sonido (edad)");
                        String def = document.getString("Algún otro defecto en su lenguaje");
                        String ent = document.getString("Entiende cuando se le hable");
                        String tart = document.getString("Tartamudea");
                        String mimi = document.getString("Utiliza mímiza para comunicarse");

                      lenguajepdf(feDG,  entreDG,  relaDG,  refeDG, paciDG,  lugaDG,  fecnaciDG,
                                prevD,  deseD,  esperD,  movD,  aborD,  durD,  parD,  incaD,
                                llanD,  colD, transD,  icteD,  apgD,  pesD,  talD,  forcD,
                                lecH, mamH, chuH, deH, aliH, dorH, sueH,cabE,sentE,parE,gatE,edaE,tiemE,camE,objE,diuE,nocE,prefE,
                                balb, prim, jun, cor, pron, def, ent, tart, mimi);



                    } else {
                        Toast.makeText(Historialclinico.this,"El documento no existe, favor de registrar todas las categorias", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Historialclinico.this, Lenguaje.class));
                    }
                } else {
                    Toast.makeText(Historialclinico.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void lenguajepdf(String feDG, String entreDG, String relaDG, String refeDG, String paciDG, String lugaDG, String fecnaciDG,
                            String prevD, String deseD, String esperD, String movD, String aborD, String durD, String parD, String incaD,
                            String llanD, String colD, String transD, String icteD, String apgD, String pesD, String talD, String forcD,
                            String lecH,String mamH,String chuH,String deH,String aliH,String dorH,String sueH,
                            String cabE,String sentE,String parE,String gatE,String edaE,String tiemE,String camE,String objE,String diuE,String nocE,String prefE,
                            String balbL,String primL,String junL,String corL,String pronL,String defL,String entL,String tartL,String mimiL){

            antecedentespdf=terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("AntecedentesMedicos");
            antecedentespdf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            String sar = document.getString("Sarampión");
                            String var = document.getString("Varicela");
                            String pap = document.getString("Paperas");
                            String inf = document.getString("Influenza");
                            String as = document.getString("Asma");
                            String otr = document.getString("Otras");
                            String hos = document.getString("Ha sido hospitalizado (por qué)");
                            String ped = document.getString("Pediatra");
                            String tped = document.getString("Telefono");
                            String cped = document.getString("Fecha de la ultima cita");
                            String den = document.getString("Dentista");
                            String tden = document.getString("Telefono");
                            String cden = document.getString("Fecha de la ultima cita");
                            String psi = document.getString("Psicologo");
                            String tpsi = document.getString("Telefono");
                            String cpsi = document.getString("Fecha de la ultima cita");


                       antecedentesmpdf(feDG,  entreDG,  relaDG,  refeDG, paciDG,  lugaDG,  fecnaciDG,
                                prevD,  deseD,  esperD,  movD,  aborD,  durD,  parD,  incaD,
                                llanD,  colD, transD,  icteD,  apgD,  pesD,  talD,  forcD,
                                lecH, mamH, chuH, deH, aliH, dorH, sueH,cabE,sentE,parE,gatE,edaE,tiemE,camE,objE,diuE,nocE,prefE,
                                balbL, primL, junL, corL, pronL, defL, entL, tartL, mimiL,sar,var,pap,inf,as,otr,hos,ped,tped,cped,den,tden,cden,psi,tpsi,cpsi);

                        } else {
                            Toast.makeText(Historialclinico.this,"El documento no existe, favor de registrar todas las categorias", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Historialclinico.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
    }

    public void antecedentesmpdf(String feDG, String entreDG, String relaDG, String refeDG, String paciDG, String lugaDG, String fecnaciDG,
                                 String prevD, String deseD, String esperD, String movD, String aborD, String durD, String parD, String incaD,
                                 String llanD, String colD, String transD, String icteD, String apgD, String pesD, String talD, String forcD,
                                 String lecH,String mamH,String chuH,String deH,String aliH,String dorH,String sueH,
                                 String cabE,String sentE,String parE,String gatE,String edaE,String tiemE,String camE,String objE,String diuE,String nocE,String prefE,
                                 String balbL,String primL,String junL,String corL,String pronL,String defL,String entL,String tartL,String mimiL,
                                 String saramAM,String variAM,String papeAM,String influAM,String asmAM,String otrAM,String hospiAM,String pediAM,String telpeAM,String citpeAM,
                                 String dentAM,String teldeAM,String citdenAM,String psicAM,String telpsicoAM,String citpsicoAM){

        escolarpdf = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("HistoriaEscolar");
        escolarpdf.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String guar = document.getString("Asistió a guardería");
                        String separ = document.getString("Presentó angustia por separación");
                        String kind = document.getString("Edad en la que inició el kinder");
                        String prim = document.getString("Edad en la que inició la primaria");
                        String opin = document.getString("Opinión de la escuela acerca del niño");
                        String conduc = document.getString("Cómo es su conducta escolar");
                        String camb = document.getString("Cambios de escuela (motivos)");
                        String rendi = document.getString("Rendimiento académico");


                      GenerarPdf(feDG,  entreDG,  relaDG,  refeDG, paciDG,  lugaDG,  fecnaciDG,
                                prevD,  deseD,  esperD,  movD,  aborD,  durD,  parD,  incaD,
                                llanD,  colD, transD,  icteD,  apgD,  pesD,  talD,  forcD,
                                lecH, mamH, chuH, deH, aliH, dorH, sueH,cabE,sentE,parE,gatE,edaE,tiemE,camE,objE,diuE,nocE,prefE,
                                balbL, primL, junL, corL, pronL, defL, entL, tartL, mimiL,
                                saramAM, variAM, papeAM, influAM, asmAM, otrAM, hospiAM, pediAM, telpeAM, citpeAM,
                                dentAM, teldeAM, citdenAM, psicAM, telpsicoAM, citpsicoAM,
                                guar,separ,kind,prim,opin,conduc,camb,rendi);

                    } else {
                        Toast.makeText(Historialclinico.this,"El documento no existe, favor de registrar todas las categorias", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Historialclinico.this, HistoriaEscolar.class));
                    }
                } else {
                    Toast.makeText(Historialclinico.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void GenerarPdf(String feDG, String entreDG, String relaDG, String refeDG, String paciDG, String lugaDG, String fecnaciDG,
                          String prevD, String deseD, String esperD, String movD, String aborD, String durD, String parD, String incaD,
                          String llanD, String colD, String transD, String icteD, String apgD, String pesD, String talD, String forcD,
                          String lecH,String mamH,String chuH,String deH,String aliH,String dorH,String sueH,
                          String cabE,String sentE,String parE,String gatE,String edaE,String tiemE,String camE,String objE,String diuE,String nocE,String prefE,
                          String balbL,String primL,String junL,String corL,String pronL,String defL,String entL,String tartL,String mimiL,
                          String saramAM,String variAM,String papeAM,String influAM,String asmAM,String otrAM,String hospiAM,String pediAM,String telpeAM,String citpeAM,
                          String dentAM,String teldeAM,String citdenAM,String psicAM,String telpsicoAM,String citpsicoAM,
                          String guarHE,String separHE,String kindHE,String primHE,String opinHE,String conducHE,String cambHE,String rendiHE){

        //Creación del pdf
        PdfDocument prueba = new PdfDocument();
        Paint prupaint = new Paint();

        PdfDocument.PageInfo pagina1 = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page pag1 = prueba.startPage(pagina1);
        Canvas canva = pag1.getCanvas();


        //Titulo del pdf centrado
        prupaint.setTextAlign(Paint.Align.CENTER);
        prupaint.setTextSize(14f);
        canva.drawText("Historial Clinico", pagina1.getPageWidth() / 2, 30, prupaint);
        //subtitulo de la parte de Datos generales
        prupaint.setTextAlign(Paint.Align.CENTER);
        prupaint.setTextSize(10f);
        canva.drawText("Datos Generales", pagina1.getPageWidth() / 2, 40, prupaint);
        //Datos de la parte de Datos generales
        prupaint.setTextSize(8f);
        prupaint.setTextAlign(Paint.Align.LEFT);
        canva.drawText("Fecha: " + feDG, 20, 50, prupaint);
        canva.drawText("Nombre del entrevistado: " + entreDG, 20, 60, prupaint);
        canva.drawText("Relacion con el paciente: " + relaDG, 20, 70, prupaint);
        canva.drawText("Referido por: " + refeDG, 20, 80, prupaint);
        canva.drawText("Nombre del paciente: " + paciDG, 20, 90, prupaint);
        canva.drawText("Lugar de nacimiento: " + lugaDG, 20, 100, prupaint);
        canva.drawText("Fecha de nacimiento: " + fecnaciDG, 20, 110, prupaint);
        //Titulo de la parte de Desarrollo
        prupaint.setTextAlign(Paint.Align.CENTER);
        prupaint.setTextSize(10f);
        canva.drawText("Desarrollo", pagina1.getPageWidth() / 2, 125, prupaint);
        //Datos de la paete de Desarrollo
        prupaint.setTextSize(8f);
        prupaint.setTextAlign(Paint.Align.LEFT);
        canva.drawText("Embarazo previo (incluir aborto) : " + prevD, 20, 135, prupaint);
        canva.drawText("Embarazo deseado : " + deseD, 20, 145, prupaint);
        canva.drawText("Esperaban : " + esperD, 20, 155, prupaint);
        canva.drawText("Duración del embarazo : " + durD, 20, 165, prupaint);
        canva.drawText("El bebé se movía durante el embarazo : " + movD, 20, 175, prupaint);
        canva.drawText("Amenaza o intento de aborto : " + aborD, 20, 185, prupaint);
        canva.drawText("Parto: " + parD, 20, 195, prupaint);
        canva.drawText("Incubadora: " + incaD, 20, 205, prupaint);
        canva.drawText("Llanto: " + llanD, 20, 215, prupaint);
        canva.drawText("Color: " + colD, 20, 225, prupaint);
        canva.drawText("Transfuciones: " + transD, 20, 235, prupaint);
        canva.drawText("Ictericia: " + icteD, 20, 245, prupaint);
        canva.drawText("Apgar: " + apgD, 20, 255, prupaint);
        canva.drawText("Peso: " + pesD, 20, 265, prupaint);
        canva.drawText("Talla: " + talD, 20, 275, prupaint);
        canva.drawText("Forceps: " + forcD, 20, 285, prupaint);
        //Titulo de la parte de Habitos
        prupaint.setTextAlign(Paint.Align.CENTER);
        prupaint.setTextSize(10f);
        canva.drawText("Habitos", pagina1.getPageWidth() / 2, 295, prupaint);
        //Datos de la parte de Habitos
        prupaint.setTextSize(8f);
        prupaint.setTextAlign(Paint.Align.LEFT);
        canva.drawText("Tomó leche materna ¿Cuánto tiempo?: " + lecH, 20, 305, prupaint);
        canva.drawText("Tomó mamila ¿Cuánto tiempo?: " + mamH, 20, 315, prupaint);
        canva.drawText("Usó chupón: " + chuH, 20, 325, prupaint);
        canva.drawText("Se chupa el dedo: " + deH, 20, 335, prupaint);
        canva.drawText("Hubo problemas para alimentarlo: " + aliH, 20, 345, prupaint);
        canva.drawText("Edad en la que durmio toda la noche: " + dorH, 20, 355, prupaint);
        canva.drawText("¿Cómo fue su sueño en la noche?: " + sueH, 20, 365, prupaint);

        //fin de la pagina 1
        prueba.finishPage(pag1);

        //inicio de la pagina 2
        Paint prupaint2=new Paint();
        PdfDocument.PageInfo pagina2 = new PdfDocument.PageInfo.Builder(250, 400, 2).create();
        PdfDocument.Page pag2 = prueba.startPage(pagina2);
        Canvas canva2 = pag2.getCanvas();

        //subtitulo de la parte de Evolución motriz
        prupaint2.setTextAlign(Paint.Align.CENTER);
        prupaint2.setTextSize(10f);
        canva2.drawText("Evolución Motriz", pagina2.getPageWidth() / 2, 30, prupaint2);

        //Datos de la parte de Evolución motriz
        prupaint2.setTextSize(8f);
        prupaint2.setTextAlign(Paint.Align.LEFT);
        canva2.drawText("A qué edad sostuvo la cabeza: " + cabE, 20, 40, prupaint2);
        canva2.drawText("Se sentó sin apoyo: " + sentE, 20, 50, prupaint2);
        canva2.drawText("Se paró por si mismo: " + parE, 20, 60, prupaint2);
        canva2.drawText("Gateó: " + gatE, 20, 70, prupaint2);
        canva2.drawText("a qué edad: " + edaE, 20, 80, prupaint2);
        canva2.drawText("Durante cuánto tiempo: " + tiemE, 20, 90, prupaint2);
        canva2.drawText("Caminó: " + camE, 20, 100, prupaint2);
        canva2.drawText("Chocaba con los objetos: " + objE, 20, 110, prupaint2);
        canva2.drawText("Diurno: " + diuE, 20, 120, prupaint2);
        canva2.drawText("Nocturno: " + nocE, 20, 130, prupaint2);
        canva2.drawText("Mano y pie de preferencia: " + prefE, 20, 140, prupaint2);

        //subtitulo de la parte de Lenguaje
        prupaint2.setTextAlign(Paint.Align.CENTER);
        prupaint2.setTextSize(10f);
        canva2.drawText("Lenguaje", pagina2.getPageWidth() / 2, 155, prupaint2);

        //parte de los datos de Lenguaje
        prupaint2.setTextSize(8f);
        prupaint2.setTextAlign(Paint.Align.LEFT);
        canva2.drawText("Balbuceó a los: " + balbL, 20, 165, prupaint2);
        canva2.drawText("Primeras palabras: " + primL, 20, 175, prupaint2);
        canva2.drawText("Dos palabras juntas: " + junL, 20, 185, prupaint2);
        canva2.drawText("Habló de corrido: " + corL, 20, 195, prupaint2);
        canva2.drawText("Dificultad para pronunciar algún sonido (edad): " + pronL, 20, 205, prupaint2);
        canva2.drawText("Algún otro defecto en su lenguaje: " + defL, 20, 215, prupaint2);
        canva2.drawText("Entiende cuando se le hable: " + entL, 20, 225, prupaint2);
        canva2.drawText("Tartamudea: " + tartL, 20, 235, prupaint2);
        canva2.drawText("Utiliza mímiza para comunicarse: " + mimiL, 20, 245, prupaint2);

        //subtitulo de la parte de Antecedente medicos
        prupaint2.setTextAlign(Paint.Align.CENTER);
        prupaint2.setTextSize(10f);
        canva2.drawText("Antecedentes Médicos", pagina2.getPageWidth() / 2, 260, prupaint2);

        //Datos de la parte de Antecedentes Médicos
        prupaint2.setTextSize(8f);
        prupaint2.setTextAlign(Paint.Align.LEFT);
        canva2.drawText("Sarampión: " + saramAM, 20, 270, prupaint2);
        canva2.drawText("Varicela: " + variAM, 20, 280, prupaint2);
        canva2.drawText("Paperas: " + papeAM, 20, 290, prupaint2);
        canva2.drawText("Influenza: " + influAM, 20, 300, prupaint2);
        canva2.drawText("Asma: " + asmAM, 20, 310, prupaint2);
        canva2.drawText("Otras: " + otrAM, 20, 320, prupaint2);
        canva2.drawText("Ha sido hospitalizado (por qué): " + hospiAM, 20, 330, prupaint2);
        canva2.drawText("Pediatra: " + pediAM, 20, 340, prupaint2);
        canva2.drawText("Telefono: " + telpeAM, 20, 350, prupaint2);
        canva2.drawText("Fecha de la ultima cita: " + citpeAM, 20, 360, prupaint2);

        prueba.finishPage(pag2);

        //inicio de la pagina 3
        Paint prupaint3=new Paint();
        PdfDocument.PageInfo pagina3 = new PdfDocument.PageInfo.Builder(250, 400, 3).create();
        PdfDocument.Page pag3 = prueba.startPage(pagina3);
        Canvas canva3 = pag3.getCanvas();

        //continuacion de los datos del antecedete medico
        prupaint3.setTextSize(8f);
        prupaint3.setTextAlign(Paint.Align.LEFT);
        canva3.drawText("Dentista: " + dentAM, 20, 40, prupaint3);
        canva3.drawText("Teléfono: " + teldeAM, 20, 50, prupaint3);
        canva3.drawText("Fecha de la ultima cita: " + citdenAM, 20, 60, prupaint3);
        canva3.drawText("Psicologo: " + psicAM, 20, 70, prupaint3);
        canva3.drawText("Telefono: " + telpsicoAM, 20, 80, prupaint3);
        canva3.drawText("Fecha de la ultima cita: " + citpsicoAM, 20, 90, prupaint3);

        //subtitulo de la parte de Historia escolar
        prupaint3.setTextAlign(Paint.Align.CENTER);
        prupaint3.setTextSize(10f);
        canva3.drawText("Historía Escolar", pagina3.getPageWidth() / 2, 105, prupaint3);

        //Datos de la parte de la historia escolar
        prupaint3.setTextSize(8f);
        prupaint3.setTextAlign(Paint.Align.LEFT);

        canva3.drawText("Asistió a guardería: " + guarHE, 20, 115, prupaint3);
        canva3.drawText("Presentó angustia por separación: " + separHE, 20, 125, prupaint3);
        canva3.drawText("Edad en la que inició el kinder: " + kindHE, 20, 135, prupaint3);
        canva3.drawText("Edad en la que inició la primaria: " + primHE, 20, 145, prupaint3);
        canva3.drawText("Opinión de la escuela acerca del niño: " + opinHE, 20, 155, prupaint3);
        canva3.drawText("Cómo es su conducta escolar: " + conducHE, 20, 165, prupaint3);
        canva3.drawText("Cambios de escuela (motivos): " + cambHE, 20, 175, prupaint3);
        canva3.drawText("Rendimiento académico: " + rendiHE, 20, 185, prupaint3);

        //fin de la pagina 3
        prueba.finishPage(pag3);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Historial Médico "+paciDG+" .pdf");


        try {
            prueba.writeTo(new FileOutputStream(file));
            Toast.makeText(Historialclinico.this, "Documento del Historial Médico se ha generado exitosamente en su carpeta de descargas", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(Historialclinico.this, "No se genero el pdf.", Toast.LENGTH_SHORT).show();
        }
        prueba.close();

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