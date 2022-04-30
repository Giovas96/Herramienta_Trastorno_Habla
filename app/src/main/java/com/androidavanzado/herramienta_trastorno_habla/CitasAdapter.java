package com.androidavanzado.herramienta_trastorno_habla;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.ViewHolder> {
    private List<CitasElement> nData;
    private LayoutInflater nInflater;
    private Context context;

    public CitasAdapter (List<CitasElement> itemlist, Context context){
        this.nInflater=LayoutInflater.from(context);
        this.nData=itemlist;
        this.context=context;

    }

    @Override
    public int getItemCount(){
        return  nData.size();
    }

    @Override
    public CitasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=nInflater.inflate(R.layout.citas_element, null);
        return new CitasAdapter.ViewHolder (view);

    }

    @Override
    public void onBindViewHolder(final CitasAdapter.ViewHolder holder, final int position){
        holder.bindData (nData.get(position));
    }

    public void setItems(List<CitasElement> items) {
        nData=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, fecha, hora,motivo;

        ViewHolder(View itemView){
            super(itemView);
            iconImage=itemView.findViewById(R.id.ImageView);
            name=itemView.findViewById(R.id.nombrepaciente);
            fecha=itemView.findViewById(R.id.fechacita);
            hora=itemView.findViewById(R.id.horacita);
            motivo=itemView.findViewById(R.id.motivocita);

        }

        void bindData (final CitasElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getNombre());
            fecha.setText(item.getFecha());
            hora.setText(item.getHora());
            motivo.setText(item.getMotivo());
        }
    }
}


