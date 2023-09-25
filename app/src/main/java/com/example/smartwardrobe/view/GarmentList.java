package com.example.smartwardrobe.view;

import android.annotation.SuppressLint;
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
import com.example.smartwardrobe.databinding.FragmentGarmentListBinding;
import com.example.smartwardrobe.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;


public class GarmentList extends Fragment {
    private GarmentViewModel garmentViewModel;
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
                // Update your garmentList when data changes
                garmentList.clear();
                garmentList.addAll(garments);
                garmentAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
            }
        });

        setUpRecyclerView();

        ibinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    void setUpRecyclerView(){

        RecyclerView recyclerView = binding.fragmentGarmentList;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        garmentAdapter = new GarmentAdapter(garmentList);
        recyclerView.setAdapter(garmentAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the ViewBinding reference to avoid memory leaks
    }
}