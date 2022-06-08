package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Galeria_integracion extends AppCompatActivity {

    // creando variables para ver paginador,
    // Lineralyout, adaptador y nuestra lista de arreglos.
    private ViewPager viewPager;
    private LinearLayout dotsLL;
    Adapterfotos adapter;
    private ArrayList<Palabra> sliderModalArrayList;
    private TextView[] dots;
    int size;
    //game options
    String fonema;
    String position;

    FirebaseFirestore nFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_integracion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        // inicializando todas nuestras vistas.
        nFirestore=FirebaseFirestore.getInstance();
        viewPager = findViewById(R.id.idViewPager);
        dotsLL = findViewById(R.id.idLLDots);
        Bundle bundle = getIntent().getExtras();
        fonema = bundle.getString("fonema");
        position = bundle.getString("position");

        // en la línea de abajo estamos creando una nueva lista de arreglos.
        sliderModalArrayList = new ArrayList<>();
        loadDataFromFirebase();

        // método de llamada para agregar indicador de puntos
        addDots(size, 0);

        // debajo de la línea se usa para llamar a la página
        // cambia el método de escucha.
        viewPager.addOnPageChangeListener(viewListner);
    }

    private void loadDataFromFirebase() {

        // la línea de abajo se usa para obtener datos de Firebase
        // firestore usando la colección en android.
        nFirestore.collection(fonema)
                .whereEqualTo("position", position)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // después de obtener los datos, llamamos al método de éxito
                        // y dentro de este método estamos comprobando si se ha recibido
                        // la instantánea de la consulta está vacía o no.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // si la instantánea no está vacía, estamos ocultando nuestra
                            // barra de progreso y agregando nuestros datos en una lista.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // después de obtener esta lista estamos pasando
                                // esa lista a nuestra clase de objeto.
                                Palabra sliderModal = d.toObject(Palabra.class);

                                // después de obtener datos de Firebase estamos
                                // almacenando esos datos en nuestra lista de arreglos
                                sliderModalArrayList.add(sliderModal);
                            }
                            // La siguiente línea se usa para agregar nuestra lista de matrices a la clase de adaptador.
                            adapter=new Adapterfotos(Galeria_integracion.this,sliderModalArrayList);

                            // la línea de abajo se usa para establecer nuestro
                            // adaptador a nuestro buscapersonas de vista.
                            viewPager.setAdapter(adapter);

                            // estamos almacenando el tamaño de nuestro
                            // lista de arreglos en una variable.
                            size = sliderModalArrayList.size();
                        } else {
                            // si la instantánea está vacía, estamos mostrando un mensaje de brindis.
                            Toast.makeText(Galeria_integracion.this, "No hay fonemas registrados o no hay posiciones con ese fonema", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // estamos mostrando un mensaje de brindis cuando
                // recibimos cualquier error de Firebase.
                Toast.makeText(Galeria_integracion.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
        nFirestore.collection("palabra_t")
                .whereEqualTo("position", true)
                .limit(8).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot snapshot : task.getResult()){

                    Palabra palabra = snapshot.toObject(Palabra.class);
                    String imge =palabra.getImageUrl();
                    String nomb=palabra.getName();
                    String desc= palabra.getSentence();

                    Picasso.get().load(imge).into(foto);

                    name.setText(nomb);
                    descrip.setText(desc);


                    addFragment(new Step.Builder().setTitle(nomb)
                            .setContent(desc)
                            .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                            .build());

                }
                setPrevText(""); // Previous button text
                setNextText(""); // Next button text
                setFinishText(""); // Finish button text
                setCancelText(""); // Cancel button text

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Falló en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });
    }
*/

    private void addDots(int size, int pos) {
        // dentro de este método estamos
        // creando una nueva vista de texto.
        dots = new TextView[size];

        /* la línea de abajo se usa para eliminar todas
         las vistas desde el diseño lineal.*/
        dotsLL.removeAllViews();

        // ejecutando un bucle for para agregar un número
        // de puntos a nuestro control deslizante.
        for (int i = 0; i < size; i++) {
            // la línea de abajo se usa para agregar los puntos
            // y modificar su color.
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("•"));
            dots[i].setTextSize(35);

            // debajo de la línea se llama cuando el
            // los puntos no están seleccionados.
            dots[i].setTextColor(getResources().getColor(R.color.white));
            dotsLL.addView(dots[i]);
        }
        if (dots.length > 0) {
            // esta línea se llama cuando los puntos
            // se selecciona el diseño lineal interior
            dots[pos].setTextColor(getResources().getColor(R.color.purple_200));
        }
    }

    // creando un método para ver el buscapersonas para el detector de cambios en la página.
    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            // estamos llamando a nuestro método de puntos para
            // cambia la posición de los puntos seleccionados.
            addDots(size, position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}