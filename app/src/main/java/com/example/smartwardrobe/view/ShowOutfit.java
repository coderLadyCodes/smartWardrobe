package com.example.smartwardrobe.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.smartwardrobe.databinding.FragmentShowOutfitBinding;
import com.example.smartwardrobe.database.Garment;

public class ShowOutfit extends Fragment {
    private SharedViewModel sharedViewModel;
    private FragmentShowOutfitBinding binding;

    public ShowOutfit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowOutfitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Bundle args = getArguments();
        if (args != null && args.containsKey("selectedOutfit")) {
            Outfit selectedOutfit = args.getParcelable("selectedOutfit");

            // Display outfit photos, check for null Garment objects
            if (selectedOutfit != null) {
                if (selectedOutfit.getTop() != null) {
                    Garment topGarment = selectedOutfit.getTop();
                    if (topGarment != null) {
                        String topPhoto = topGarment.getPhoto();
                        if (topPhoto != null) {
                            Uri topUri = Uri.parse(topPhoto);
                            binding.top.setImageURI(topUri);
                        }
                    }
                }

                if (selectedOutfit.getBottom() != null) {
                    Garment bottomGarment = selectedOutfit.getBottom();
                    if (bottomGarment != null) {
                        String bottomPhoto = bottomGarment.getPhoto();
                        if (bottomPhoto != null) {
                            Uri bottomUri = Uri.parse(bottomPhoto);
                            binding.bottom.setImageURI(bottomUri);
                        }
                    }
                }

                if (selectedOutfit.getShoes() != null) {
                    Garment shoesGarment = selectedOutfit.getShoes();
                    if (shoesGarment != null) {
                        String shoesPhoto = shoesGarment.getPhoto();
                        if (shoesPhoto != null) {
                            Uri shoesUri = Uri.parse(shoesPhoto);
                            binding.shoes.setImageURI(shoesUri);
                        }
                    }
                }

                if (selectedOutfit.getCoat() != null) {
                    Garment coatGarment = selectedOutfit.getCoat();
                    if (coatGarment != null) {
                        String coatPhoto = coatGarment.getPhoto();
                        if (coatPhoto != null) {
                            Uri coatUri = Uri.parse(coatPhoto);
                            binding.coat.setImageURI(coatUri);
                        }
                    }
                }
            }
        }

    }}