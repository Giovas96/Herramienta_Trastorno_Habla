package com.androidavanzado.herramienta_trastorno_habla.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidavanzado.herramienta_trastorno_habla.R;

public class ViewHolder_listapaciente extends RecyclerView.ViewHolder {


    View nview;

    private ViewHolder_listapaciente.ClickListener nClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position); /*SE EJECUTA AL PRESIONAR EL ITEM*/
        void onItemLongClick(View view, int position); /*SE EJECUTA AL MANTENER PRESIONADO EL ITEM*/

    }

    public void setOnClickListener (ViewHolder_listapaciente.ClickListener clickListener){
        nClickListener=clickListener;
    }



    public ViewHolder_listapaciente(@NonNull View itemView) {
        super(itemView);
        nview= itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nClickListener.onItemClick(v, getBindingAdapterPosition ());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                nClickListener.onItemLongClick(v, getBindingAdapterPosition ());
                return false;
            }
        });

    }

    public void SetearDatos(Context context,  String nombre, String apellidopat, String apellidomat, String fechanac, String lugar, String direccion, String telefono, String escuela) {


       //Declarar las vistas
        TextView  nombrepaciente,apellidopatp,apellidomatp,fechanacp,lugarp,direccionp,telefonop,escuelap;

        //Establecer la conexión con el item

        nombrepaciente = nview.findViewById(R.id.nombrepaciente);
        apellidopatp  = nview.findViewById(R.id.apellidopatp);
        apellidomatp  = nview.findViewById(R.id.apellidomatp);
        fechanacp  = nview.findViewById(R.id.fechanacp);
        lugarp  = nview.findViewById(R.id.lugarp);
        direccionp  = nview.findViewById(R.id.direccionp);
        telefonop  = nview.findViewById(R.id.telefonop);
        escuelap  = nview.findViewById(R.id.direccionp);



        //Setear dentro la información dentro del item

        nombrepaciente.setText(nombre);
        apellidopatp.setText(apellidopat);
        apellidomatp.setText(apellidomat);
        fechanacp.setText(fechanac);
        lugarp.setText(lugar);
        direccionp.setText(direccion);
        telefonop.setText(telefono);
        escuelap.setText(escuela);



    }
}
