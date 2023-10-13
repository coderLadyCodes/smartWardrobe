package com.example.smartwardrobe.view;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.smartwardrobe.R;
import com.example.smartwardrobe.database.Garment;
import com.example.smartwardrobe.database.Warmth;
import com.example.smartwardrobe.databinding.FragmentChooseOutfitBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChooseOutfit extends Fragment {
    private String selectedWarmth;
    private boolean selectedComfort;
    private boolean selectedFancy;
    private boolean selectedLoose;
    private String selectedColor;
    private SharedViewModel sharedViewModel;
    FragmentChooseOutfitBinding binding;

    public ChooseOutfit() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseOutfitBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Replace 'garmentList' with your actual garment list or retrieve it from the ViewModel
        List<Garment> garmentList = sharedViewModel.getGarmentList();

        binding.buttongenerateoutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGenerateOutfitButtonClick();
            }
        });

        // Initialize Spinner for Warmth
        ArrayAdapter<Warmth> warmthAdapter = new ArrayAdapter<>(requireContext(), R.layout.my_spinner, Warmth.values());
        warmthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerwarmth.setAdapter(warmthAdapter);
    }

    private void onGenerateOutfitButtonClick() {
        selectedWarmth = binding.spinnerwarmth.getSelectedItem().toString();
        selectedComfort = binding.layoutcomfortradio.getCheckedRadioButtonId() == R.id.yescomfort;
        selectedFancy = binding.layoutfancyradio.getCheckedRadioButtonId() == R.id.yesfancy;
        selectedLoose = binding.layoutlooseradio.getCheckedRadioButtonId() == R.id.yesloose;
        selectedColor = binding.editcolor.getText().toString();

        Outfit generatedOutfit = generateOutfit(selectedWarmth, selectedComfort, selectedFancy, selectedLoose, selectedColor);

        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedOutfit", generatedOutfit);

        Navigation.findNavController(requireView()).navigate(R.id.action_chooseOutfit_to_showOutfit, bundle);
    }

    private Outfit generateOutfit(String warmth, boolean comfort, boolean fancy, boolean loose, String color) {
        List<Garment> garmentList = getWardrobeData(); // Implement this method to get your garment list.
        Outfit outfit = new Outfit();

        // Filter your garment list based on user choices
        List<Garment> filteredGarments = filterGarments(garmentList, warmth, comfort, fancy, loose, color);

        if (filteredGarments.size() > 0) {
            outfit = selectRandomOutfit(filteredGarments);
        }

        return outfit;
    }

    private Outfit selectRandomOutfit(List<Garment> filteredGarments) {
        Random random = new Random();
        Outfit outfit = new Outfit();

        // Randomly select a top from the filtered garments
        int topIndex = random.nextInt(filteredGarments.size());
        outfit.setTop(filteredGarments.get(topIndex));

        filteredGarments.remove(topIndex);

        if (!filteredGarments.isEmpty()) {
            // Randomly select a bottom from the remaining filtered garments
            int bottomIndex = random.nextInt(filteredGarments.size());
            outfit.setBottom(filteredGarments.get(bottomIndex));
            filteredGarments.remove(bottomIndex);
        }

        if (!filteredGarments.isEmpty()) {
            // Randomly select shoes from the remaining filtered garments
            int shoesIndex = random.nextInt(filteredGarments.size());
            outfit.setShoes(filteredGarments.get(shoesIndex));
            filteredGarments.remove(shoesIndex);
        }

        if (!filteredGarments.isEmpty()) {
            // Randomly select a coat from the remaining filtered garments
            int coatIndex = random.nextInt(filteredGarments.size());
            outfit.setCoat(filteredGarments.get(coatIndex));
        }

        return outfit;
    }

    // Implement your filtering logic based on user choices.
    private List<Garment> filterGarments(List<Garment> garmentList, String warmth, boolean comfort, boolean fancy, boolean loose, String color) {
        // Implement your filtering logic here based on user choices (warmth, comfort, fancy, loose, color).
        // This can involve iterating through the garmentList and applying filters.
        // You should return a filtered list of garments that match the user's criteria.

        // Replace this with your actual filtering logic.
        List<Garment> filteredGarments = new ArrayList<>();
        for (Garment garment : garmentList) {
            if (garmentMatchesCriteria(garment, warmth, comfort, fancy, loose, color)) {
                filteredGarments.add(garment);
            }
        }

        return filteredGarments;
    }

    // Implement a method to check if a garment matches user criteria.
    private boolean garmentMatchesCriteria(Garment garment, String warmth, boolean comfort, boolean fancy, boolean loose, String color) {
        // Implement your matching logic here, e.g., checking the warmth level, comfort, color, etc.
        // You should customize this method based on your garment model.

        // Replace this with your actual matching logic.
        return garment.getWarmth().equals(warmth)
                && garment.isComfort() == comfort
                && garment.isFancy() == fancy
                && garment.isLoose() == loose
                && garment.getColor().equals(color);
    }

    // Implement a method to retrieve your garment list from the ViewModel or data source.
    private List<Garment> getWardrobeData() {
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return sharedViewModel.getGarmentList();
    }
}
