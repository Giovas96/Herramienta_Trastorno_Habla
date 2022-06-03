package com.androidavanzado.herramienta_trastorno_habla;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.androidavanzado.herramienta_trastorno_habla.Objetos.Palabra;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class Adapterfotos extends PagerAdapter {
    List<Palabra> simple, nameArray, sentenceArray;
    LayoutInflater inflater;
    TextView name, description;
    ImageView image;
    Context context;

    public Adapterfotos (Context aplicattionContext, List<Palabra> arrayListname){
            simple= arrayListname;
            inflater= LayoutInflater.from(aplicattionContext);
            context=aplicattionContext;
    }

    @Override
    public int getCount() {
        return simple.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instatiateItem(@NonNull ViewGroup container, int position){

        View v= inflater.inflate(R.layout.item_foto, container, false);
        assert v!=null;
        {
            name=(TextView)v.findViewById(R.id.textViewname);
            image=(ImageView)v.findViewById(R.id.imageViewgaler);
            description=(TextView)v.findViewById(R.id.textViewsentence);

        }
        Picasso.get().load(simple.get(position).getImageUrl()).into(image);
        LinearLayout layout= new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);
        layout.addView(v);

        container.addView(layout);

       return layout;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }

}
