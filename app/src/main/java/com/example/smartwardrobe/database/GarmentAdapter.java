package com.example.smartwardrobe.database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwardrobe.GarmentViewModel;
import com.example.smartwardrobe.R;
import com.example.smartwardrobe.databinding.ItemViewBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GarmentAdapter  extends RecyclerView.Adapter<GarmentAdapter.GarmentViewHolder> {
    private static List<Garment> garmentList;
    private static Context context;
    GarmentDAO garmentDAO;
    private OnModifyClickListener onModifyClickListener;


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
    public interface OnModifyClickListener {
        void onModifyClick(Garment garment);
    }
    public void setOnModifyClickListener(OnModifyClickListener onModifyClickListener) {
        this.onModifyClickListener = onModifyClickListener;
    }

    @NonNull
    @Override
    public GarmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GarmentViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GarmentViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        holder.binding.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onModifyClickListener != null) {
                    onModifyClickListener.onModifyClick(garment);
                }
            }
        });
    }

    public void deleteItem(int position) {
//        Garment deletedGarment  = garmentList.get(position);
//        notifyItemRemoved(position);
//        garmentList.remove(position);

///////////////////////////////////PHOTO IS NOT DELETED FROM EXTERNAL STORAGE WHEN I DELETE GARMENT////////////////////////////////////////////
        if (position >= 0 && position < garmentList.size()) {
            Garment deletedGarment = garmentList.get(position);
            garmentList.remove(position);
            notifyItemRemoved(position);

        if (deletedGarment != null) {
            String imagePath = deletedGarment.getPhoto();
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    boolean deleted = imageFile.delete();
                    if (deleted) {
                        // Image deleted successfully
                        Log.d("ImageDeletion", "Image deleted: " + imagePath);
                    } else {
                        // Handle deletion failure here (e.g., show a Toast or log an error message)
                        Log.e("ImageDeletion", "Failed to delete image: " + imagePath);
                    }
                } else {
                    // File does not exist at the specified path
                    Log.e("ImageDeletion", "File does not exist: " + imagePath);
                }
            } else {
                // Image path is null or empty
                Log.e("ImageDeletion", "Invalid image path");
            }
        }
    }}
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
                        onDeleteClickListener.onDeleteClick(garmentToDelete, getAbsoluteAdapterPosition());
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

