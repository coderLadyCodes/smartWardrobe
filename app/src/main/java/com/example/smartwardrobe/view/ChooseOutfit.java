package com.example.smartwardrobe.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.smartwardrobe.R;
import com.example.smartwardrobe.database.Garment;
import com.example.smartwardrobe.database.Warmth;
import com.example.smartwardrobe.databinding.FragmentChooseOutfitBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChooseOutfit extends Fragment {
    private SharedViewModel sharedViewModel;
    private FragmentChooseOutfitBinding binding;

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
        String selectedWarmth = binding.spinnerwarmth.getSelectedItem().toString();
        boolean selectedComfort = binding.layoutcomfortradio.getCheckedRadioButtonId() == R.id.yescomfort;
        boolean selectedFancy = binding.layoutfancyradio.getCheckedRadioButtonId() == R.id.yesfancy;
        boolean selectedLoose = binding.layoutlooseradio.getCheckedRadioButtonId() == R.id.yesloose;
        String selectedColor = binding.editcolor.getText().toString();

        Outfit generatedOutfit = generateOutfit(selectedWarmth, selectedComfort, selectedFancy, selectedLoose, selectedColor);

        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedOutfit", generatedOutfit);

        Navigation.findNavController(requireView()).navigate(R.id.action_chooseOutfit_to_showOutfit, bundle);
    }

    private Outfit generateOutfit(String warmth, boolean comfort, boolean fancy, boolean loose, String color) {
        List<Garment> garmentList = getWardrobeData();
        Outfit outfit = new Outfit();

        if (garmentList != null && !garmentList.isEmpty()) {
            List<Garment> filteredGarments = filterGarments(garmentList, warmth, comfort, fancy, loose, color);

            if (!filteredGarments.isEmpty()) {
                outfit = selectRandomOutfit(filteredGarments);
            }
        }

        return outfit;
    }

    private Outfit selectRandomOutfit(List<Garment> filteredGarments) {
        Random random = new Random();
        Outfit outfit = new Outfit();

        if (!filteredGarments.isEmpty()) {
            // Randomly select a top from the filtered garments
            int topIndex = random.nextInt(filteredGarments.size());
            outfit.setTop(filteredGarments.get(topIndex));

            filteredGarments.remove(topIndex);
        }

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
        List<Garment> filteredGarments = new ArrayList<>();
        for (Garment garment : garmentList) {
            if (garmentMatchesCriteria(garment, warmth, comfort, fancy, loose, color)) {
                filteredGarments.add(garment);
            }
        }

        return filteredGarments;
    }

    private boolean garmentMatchesCriteria(Garment garment, String warmth, boolean comfort, boolean fancy, boolean loose, String color) {
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
