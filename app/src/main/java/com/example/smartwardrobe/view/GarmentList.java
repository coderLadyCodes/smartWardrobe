package com.example.smartwardrobe.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;


public class GarmentList extends Fragment {
    private GarmentViewModel garmentViewModel;
    List<Garment> garmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GarmentAdapter garmentAdapter;
    FragmentGarmentListBinding binding;

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
        setUpRecyclerView();
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