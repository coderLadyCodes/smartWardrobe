////package com.example.smartwardrobe.view;
////
////import android.os.Bundle;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////
////import androidx.annotation.NonNull;
////import androidx.annotation.Nullable;
////import androidx.fragment.app.Fragment;
////
////import com.example.smartwardrobe.R;
////import com.example.smartwardrobe.database.Categorization;
////import com.example.smartwardrobe.database.Garment;
////import com.example.smartwardrobe.databinding.FragmentShowOutfitBinding;
////
////public class ShowOutfit extends Fragment {
////    FragmentShowOutfitBinding binding;
////    Outfit chosenOutfit;
////
////    public ShowOutfit() {
////    }
////
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////
////        if (getArguments() != null) {
////            chosenOutfit = getArguments().getParcelable("selectedOutfit");
////        }
////    }
////
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        binding = FragmentShowOutfitBinding.inflate(inflater, container, false);
////        return binding.getRoot();
////    }
////
////    @Override
////    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
////        super.onViewCreated(view, savedInstanceState);
////
////        if (chosenOutfit != null) {
////            binding.top.setImageResource(getImageResource(chosenOutfit.getTop()));
////            binding.bottom.setImageResource(getImageResource(chosenOutfit.getBottom()));
////            binding.shoes.setImageResource(getImageResource(chosenOutfit.getShoes()));
////            binding.coat.setImageResource(getImageResource(chosenOutfit.getCoat()));
////        }
////    }
////
////    private int getImageResource(Garment garment) {
////        if (garment != null) {
////            switch (garment.getCategorization()) {
////                case TOP:
////                    return getTopImageResource(garment);
////                case BOTTOM:
////                    return getBottomImageResource(garment);
////                case SHOES:
////                    return getShoesImageResource(garment);
////                case COAT:
////                    return getCoatImageResource(garment);
////                default:
////                    return getTopImageResource(garment);
////            }
////        } else {
////            return getTopImageResource(null);
////        }
////    }
////
////    private int getTopImageResource(Garment garment) {
////        if (garment != null) {
////            // Implement your logic here to map the top garment to a specific image resource.
////            // For example, you can check the garment's name or category.
////            if (garment.getCategorization() == Categorization.TOP) {
////                return getTopImageResource(garment); // Replace with your image resource.
////            } else if (garment.getCategorization() == Categorization.BOTTOM) {
////                return getTopImageResource(garment); // Replace with your image resource.
////            } else if (garment.getCategorization() == Categorization.SHOES) {
////                return getTopImageResource(garment); // Default image resource.
////            }
////        } else if (garment.getCategorization() == Categorization.COAT){
////
////            return getTopImageResource(garment);
////        } return getTopImageResource(garment);
////    }
////
////
////
////    private int getBottomImageResource(Garment garment) {
////        // Implement your logic to map the bottom garment to a specific image resource.
////        // Return the corresponding image resource ID.
////        return getTopImageResource(garment); // Replace with your image resources.
////    }
////
////    private int getShoesImageResource(Garment garment) {
////        // Implement your logic to map the shoes to a specific image resource.
////        // Return the corresponding image resource ID.
////        return getTopImageResource(garment);// Replace with your image resources.
////    }
////
////    private int getCoatImageResource(Garment garment) {
////        // Implement your logic to map the coat to a specific image resource.
////        // Return the corresponding image resource ID.
////        return getTopImageResource(garment); // Replace with your image resources.
////    }
////
////}
//
//package com.example.smartwardrobe.view;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import com.example.smartwardrobe.R;
//import com.example.smartwardrobe.database.Categorization;
//import com.example.smartwardrobe.database.Garment;
//import com.example.smartwardrobe.databinding.FragmentShowOutfitBinding;
//
//public class ShowOutfit extends Fragment {
//    private FragmentShowOutfitBinding binding;
//    private Outfit chosenOutfit;
//
//    public ShowOutfit() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            chosenOutfit = getArguments().getParcelable("selectedOutfit");
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentShowOutfitBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        if (chosenOutfit != null) {
//            displayOutfit(chosenOutfit);
//        }
//    }
//
//    private void displayOutfit(Outfit outfit) {
//        // Display each garment in the outfit.
//        displayGarment(binding.top, outfit.getTop());
//        displayGarment(binding.bottom, outfit.getBottom());
//        displayGarment(binding.shoes, outfit.getShoes());
//        displayGarment(binding.coat, outfit.getCoat());
//    }
//
//    private void displayGarment(ImageView imageView, Garment garment) {
//        if (garment != null) {
//            int imageResource = getImageResource(garment);
//            imageView.setImageResource(imageResource);
//        }
//    }
//
//    private int getImageResource(Garment garment) {
//        // Implement this method to map a garment to its image resource.
//        // You can use a switch statement or a mapping table.
//        // Return the image resource ID based on the garment's information.
//        switch (garment.getCategorization()) {
//            case TOP:
//                return getTopImageResource(garment);
//            case BOTTOM:
//                return getBottomImageResource(garment);
//            case SHOES:
//                return getShoesImageResource(garment);
//            case COAT:
//                return getCoatImageResource(garment);
//            default:
//                return R.drawable.default_image; // Default image resource.
//        }
//    }
//
//    private int getTopImageResource(Garment garment) {
//        // Implement your logic here to map the top garment to a specific image resource.
//        // Replace this with your logic to determine the image resource for tops.
//        return R.drawable.default_top_image; // Default top image resource.
//    }
//
//    private int getBottomImageResource(Garment garment) {
//        // Implement your logic to map the bottom garment to a specific image resource.
//        // Replace this with your logic to determine the image resource for bottoms.
//        return R.drawable.default_bottom_image; // Default bottom image resource.
//    }
//
//    private int getShoesImageResource(Garment garment) {
//        // Implement your logic to map the shoes to a specific image resource.
//        // Replace this with your logic to determine the image resource for shoes.
//        return R.drawable.default_shoes_image; // Default shoes image resource.
//    }
//
//    private int getCoatImageResource(Garment garment) {
//        // Implement your logic to map the coat to a specific image resource.
//        // Replace this with your logic to determine the image resource for coats.
//        return R.drawable.default_coat_image; // Default coat image resource.
//    }
//}
//
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
import com.example.smartwardrobe.R;
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

        // Get the selected outfit from the arguments
        Bundle args = getArguments();
        if (args != null && args.containsKey("selectedOutfit")) {
            Outfit selectedOutfit = args.getParcelable("selectedOutfit");

            // Display outfit photos, check for null Garment objects
            if (selectedOutfit != null) {
                String topPhoto = (selectedOutfit.getTop() != null) ? selectedOutfit.getTop().getPhoto() : "";
                String bottomPhoto = (selectedOutfit.getBottom() != null) ? selectedOutfit.getBottom().getPhoto() : "";
                String shoesPhoto = (selectedOutfit.getShoes() != null) ? selectedOutfit.getShoes().getPhoto() : "";
                String coatPhoto = (selectedOutfit.getCoat() != null) ? selectedOutfit.getCoat().getPhoto() : "";

                // Display photos (you may need to load these into ImageViews)
                 binding.top.setImageURI(Uri.parse(topPhoto));
                 binding.bottom.setImageURI(Uri.parse(bottomPhoto));
                 binding.shoes.setImageURI(Uri.parse(shoesPhoto));
                 binding.coat.setImageURI(Uri.parse(coatPhoto));
            }
        }
    }
}

