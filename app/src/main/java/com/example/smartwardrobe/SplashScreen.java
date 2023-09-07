package com.example.smartwardrobe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartwardrobe.databinding.FragmentSplashScreenBinding;


public class SplashScreen extends Fragment {
    FragmentSplashScreenBinding binding;

    public SplashScreen() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false);

        // SPLASH SCREEN
        new Handler().postDelayed(() -> {
            NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigate(R.id.action_splashScreen_to_welcome);

        }, 7000);
        return binding.getRoot();
    }
}