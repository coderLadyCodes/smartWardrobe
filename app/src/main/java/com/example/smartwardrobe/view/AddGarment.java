package com.example.smartwardrobe.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.Room;


import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.example.smartwardrobe.R;
import com.example.smartwardrobe.database.Categorization;
import com.example.smartwardrobe.database.Garment;
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
import java.util.Objects;


public class AddGarment extends Fragment {
    String imagePath;
    GarmentDatabase garmentDatabase;
    FragmentAddGarmentBinding binding;

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
        super.onViewCreated(view, savedInstanceState);
        binding.buttonaddgarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String photo = imagePath;
                String selectedCategory = binding.spinnercategory.getSelectedItem().toString();
                String selectedWarmth = binding.spinnerwarmth.getSelectedItem().toString();
                int selectedComfort = binding.layoutcomfortradio.getCheckedRadioButtonId();
                int selectedLoose = binding.layoutlooseradio.getCheckedRadioButtonId();
                int selectedFancy = binding.layoutfancyradio.getCheckedRadioButtonId();
                String colors = binding.editcolor.getText().toString();

                if(photo.isEmpty()){
                    Toast.makeText(getContext(), "Please Load an Image", Toast.LENGTH_SHORT).show();
                }else if((selectedComfort == -1) || (selectedLoose == -1) || (selectedFancy == -1)) {
                    Toast.makeText(getContext(), "Please make a choise", Toast.LENGTH_SHORT).show();
                } else if(colors.isEmpty()){
                    Toast.makeText(getContext(), "Please Choose a color", Toast.LENGTH_SHORT).show();
                }

                String comfort = "Is Comfortable";
                String loose = "Is Loose";;
                String fancy = "Is Fancy";;
                if(selectedComfort == R.id.yescomfort){ comfort = "Is Comfortable";}
                else if(selectedComfort == R.id.nocomfort){comfort = "Not Comfortable";}
                else if(selectedLoose == R.id.yesloose){loose = "Is Loose";}
                else if(selectedLoose == R.id.noloose){loose = "Not Loose";}
                else if(selectedFancy == R.id.yesfancy){fancy = "Is Fancy";}
                else if (selectedFancy == R.id.nofancy){fancy = "Not Fancy";}

//                GarmentDatabase db = Room.databaseBuilder(getActivity(), GarmentDatabase.class, "garment_database")
//                                .fallbackToDestructiveMigration()
//                                        .allowMainThreadQueries()
//                                                .build();
//                Garment garment = new Garment(photo, selectedCategory, selectedWarmth, comfort, loose, fancy);
//                db.garmentDAO().addGarment(garment);



                Navigation.findNavController(view).navigate(R.id.action_addGarment_to_garmentList);
            }
        });

////////////////////////////////// TAKE PHOTO WITH PHONE CAMERA AND STORE IT IN FILE DIRECTORY/////////////////////////////////

        binding.imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        /////////////////////////////////////// SPINNER CATEGORY///////////////////////////////////////////
        ArrayAdapter<Categorization> adapter = new ArrayAdapter<>(requireContext() , R.layout.my_spinner, Categorization.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnercategory.setAdapter(adapter);

        /////////////////////////////////////// SPINNER WARMTH///////////////////////////////////////////
        ArrayAdapter<Warmth> adapter1 = new ArrayAdapter<>(requireContext(), R.layout.my_spinner, Warmth.values());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerwarmth.setAdapter(adapter1);
    }



    ////////////////////////////////// TAKE PHOTO WITH PHONE CAMERA AND STORE IT IN FILE DIRECTORY/////////////////////////////////

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
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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

