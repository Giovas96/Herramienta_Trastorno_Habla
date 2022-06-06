package com.androidavanzado.herramienta_trastorno_habla.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidavanzado.herramienta_trastorno_habla.R;
import com.squareup.picasso.Picasso;

public class ViewHolder_palabras extends RecyclerView.ViewHolder {

    View nview;
    private ViewHolder_palabras.ClickListener nClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder_palabras.ClickListener clickListener) {
        nClickListener = clickListener;
    }

    public ViewHolder_palabras(@NonNull View itemView) {
        super(itemView);
        nview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nClickListener.onItemClick(v, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                nClickListener.onItemLongClick(v, getBindingAdapterPosition());
                return false;
            }
        });
    }

    public void SetearDatos(Context context, String name, String sentence, String position, String url) {


        //Declarar las vistas
        ImageView imageurl;
        TextView nomb, pos, sent;

        //Establecer la conexión con el item

        imageurl = nview.findViewById(R.id.Imagepalabra);
        nomb = nview.findViewById(R.id.namepalabra);
        sent = nview.findViewById(R.id.sentencepalabra);
        pos = nview.findViewById(R.id.positionpalabra);



        //Setear dentro la información dentro del item

        Picasso.get().load(url).into(imageurl);
       nomb.setText(name);
        sent.setText(sentence);
        pos.setText(position);

    }

}
