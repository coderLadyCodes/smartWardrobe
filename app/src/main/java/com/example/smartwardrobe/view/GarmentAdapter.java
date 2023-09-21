package com.example.smartwardrobe.view;

import android.content.ClipData;
import android.content.Context;
import android.media.RouteListingPreference;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwardrobe.database.Garment;

import java.util.ArrayList;
import java.util.List;

public class GarmentAdapter extends RecyclerView.Adapter<GarmentViewHolder> {

    Context context;
    private List<Garment> garmentList;

    public GarmentAdapter(Context context) {
        this.context = context;
        garmentList = new ArrayList<>();
    }

    @NonNull
    @Override
    public GarmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GarmentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
