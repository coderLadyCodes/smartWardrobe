package com.example.smartwardrobe.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwardrobe.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;

public class GarmentAdapter  extends RecyclerView.Adapter<GarmentAdapter.GarmentViewHolder> {
    private final List<Garment> garmentList;
    GarmentDAO garmentDAO;


    public GarmentAdapter(List<Garment> garmentList) {
        this.garmentList = garmentList;
    }
//
//    public void deleteGarment(int position) {
//        // Delete the garment from the database
//        Garment garmentToDelete = garmentList.get(position);
//        garmentDAO.deleteGarment(garmentToDelete);
//
//        // Remove the garment from the list
//        garmentList.remove(position);
//
//        // Notify the adapter of the item removal
//        notifyItemRemoved(position);
//    }
//
//    public void updateGarment(int position, Garment updatedGarment) {
//        // Update the garment in the database
//        garmentDAO.updateGarment(updatedGarment);
//
//        // Update the garment in the list
//        garmentList.set(position, updatedGarment);
//
//        // Notify the adapter of the data change
//        notifyItemChanged(position);
//    }

    @NonNull
    @Override
    public GarmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GarmentViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GarmentViewHolder holder, int position) {
     Garment garment = garmentList.get(position);

     Bitmap bitmap = BitmapFactory.decodeFile(garment.getPhoto());
     holder.binding.clothimage.setImageBitmap(bitmap);
     holder.binding.clothcategory.setText(garment.getCategorization().toString());
     holder.binding.clothwarmth.setText(garment.getWarmth().toString());
     holder.binding.clothcomfort.setText("Comfort: " + garment.isComfort());
     holder.binding.clothloose.setText("Loose: " + garment.isLoose());
     holder.binding.clothfancy.setText("Fancy: " + garment.isFancy());
     holder.binding.clothloose.setText("Loose: " + garment.isLoose());
     holder.binding.clothcolor.setText("Color : " + garment.getColor());
        holder.bind(garment);
    }

    public void setGarments(List<Garment> garments){
        garmentList.clear();
        garmentList.addAll(garments);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return garmentList.size();
    }

    static class GarmentViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewBinding binding;
        public GarmentViewHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        @SuppressLint("SetTextI18n")
        public void bind(Garment garment) {
            binding.clothcategory.setText("Category: " + garment.getCategorization().toString());
            binding.clothwarmth.setText("Warmth: " + garment.getWarmth().toString());
            binding.clothcomfort.setText("Comfort: " + garment.isComfort());
            binding.clothloose.setText("Loose: " + garment.isLoose());
            binding.clothfancy.setText("Fancy: " + garment.isFancy());
            binding.clothloose.setText("Loose: " + garment.isLoose());
            binding.clothcolor.setText("Color : " + garment.getColor());
            Bitmap bitmap = BitmapFactory.decodeFile(garment.getPhoto());
            binding.clothimage.setImageBitmap(bitmap);
        }
    }

}
