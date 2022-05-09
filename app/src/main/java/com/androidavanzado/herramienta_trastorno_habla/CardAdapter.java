/*package com.androidavanzado.herramienta_trastorno_habla;


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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<listcarElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public CardAdapter (List<listcarElement> itemlist, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.mData=itemlist;
        this.context=context;

    }

    @Override
    public int getItemCount(){
        return  mData.size();
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.inflate(R.layout.listcard_element, null);
        return new CardAdapter.ViewHolder (view);

    }

    @Override
    public void onBindViewHolder(final CardAdapter.ViewHolder holder, final int position){
        holder.bindData (mData.get(position));
    }
    
    public void setItems(List<listcarElement> items) {
        mData=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, apellidopat, apellidomat ,telefono;

        ViewHolder(View itemView){
            super(itemView);
            iconImage=itemView.findViewById(R.id.Imagelistarpaciente);
            name=itemView.findViewById(R.id.nombrepaciente);
            apellidopat=itemView.findViewById(R.id.apellidopatp);
            apellidomat=itemView.findViewById(R.id.apellidomatp);
            telefono=itemView.findViewById(R.id.cardteltutor);

        }

        void bindData (final listcarElement item){

            name.setText(item.getNombre());
           apellidopat.setText(item.getApellidop());
           apellidomat.setText(item.getApellidom());
            telefono.setText(item.getTelefono());
        }
    }
}*/
