package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Galeria_integracion extends AppCompatActivity {

  //  List<Palabra> simpleArray, nameArray, sentenceArray;
     ArrayList<Palabra> simpleArray = new ArrayList<>();
    String images[], name[],sente[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_integracion);
        ViewPager pager= (ViewPager)findViewById(R.id.pager);
        FirebaseFirestore nFirestore=FirebaseFirestore.getInstance();



        simpleArray.clear();
        nFirestore.collection("palabra_t")
                .whereEqualTo("position", true)
                .limit(8).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){

                    Palabra palabra = snapshot.toObject(Palabra.class);
                    simpleArray.add(palabra);
                 /*   simpleArray.add(snapshot.getString("imageUrl"));
                    nameArray.add(snapshot.getString("name"));
                    sentenceArray.add(snapshot.getString("sentence"));*/

                }
                /*name= new String[nameArray.size()];
                nameArray.toArray(name);

                images = new String[ simpleArray.size() ];
                simpleArray.toArray(images);

                sente= new String [sentenceArray.size()];
                sentenceArray.toArray(sente);

                System.out.println(Arrays.toString(name));
                System.out.println(Arrays.toString(images));
                System.out.println(Arrays.toString(sente));*/

                pager.setAdapter(new Adapterfotos(getApplicationContext(), simpleArray));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Falló en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });
    }
}