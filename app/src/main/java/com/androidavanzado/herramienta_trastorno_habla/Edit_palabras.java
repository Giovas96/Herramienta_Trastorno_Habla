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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Edit_palabras extends AppCompatActivity {

    TextView spin_fonema, spin_position;
    EditText etName, etSentence;
    Button btnGuardar;
    ImageView imageView, back, edit;
    ProgressBar progressBar;
    String fonema;
    String id;
    Uri imageUri;

    FirebaseFirestore nFirestore;
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    CollectionReference apalabra;
    DocumentReference epalabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_palabras);
        nFirestore= FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        fonema = bundle.getString("fonema");
        id = bundle.getString("id");

        spin_position = findViewById(R.id.spinner_position_load_e);
        etName = findViewById(R.id.etName_load_e);
        etSentence = findViewById(R.id.etSentence_load_e);

        btnGuardar = findViewById(R.id.btnGuardar_load_e);

        progressBar = findViewById(R.id.progressBar_e);
        imageView = findViewById(R.id.imageView_load_e);

        progressBar.setVisibility(View.INVISIBLE);

        back = findViewById(R.id.back_list_actividad);
        edit=findViewById(R.id.edit_palabras);
        edit.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Edit_palabras.this, Opciones_editar_palabras.class);
                startActivity(b);
            }
        });
        seteardatos();
       /* String [] fonemas = {"/p/","/t/","/k/","/b/","/d/","/g/","/f/","/s/","/x/","/c^/","/m/","/n/","/ñ/","/l/","/ll/","/-r/","/r/"};
        ArrayAdapter<String> adapter_fonema = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fonemas);
        spin_fonema.setAdapter(adapter_fonema);

        String [] position = {"inicial","sílaba directa","sílaba inversa","intervocálica"," sílaba trabada","final","grupo r","grupo l","grupo s"};
        ArrayAdapter<String> adapter_position = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,position);
        spin_position.setAdapter(adapter_position);*/



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

                if (imageUri != null) {

                    cargarAFirestore(imageUri);

                } else {
                    editarafirestore();
                    //Toast.makeText(Edit_palabras.this, "Selecciona una imagen por favor", Toast.LENGTH_SHORT).show();

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

        String palabracol=fonema;



        String position = spin_position.getText().toString();
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
                        Toast.makeText(Edit_palabras.this, "Cargado correctamente", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Edit_palabras.this, "La carga falló !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void editarafirestore(){
        String palabracol=fonema;



        String position = spin_position.getText().toString();
        String name = etName.getText().toString();
        String sentence = etSentence.getText().toString();



        epalabra= nFirestore.collection(palabracol).document(id);
        epalabra.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Palabra palabra= documentSnapshot.toObject(Palabra.class);
                        palabra.setName(name);
                        palabra.setSentence(sentence);
                        epalabra.set(palabra);

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Edit_palabras.this, "Cargado correctamente", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

                }

        });
    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    private void seteardatos(){
        epalabra= nFirestore.collection(fonema).document(id);
              epalabra.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               Palabra palabra = documentSnapshot.toObject(Palabra.class);

                  String name= palabra.getName();
                  String desc= palabra.getSentence();
                  String pos= palabra.getPosition();
                  String url= palabra.getImageUrl();

                  etName.setText(name);
                  spin_position.setText(pos);
                  etSentence.setText(desc);
                Picasso.get().load(url).into(imageView);
            }
        });


    }


}