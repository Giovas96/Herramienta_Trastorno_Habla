package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Obtencion;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Obtencion_fonema extends AppCompatActivity {
    // creando variables para ver paginador,
    // Lineralyout, adaptador y nuestra lista de arreglos.
    private ViewPager viewPager;
    private LinearLayout dotsLL;
    AdapterObtencion adapter;
    private ArrayList<Obtencion> sliderModalArrayList;
    private TextView[] dots;
    int size;
    //game options
    String fonema;
    String position;

    FirebaseFirestore nFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtencion_fonema);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        // inicializando todas nuestras vistas.
        nFirestore=FirebaseFirestore.getInstance();
        viewPager = findViewById(R.id.idViewPager);
        dotsLL = findViewById(R.id.idLLDots);
        Bundle bundle = getIntent().getExtras();
        fonema = bundle.getString("fonema");
        position = bundle.getString("fon");

        // en la l??nea de abajo estamos creando una nueva lista de arreglos.
        sliderModalArrayList = new ArrayList<>();
        loadDataFromFirebase();

        // m??todo de llamada para agregar indicador de puntos
        addDots(size, 0);

        // debajo de la l??nea se usa para llamar a la p??gina
        // cambia el m??todo de escucha.
        viewPager.addOnPageChangeListener(viewListner);


    }
    private void loadDataFromFirebase() {

        // la l??nea de abajo se usa para obtener datos de Firebase
        // firestore usando la colecci??n en android.
        nFirestore.collection(fonema)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // despu??s de obtener los datos, llamamos al m??todo de ??xito
                        // y dentro de este m??todo estamos comprobando si se ha recibido
                        // la instant??nea de la consulta est?? vac??a o no.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // si la instant??nea no est?? vac??a, estamos ocultando nuestra
                            // barra de progreso y agregando nuestros datos en una lista.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // despu??s de obtener esta lista estamos pasando
                                // esa lista a nuestra clase de objeto.
                                Obtencion sliderModal = d.toObject(Obtencion.class);

                                // despu??s de obtener datos de Firebase estamos
                                // almacenando esos datos en nuestra lista de arreglos
                                sliderModalArrayList.add(sliderModal);
                            }
                            // La siguiente l??nea se usa para agregar nuestra lista de matrices a la clase de adaptador.
                            adapter=new AdapterObtencion(Obtencion_fonema.this,sliderModalArrayList);

                            // la l??nea de abajo se usa para establecer nuestro
                            // adaptador a nuestro buscapersonas de vista.
                            viewPager.setAdapter(adapter);

                            // estamos almacenando el tama??o de nuestro
                            // lista de arreglos en una variable.
                            size = sliderModalArrayList.size();
                        } else {
                            // si la instant??nea est?? vac??a, estamos mostrando un mensaje de brindis.
                            Toast.makeText(Obtencion_fonema.this, "No hay fonemas registrados o no hay posiciones con ese fonema", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // estamos mostrando un mensaje de brindis cuando
                // recibimos cualquier error de Firebase.
                Toast.makeText(Obtencion_fonema.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
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
                Toast toastfaildata = Toast.makeText(getApplicationContext(), "Fall?? en cargar imagenes!!", Toast.LENGTH_LONG);
                toastfaildata.show();
            }
        });
    }
*/

    private void addDots(int size, int pos) {
        // dentro de este m??todo estamos
        // creando una nueva vista de texto.
        dots = new TextView[size];

        /* la l??nea de abajo se usa para eliminar todas
         las vistas desde el dise??o lineal.*/
        dotsLL.removeAllViews();

        // ejecutando un bucle for para agregar un n??mero
        // de puntos a nuestro control deslizante.
        for (int i = 0; i < size; i++) {
            // la l??nea de abajo se usa para agregar los puntos
            // y modificar su color.
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("???"));
            dots[i].setTextSize(35);

            // debajo de la l??nea se llama cuando el
            // los puntos no est??n seleccionados.
            dots[i].setTextColor(getResources().getColor(R.color.white));
            dotsLL.addView(dots[i]);
        }
        if (dots.length > 0) {
            // esta l??nea se llama cuando los puntos
            // se selecciona el dise??o lineal interior
            dots[pos].setTextColor(getResources().getColor(R.color.purple_200));
        }
    }

    // creando un m??todo para ver el buscapersonas para el detector de cambios en la p??gina.
    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            // estamos llamando a nuestro m??todo de puntos para
            // cambia la posici??n de los puntos seleccionados.
            addDots(size, position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}