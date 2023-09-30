package com.example.smartwardrobe.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartwardrobe.GarmentViewModel;
import com.example.smartwardrobe.database.Garment;
import com.example.smartwardrobe.database.GarmentAdapter;
import com.example.smartwardrobe.database.GarmentDAO;
import com.example.smartwardrobe.databinding.FragmentGarmentListBinding;
import com.example.smartwardrobe.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;


public class GarmentList extends Fragment {
    private GarmentViewModel garmentViewModel;
    private GarmentDAO garmentDAO;
    List<Garment> garmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GarmentAdapter garmentAdapter;
    FragmentGarmentListBinding binding;
    ItemViewBinding ibinding;

    public GarmentList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGarmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        garmentViewModel = new ViewModelProvider(this).get(GarmentViewModel.class);
        garmentViewModel.getListGarments().observe(getViewLifecycleOwner(), new Observer<List<Garment>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Garment> garments) {
                garmentList.clear();
                garmentList.addAll(garments);
                if (garmentAdapter != null) {
                    garmentAdapter.notifyDataSetChanged();
                }
            }
        });

        setUpRecyclerView();

    }

    void setUpRecyclerView(){

        RecyclerView recyclerView = binding.fragmentGarmentList;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        garmentAdapter = new GarmentAdapter(garmentList, getContext());
        recyclerView.setAdapter(garmentAdapter);


        garmentAdapter.setOnDeleteClickListener(new GarmentAdapter.OnDeleteClickListener() {

            @Override
            public void onDeleteClick(Garment garment, int position) {
                showDeleteConfirmationDialog(position);
            }

        });
    }


    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (position != RecyclerView.NO_POSITION) {
                            if (garmentViewModel != null) {
                                garmentViewModel.deleteGarment(garmentList.get(position));
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the ViewBinding reference to avoid memory leaks
    }

}