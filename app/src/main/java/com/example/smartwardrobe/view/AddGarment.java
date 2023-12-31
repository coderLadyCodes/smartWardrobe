
package com.example.smartwardrobe.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smartwardrobe.GarmentViewModel;
import com.example.smartwardrobe.R;
import com.example.smartwardrobe.database.Categorization;
import com.example.smartwardrobe.database.Garment;
import com.example.smartwardrobe.database.GarmentAdapter;
import com.example.smartwardrobe.database.GarmentDAO;
import com.example.smartwardrobe.database.GarmentDatabase;
import com.example.smartwardrobe.database.Warmth;
import com.example.smartwardrobe.databinding.FragmentAddGarmentBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddGarment extends Fragment {
    String imagePath;
    private GarmentDatabase garmentDatabase;
    private GarmentDAO garmentDAO;
    private GarmentViewModel mgarmentViewModel;
    FragmentAddGarmentBinding binding;
    GarmentAdapter adapter;
    boolean isEditMode = false;
    Garment garmentToUpdate;

    public AddGarment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddGarmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mgarmentViewModel = new ViewModelProvider(this).get(GarmentViewModel.class);
        garmentDatabase = GarmentDatabase.getDatabase(requireContext());
        garmentDAO = garmentDatabase.garmentDAO();

        initSpinners();

        binding.buttonaddgarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditMode) {
                    updateGarment();
                } else {
                    addGarment();
                }
            }
        });

        // Capture photo
        binding.imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        // Check if we are in edit mode
        if (getArguments() != null) {
            garmentToUpdate = getArguments().getParcelable("garmentToUpdate");

            if (garmentToUpdate != null) {
                // Populate the form fields
                binding.editcolor.setText(garmentToUpdate.getColor());
                isEditMode = true; // Set edit mode to true
            }

            if (garmentToUpdate != null && garmentToUpdate.getPhoto() != null && !garmentToUpdate.getPhoto().isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(garmentToUpdate.getPhoto());
                if (bitmap != null) {
                    binding.displayimg.setImageBitmap(bitmap);
                    imagePath = garmentToUpdate.getPhoto();
                }

                // For the category spinner, set the selected item based on the garment's category
                String selectedCategory = garmentToUpdate.getCategorization().toString();
                for (int i = 0; i < binding.spinnercategory.getCount(); i++) {
                    if (binding.spinnercategory.getItemAtPosition(i).toString().equals(selectedCategory)) {
                        binding.spinnercategory.setSelection(i);
                        break;
                    }
                }

                // For the warmth spinner, set the selected item based on the garment's warmth
                String selectedWarmth = garmentToUpdate.getWarmth().toString();
                for (int i = 0; i < binding.spinnerwarmth.getCount(); i++) {
                    if (binding.spinnerwarmth.getItemAtPosition(i).toString().equals(selectedWarmth)) {
                        binding.spinnerwarmth.setSelection(i);
                        break;
                    }
                }

                // For radio buttons, check/uncheck based on the values from the garment
                binding.layoutcomfortradio.check(garmentToUpdate.isComfort() ? R.id.yescomfort : R.id.nocomfort);
                binding.layoutlooseradio.check(garmentToUpdate.isLoose() ? R.id.yesloose : R.id.noloose);
                binding.layoutfancyradio.check(garmentToUpdate.isFancy() ? R.id.yesfancy : R.id.nofancy);
            }
        }
    }

    private void initSpinners() {
        // Initialize Spinner for Category
        ArrayAdapter<Categorization> categoryAdapter = new ArrayAdapter<>(requireContext(), R.layout.my_spinner, Categorization.values());
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnercategory.setAdapter(categoryAdapter);

        // Initialize Spinner for Warmth
        ArrayAdapter<Warmth> warmthAdapter = new ArrayAdapter<>(requireContext(), R.layout.my_spinner, Warmth.values());
        warmthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerwarmth.setAdapter(warmthAdapter);
    }

    private void addGarment() {
        // Get data from the form
        String photo = imagePath;
        String selectedCategory = getSelectedSpinnerItem(binding.spinnercategory);
        String selectedWarmth = getSelectedSpinnerItem(binding.spinnerwarmth);
        int selectedComfort = binding.layoutcomfortradio.getCheckedRadioButtonId();
        int selectedLoose = binding.layoutlooseradio.getCheckedRadioButtonId();
        int selectedFancy = binding.layoutfancyradio.getCheckedRadioButtonId();
        String colors = binding.editcolor.getText() != null ? binding.editcolor.getText().toString() : "";

        boolean isValid = true;

        if (imagePath == null || imagePath.isEmpty()) {
            if (garmentToUpdate != null && garmentToUpdate.getPhoto() != null) {
                imagePath = garmentToUpdate.getPhoto();
            }
        }

        if (selectedComfort == -1 || selectedLoose == -1 || selectedFancy == -1) {
            isValid = false;
            Toast.makeText(getContext(), "Please make a choice", Toast.LENGTH_SHORT).show();
        }

        if (colors == null || colors.isEmpty()) {
            isValid = false;
            binding.editcolor.setError("Please Choose a color");
        }

        if (!isValid) {
            return;
        }

        String comfort = selectedComfort == R.id.yescomfort ? "Is Comfortable" : "Not Comfortable";
        String loose = selectedLoose == R.id.yesloose ? "Is Loose" : "Not Loose";
        String fancy = selectedFancy == R.id.yesfancy ? "Is Fancy" : "Not Fancy";

        Garment garment = new Garment(photo, Categorization.valueOf(selectedCategory), colors, selectedLoose == R.id.yesloose,
                selectedComfort == R.id.yescomfort, selectedFancy == R.id.yesfancy, Warmth.valueOf(selectedWarmth));

        mgarmentViewModel.addGarment(garment);

        mgarmentViewModel.getListGarments().observe(getViewLifecycleOwner(), new Observer<List<Garment>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Garment> garments) {
                if (adapter != null) {
                    adapter.setGarments(garments);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        try {
            Navigation.findNavController(requireView()).navigate(R.id.action_addGarment_to_garmentList);
        } catch (Exception e) {
            Log.e("NavigationError", "Error navigating to GarmentList fragment: " + e.getMessage());
            Toast.makeText(requireContext(), "Navigation error", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGarment() {
        String photo = imagePath;
        String selectedCategory = getSelectedSpinnerItem(binding.spinnercategory);
        String selectedWarmth = getSelectedSpinnerItem(binding.spinnerwarmth);
        int selectedComfort = binding.layoutcomfortradio.getCheckedRadioButtonId();
        int selectedLoose = binding.layoutlooseradio.getCheckedRadioButtonId();
        int selectedFancy = binding.layoutfancyradio.getCheckedRadioButtonId();
        String colors = binding.editcolor.getText() != null ? binding.editcolor.getText().toString() : "";

        boolean isValid = true;

        if (imagePath == null || imagePath.isEmpty()) {
            if (garmentToUpdate != null && garmentToUpdate.getPhoto() != null) {
                imagePath = garmentToUpdate.getPhoto();
            }
        }

        if (selectedComfort == -1 || selectedLoose == -1 || selectedFancy == -1) {
            isValid = false;
            Toast.makeText(getContext(), "Please make a choice", Toast.LENGTH_SHORT).show();
        }

        if (colors == null || colors.isEmpty()) {
            isValid = false;
            binding.editcolor.setError("Please Choose a color");
        }

        if (!isValid) {
            return;
        }

        String comfort = selectedComfort == R.id.yescomfort ? "Is Comfortable" : "Not Comfortable";
        String loose = selectedLoose == R.id.yesloose ? "Is Loose" : "Not Loose";
        String fancy = selectedFancy == R.id.yesfancy ? "Is Fancy" : "Not Fancy";

        if (garmentToUpdate != null) {
            // Update the existing garment
            garmentToUpdate.setPhoto(photo);
            garmentToUpdate.setCategorization(Categorization.valueOf(selectedCategory));
            garmentToUpdate.setWarmth(Warmth.valueOf(selectedWarmth));
            garmentToUpdate.setLoose(selectedLoose == R.id.yesloose);
            garmentToUpdate.setComfort(selectedComfort == R.id.yescomfort);
            garmentToUpdate.setFancy(selectedFancy == R.id.yesfancy);
            garmentToUpdate.setColor(colors);

            // Pass the updated garment to the ViewModel to update in the database
            mgarmentViewModel.updateGarment(garmentToUpdate);
        }

        try {
            Navigation.findNavController(requireView()).navigate(R.id.action_addGarment_to_garmentList);
        } catch (Exception e) {
            Log.e("NavigationError", "Error navigating to GarmentList fragment: " + e.getMessage());
            Toast.makeText(requireContext(), "Navigation error", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper function to get the selected item from a spinner
    private String getSelectedSpinnerItem(AdapterView<?> spinner) {
        if (spinner != null && spinner.getSelectedItem() != null) {
            return spinner.getSelectedItem().toString();
        }
        return "";
    }

    // Capture photo with phone camera and store it in a file directory
    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bundle bundle = result.getData().getExtras();
                        if (bundle != null) {
                            Bitmap bitmap = (Bitmap) bundle.get("data");
                            if (bitmap != null) {
                                OutputStream fos;
                                try {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        ContentResolver resolver = requireContext().getContentResolver();
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Images" + ".jpg");
                                        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                                        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                                        fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                                        imagePath = getRealPathFromUri(imageUri);
                                        Log.d("Image Path", imagePath);
                                        assert fos != null;
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                                        Objects.requireNonNull(fos);
                                    }
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }

                                binding.displayimg.setImageBitmap(bitmap);
                            }
                        }
                    }
                }
            });

    public String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireContext().getContentResolver().query(uri, projection, null, null, null);
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }
}

