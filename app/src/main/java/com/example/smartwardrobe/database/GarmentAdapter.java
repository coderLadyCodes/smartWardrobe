package com.example.smartwardrobe.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwardrobe.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;

public class GarmentAdapter  extends RecyclerView.Adapter<GarmentAdapter.GarmentViewHolder> {
    private List<Garment> garments = new ArrayList<>();

    public void setGarments(List<Garment> garments) {
        this.garments = garments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GarmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GarmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GarmentViewHolder holder, int position) {
     Garment currentGarment = garments.get(position);
     holder.bind(currentGarment);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class GarmentViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewBinding binding;
        public GarmentViewHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Garment garment) {
            binding.clothcategory.setText("Category: " + garment.getCategorization().toString());
        }


    }
}
