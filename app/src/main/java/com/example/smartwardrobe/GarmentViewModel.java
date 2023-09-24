package com.example.smartwardrobe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smartwardrobe.database.Garment;
import com.example.smartwardrobe.repository.GarmentRepository;

import java.util.List;

public class GarmentViewModel extends AndroidViewModel{

    private final GarmentRepository garmentRepository;
    private final LiveData<List<Garment>> allGarments;
    public GarmentViewModel(@NonNull Application application) {
        super(application);
        garmentRepository = new GarmentRepository(application);
        allGarments = garmentRepository.getListGarments();
    }
    public LiveData<List<Garment>> getListGarments(){
        return allGarments;
    }
    public void addGarment(Garment garment){
        garmentRepository.addGarment(garment);
    }
}
