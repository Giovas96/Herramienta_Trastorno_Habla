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

public class CardAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
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
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=mInflater.inflate(R.layout.listcard_element, null);
        return new ListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    
    public void setItems(List<listcarElement> items) {
        mData=items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, tutor,telefono;

        ViewHolder(View itemView){
            super(itemView);
            iconImage=itemView.findViewById(R.id.ImageView);
            name=itemView.findViewById(R.id.nombrepaciente);
            tutor=itemView.findViewById(R.id.nombretutor);
            telefono=itemView.findViewById(R.id.cardteltutor);

        }

        void binData (final listcarElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getNombre());
            tutor.setText(item.getTutor());
            telefono.setText(item.getTelefono());
        }
    }
}
