package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
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

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Load extends AppCompatActivity {

    Spinner spin_fonema, spin_position;
    EditText etName, etSentence;
    Button btnGuardar;
    ImageView imageView, back;
    ProgressBar progressBar;

    Uri imageUri;

    FirebaseFirestore nFirestore;
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    CollectionReference apalabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        spin_fonema = findViewById(R.id.spinner_fonema_load);
        spin_position = findViewById(R.id.spinner_position_load);
        etName = findViewById(R.id.etName_load);
        etSentence = findViewById(R.id.etSentence_load);

        btnGuardar = findViewById(R.id.btnGuardar_load);

        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView_load);

        progressBar.setVisibility(View.INVISIBLE);

        back = findViewById(R.id.back_list_actividad);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Load.this, Listar_actividades.class);
                startActivity(b);
            }
        });

        String [] fonemas = {"/p/","/t/","/k/","/b/","/d/","/g/","/f/","/s/","/x/","/c^/","/m/","/n/","/ñ/","/l/","/ll/","/-r/","/r/"};
        ArrayAdapter<String> adapter_fonema = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fonemas);
        spin_fonema.setAdapter(adapter_fonema);

        String [] position = {"inicial","sílaba directa","sílaba inversa","intervocálica"," sílaba trabada","final","grupo r","grupo l","grupo s"};
        ArrayAdapter<String> adapter_position = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,position);
        spin_position.setAdapter(adapter_position);

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
                    Toast.makeText(Load.this, "Selecciona una imagen por favor", Toast.LENGTH_SHORT).show();
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
            palabracol = "palabra_p";
        }else{
            if (fonema == "/t/"){
                palabracol = "palabra_t";
            }else{
                if (fonema == "/k/"){
                    palabracol = "palabra_k";
                }else{
                    if (fonema == "/b/"){
                        palabracol = "palabra_b";
                    }else{
                        if (fonema == "/d/"){
                            palabracol = "palabra_d";
                        }else{
                            if (fonema == "/g/"){
                                palabracol = "palabra_g";
                            }else{
                                if (fonema == "/f/"){
                                    palabracol = "palabra_f";
                                }else{
                                    if (fonema == "/s/"){
                                        palabracol = "palabra_s";
                                    }else{
                                        if (fonema == "/x/"){
                                            palabracol = "palabra_x";
                                        }else{
                                            if (fonema == "/c^/"){
                                                palabracol = "palabra_ch";
                                            }else{
                                                if (fonema == "/m/"){
                                                    palabracol = "palabra_m";
                                                }else{
                                                    if (fonema == "/n/"){
                                                        palabracol = "palabra_n";
                                                    }else{
                                                        if (fonema == "/ñ/"){
                                                            palabracol = "palabra_ñ";
                                                        }else{
                                                            if (fonema == "/l/"){
                                                                palabracol = "palabra_l";
                                                            }else{
                                                                if (fonema == "/-r/"){
                                                                    palabracol = "palabra_r";
                                                                }else{
                                                                    if (fonema == "/r/"){
                                                                        palabracol = "palabra_rr";
                                                                    }else{
                                                                        palabracol = "palabra_ll";
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

        String position = spin_position.getSelectedItem().toString();
        String name = etName.getText().toString();
        String sentence = etSentence.getText().toString();

        apalabra = nFirestore.collection(palabracol);


        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String imageUrl = uri.toString();

                        Palabra palabra= new Palabra(position,name,sentence,imageUrl);
                        apalabra.document(name).set(palabra);

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Load.this, "Cargado correctamente", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Load.this, "La carga falló !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}