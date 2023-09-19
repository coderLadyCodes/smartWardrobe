package com.example.smartwardrobe.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
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


import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.smartwardrobe.R;
import com.example.smartwardrobe.database.GarmentDatabase;
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

    GarmentDatabase garmentDatabase;
    FragmentAddGarmentBinding binding;

    public AddGarment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //prepar the adapter ( for non graphical )
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
                Navigation.findNavController(view).navigate(R.id.action_addGarment_to_garmentList);
            }
        });
                         // TAKE IMAGE AND STORE IN IN FILE DIRECTORY
        binding.imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            openCamera();
            }
        });
    }
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
                                try{
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        ContentResolver resolver = requireContext().getContentResolver();
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Images" + ".jpg");
                                        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                                        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                                        fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
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
            }
    );
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    } }
