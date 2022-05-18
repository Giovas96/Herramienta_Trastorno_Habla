package com.androidavanzado.herramienta_trastorno_habla.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidavanzado.herramienta_trastorno_habla.R;

public class ViewHolder_listaevaluacion extends RecyclerView.ViewHolder{
    View nview;
    private ViewHolder_listaevaluacion.ClickListener nClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder_listaevaluacion.ClickListener clickListener) {
        nClickListener = clickListener;
    }

    public ViewHolder_listaevaluacion(@NonNull View itemView) {
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

    public void SetearDatos(Context context, String fonema, String inicial, String directa, String inversa,
                            String intervolica, String trabada, String fina, String gr, String gl, String gs,
                            String observacion, String adquirido) {


        //Declarar las vistas
        TextView fone, ini,dir, inv,inte, tra,fi, grur,grul, grus, obs, adq;

        //Establecer la conexión con el item
        fone=nview.findViewById(R.id.fonema);
        ini=nview.findViewById(R.id.inicial);
        dir=nview.findViewById(R.id.directa);
        inv=nview.findViewById(R.id.inversa);
        inte=nview.findViewById(R.id.intervocalica);
        tra=nview.findViewById(R.id.trabada);
        fi=nview.findViewById(R.id.finals);
        grur=nview.findViewById(R.id.grupor);
        grul=nview.findViewById(R.id.grupol);
        grus=nview.findViewById(R.id.grupos);
        obs=nview.findViewById(R.id.observacion);
        adq=nview.findViewById(R.id.adquirido);




        //Setear dentro la información dentro del item
        fone.setText(fonema);
        ini.setText(inicial);
        dir.setText(directa);
        inv.setText(inversa);
        inte.setText(intervolica);
        tra.setText(trabada);
        fi.setText(fina);
        grur.setText(gr);
        grul.setText(gl);
        grus.setText(gs);
        obs.setText(observacion);
        adq.setText(adquirido);

    }
}
