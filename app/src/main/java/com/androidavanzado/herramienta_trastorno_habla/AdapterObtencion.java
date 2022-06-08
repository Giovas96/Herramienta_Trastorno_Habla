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

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Obtencion;
import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterObtencion extends PagerAdapter {

    // creando variables para el inflador de diseño,
    // contexto y lista de arreglos.
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Obtencion> sliderModalArrayList;

    // creating constructor.
    public AdapterObtencion(Context context, ArrayList<Obtencion> sliderModalArrayList) {
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
        View view = layoutInflater.inflate(R.layout.item_obtencion, container, false);

        // inicializando nuestras vistas.
        ImageView imageView = view.findViewById(R.id.idIV_obtencion);
        TextView titleTV = view.findViewById(R.id.idTVtitle_obtencion);


        // configurando datos para nuestras vistas.
        Obtencion modal = sliderModalArrayList.get(position);
        titleTV.setText(modal.getFonema());
        Picasso.get().load(modal.getUrl()).into(imageView);

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