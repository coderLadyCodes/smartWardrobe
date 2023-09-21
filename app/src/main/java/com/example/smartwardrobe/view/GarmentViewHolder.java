package com.example.smartwardrobe.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwardrobe.database.Garment;

public class GarmentViewHolder extends RecyclerView.ViewHolder {
   private final Garment garmentView;

    public GarmentViewHolder(@NonNull View itemView, Garment garmentView) {
        super(itemView);
        this.garmentView = garmentView;
    }
    
}
