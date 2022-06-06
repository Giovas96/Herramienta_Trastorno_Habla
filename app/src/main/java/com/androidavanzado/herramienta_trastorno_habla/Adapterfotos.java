package com.androidavanzado.herramienta_trastorno_habla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapterfotos extends PagerAdapter {

    // creando variables para el inflador de diseño,
    // contexto y lista de arreglos.
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Palabra> sliderModalArrayList;

    // creating constructor.
    public Adapterfotos(Context context, ArrayList<Palabra> sliderModalArrayList) {
        this.context = context;
        this.sliderModalArrayList = sliderModalArrayList;
    }

    @Override
    public int getCount() {
        // dentro del método get count regresando
        // el tamaño de nuestra lista de arreglos.
        return sliderModalArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // dentro del método isViewFrombject estamos
        // devolviendo nuestro objeto de RelativeLayout.
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // en este método inicializaremos todos nuestros elementos de diseño
        // e inflar nuestro archivo de diseño también.
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        // La línea de abajo se usa para inflar el archivo de diseño que creamos.
        View view = layoutInflater.inflate(R.layout.item_foto, container, false);

        // inicializando nuestras vistas.
        ImageView imageView = view.findViewById(R.id.idIV);
        TextView titleTV = view.findViewById(R.id.idTVtitle);
        TextView headingTV = view.findViewById(R.id.idTVheading);

        // configurando datos para nuestras vistas.
        Palabra modal = sliderModalArrayList.get(position);
        titleTV.setText(modal.getName());
        headingTV.setText(modal.getSentence());
        Picasso.get().load(modal.getImageUrl()).into(imageView);

        // después de configurar los datos en nuestras vistas,
        // estamos agregando la vista a nuestro contenedor.
        container.addView(view);

        // por fin estamos devolviendo la vista.
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // este es un método de vista de destrucción que
        // se usa para eliminar una vista.
        container.removeView((RelativeLayout) object);
    }
}