package com.androidavanzado.herramienta_trastorno_habla.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidavanzado.herramienta_trastorno_habla.R;

import java.util.Objects;

import static java.util.Objects.*;

public class ViewHolder_listanota extends RecyclerView.ViewHolder {

    View nview;
    private ViewHolder_listanota.ClickListener nClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder_listanota.ClickListener clickListener) {
        nClickListener = clickListener;
    }

    public ViewHolder_listanota (@NonNull View itemView) {
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
    public void SetearDatos(Context context, String titulo, String fecha, String asunto, String resumen, String descripcion) {


        //Declarar las vistas
        TextView tit, fec, asun, res, desc;

        //Establecer la conexión con el item

       tit = nview.findViewById(R.id.titulolistarnota);
        fec = nview.findViewById(R.id.fechalistarnota);
        asun = nview.findViewById(R.id.asuntonota);
        res = nview.findViewById(R.id.resumennota);
        desc = nview.findViewById(R.id.descripcionnota);


        //Setear dentro la información dentro del item

       tit.setText(titulo);
        fec.setText(fecha);
        asun.setText(asunto);
        res.setText(resumen);
        desc.setText(descripcion);

    }

}
