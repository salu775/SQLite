package com.example.test1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.DbViewHolderClass> {
    private ArrayList<ModelClass> objectModelClassArrayList;

    public AdapterClass(ArrayList<ModelClass> objectModelClassArrayList) {
        this.objectModelClassArrayList = objectModelClassArrayList;
    }

    @NonNull
    @Override
    public DbViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        DbViewHolderClass objectDbViewHolderClass = new DbViewHolderClass(singleItem);
        return objectDbViewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull DbViewHolderClass holder, int position) {
        holder.nameTV.setText(objectModelClassArrayList.get(position).getName());
        holder.locationTV.setText(objectModelClassArrayList.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return objectModelClassArrayList.size();
    }

    class DbViewHolderClass extends RecyclerView.ViewHolder
    {
        TextView nameTV, locationTV;
        public DbViewHolderClass(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.single_item_NameTV);
            locationTV=itemView.findViewById(R.id.single_item_LocationTV);
        }
    }
}
