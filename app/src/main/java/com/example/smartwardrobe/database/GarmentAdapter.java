package com.example.smartwardrobe.database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwardrobe.GarmentViewModel;
import com.example.smartwardrobe.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;

public class GarmentAdapter  extends RecyclerView.Adapter<GarmentAdapter.GarmentViewHolder> {
    private static List<Garment> garmentList;
    private static Context context;
    GarmentDAO garmentDAO;


    public GarmentAdapter(List<Garment> garmentList, Context context) {
        this.garmentList = garmentList;
        this.context = context;
    }
    private static OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Garment garment, int posiion);
    }
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }

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

        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDeleteClickListener != null) {
                    Garment currentGarment = garmentList.get(position);
                    onDeleteClickListener.onDeleteClick(currentGarment, position);
                }
            }
        });

    }

    public void deleteItem(int position) {
        garmentList.remove(position);
        notifyItemRemoved(position);
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

       ItemViewBinding binding;

        public GarmentViewHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Garment currentGarment = garmentList.get(position);
                        onDeleteClickListener.onDeleteClick(currentGarment, position);
                        showDeleteConfirmationDialog(garmentList.get(position));
                }
            };
        });}

        private void showDeleteConfirmationDialog(final Garment garmentToDelete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirm Deletion");
            builder.setMessage("Are you sure you want to delete this item?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.onDeleteClick(garmentToDelete, getAbsoluteAdapterPosition()); //not sure
                    }
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
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
    }}

