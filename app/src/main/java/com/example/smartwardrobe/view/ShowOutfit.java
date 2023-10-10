package com.example.smartwardrobe.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartwardrobe.R;
import com.example.smartwardrobe.databinding.FragmentShowOutfitBinding;


public class ShowOutfit extends Fragment {

    FragmentShowOutfitBinding binding;


    public ShowOutfit() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowOutfitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}