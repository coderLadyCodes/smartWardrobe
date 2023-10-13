package com.example.smartwardrobe.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartwardrobe.R;
import com.example.smartwardrobe.database.Categorization;
import com.example.smartwardrobe.database.Garment;
import com.example.smartwardrobe.databinding.FragmentShowOutfitBinding;

public class ShowOutfit extends Fragment {
    FragmentShowOutfitBinding binding;
    Outfit chosenOutfit;

    public ShowOutfit() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            chosenOutfit = getArguments().getParcelable("selectedOutfit");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShowOutfitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (chosenOutfit != null) {
            binding.top.setImageResource(getImageResource(chosenOutfit.getTop()));
            binding.bottom.setImageResource(getImageResource(chosenOutfit.getBottom()));
            binding.shoes.setImageResource(getImageResource(chosenOutfit.getShoes()));
            binding.coat.setImageResource(getImageResource(chosenOutfit.getCoat()));
        }
    }

    private int getImageResource(Garment garment) {
        if (garment != null) {
            switch (garment.getCategorization()) {
                case TOP:
                    return getTopImageResource(garment);
                case BOTTOM:
                    return getBottomImageResource(garment);
                case SHOES:
                    return getShoesImageResource(garment);
                case COAT:
                    return getCoatImageResource(garment);
                default:
                    return getTopImageResource(garment);
            }
        } else {
            return getTopImageResource(null);
        }
    }

    private int getTopImageResource(Garment garment) {
        if (garment != null) {
            // Implement your logic here to map the top garment to a specific image resource.
            // For example, you can check the garment's name or category.
            if (garment.getCategorization() == Categorization.TOP) {
                return getTopImageResource(garment); // Replace with your image resource.
            } else if (garment.getCategorization() == Categorization.BOTTOM) {
                return getTopImageResource(garment); // Replace with your image resource.
            } else if (garment.getCategorization() == Categorization.SHOES) {
                return getTopImageResource(garment); // Default image resource.
            }
        } else if (garment.getCategorization() == Categorization.COAT){

            return getTopImageResource(garment);
        } return getTopImageResource(garment);
    }



    private int getBottomImageResource(Garment garment) {
        // Implement your logic to map the bottom garment to a specific image resource.
        // Return the corresponding image resource ID.
        return getTopImageResource(garment); // Replace with your image resources.
    }

    private int getShoesImageResource(Garment garment) {
        // Implement your logic to map the shoes to a specific image resource.
        // Return the corresponding image resource ID.
        return getTopImageResource(garment);// Replace with your image resources.
    }

    private int getCoatImageResource(Garment garment) {
        // Implement your logic to map the coat to a specific image resource.
        // Return the corresponding image resource ID.
        return getTopImageResource(garment); // Replace with your image resources.
    }

}
