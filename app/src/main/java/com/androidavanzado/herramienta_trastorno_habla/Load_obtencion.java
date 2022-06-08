package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Obtencion;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Load_obtencion extends AppCompatActivity {
    Spinner spin_fonema;
    Button btnGuardar;
    ImageView imageView, back,edit;
    ProgressBar progressBar;

    Uri imageUri;

    FirebaseFirestore nFirestore;
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    CollectionReference apalabra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_obtencion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        spin_fonema = findViewById(R.id.spinner_fonema_obtencion);


        btnGuardar = findViewById(R.id.btnGuardar_load_o);

        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView_load_obtencion);

        progressBar.setVisibility(View.INVISIBLE);

        back = findViewById(R.id.back_list_actividad);
        edit=findViewById(R.id.edit_palabras);
        edit.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Load_obtencion.this, Seleccion_obtencion_fonema.class);
                startActivity(b);
            }
        });

        String [] fonemas = {"/p/","/t/","/k/","/b/","/d/","/g/","/f/","/s/","/x/","/c^/","/m/","/n/","/ñ/","/l/","/ll/","/-r/","/r/"};
        ArrayAdapter<String> adapter_fonema = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fonemas);
        spin_fonema.setAdapter(adapter_fonema);



        nFirestore= FirebaseFirestore.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageUri != null){

                    cargarAFirestore(imageUri);

                }else{
                    Toast.makeText(Load_obtencion.this, "Selecciona una imagen por favor", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }

    }

    private void cargarAFirestore(Uri uri){

        String palabracol;
        String fonema = spin_fonema.getSelectedItem().toString();

        if (fonema == "/p/"){
            palabracol = "obtencion_p";
        }else{
            if (fonema == "/t/"){
                palabracol = "obtencion_t";
            }else{
                if (fonema == "/k/"){
                    palabracol = "obtencion_k";
                }else{
                    if (fonema == "/b/"){
                        palabracol = "obtencion_b";
                    }else{
                        if (fonema == "/d/"){
                            palabracol = "obtencion_d";
                        }else{
                            if (fonema == "/g/"){
                                palabracol = "obtencion_g";
                            }else{
                                if (fonema == "/f/"){
                                    palabracol = "obtencion_f";
                                }else{
                                    if (fonema == "/s/"){
                                        palabracol = "obtencion_s";
                                    }else{
                                        if (fonema == "/x/"){
                                            palabracol = "obtencion_x";
                                        }else{
                                            if (fonema == "/c^/"){
                                                palabracol = "obtencion_ch";
                                            }else{
                                                if (fonema == "/m/"){
                                                    palabracol = "obtencion_m";
                                                }else{
                                                    if (fonema == "/n/"){
                                                        palabracol = "obtencion_n";
                                                    }else{
                                                        if (fonema == "/ñ/"){
                                                            palabracol = "obtencion_ñ";
                                                        }else{
                                                            if (fonema == "/l/"){
                                                                palabracol = "obtencion_l";
                                                            }else{
                                                                if (fonema == "/-r/"){
                                                                    palabracol = "obtencion_r";
                                                                }else{
                                                                    if (fonema == "/r/"){
                                                                        palabracol = "obtencion_rr";
                                                                    }else{
                                                                        palabracol = "obtencion_ll";
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        apalabra = nFirestore.collection(palabracol);


        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String imageUrl = uri.toString();

                        Obtencion palabra= new Obtencion(imageUrl,fonema);
                        apalabra.add(palabra);

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Load_obtencion.this, "Cargado correctamente", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Load_obtencion.this, "La carga falló !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}