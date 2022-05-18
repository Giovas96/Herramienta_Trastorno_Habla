package com.androidavanzado.herramienta_trastorno_habla.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidavanzado.herramienta_trastorno_habla.R;

public class ViewHolder_listacita extends RecyclerView.ViewHolder {

    View nview;
    private ViewHolder_listacita.ClickListener nClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder_listacita.ClickListener clickListener) {
        nClickListener = clickListener;
    }

    public ViewHolder_listacita(@NonNull View itemView) {
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

    public void SetearDatos(Context context, String fecha, String hora, String duracion, String motivacion, String observacion) {


        //Declarar las vistas
        TextView fec, hour, dur, mot, obs;

        //Establecer la conexión con el item

        fec = nview.findViewById(R.id.fechacitalistar);
        hour = nview.findViewById(R.id.horacitalistar);
        dur = nview.findViewById(R.id.duracioncita);
        mot = nview.findViewById(R.id.motivocitalistar);
        obs = nview.findViewById(R.id.observacioncitalistar);


        //Setear dentro la información dentro del item

        fec.setText(fecha);
        hour.setText(hora);
        dur.setText(duracion);
        mot.setText(motivacion);
        obs.setText(observacion);

    }

}
