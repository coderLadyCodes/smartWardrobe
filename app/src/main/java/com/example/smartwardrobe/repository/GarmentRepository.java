package com.example.smartwardrobe.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.smartwardrobe.database.Garment;
import com.example.smartwardrobe.database.GarmentDAO;
import com.example.smartwardrobe.database.GarmentDatabase;

import java.util.List;


public class GarmentRepository {
    private GarmentDAO garmentDao;
    private LiveData<List<Garment>> allGarments;

    public GarmentRepository(Application application) {
        GarmentDatabase db = GarmentDatabase.getDatabase(application);
        garmentDao = db.garmentDAO();
        allGarments = garmentDao.getAllGarment();
    }
    public LiveData<List<Garment>> getListGarments(){
        return allGarments;
    };

    public void addGarment (Garment garment) {
        GarmentDatabase.databaseWriteExecutor.execute(() -> {
            garmentDao.addGarment(garment);
        });
    }

    public void deleteGarment(Garment garment) {
        GarmentDatabase.databaseWriteExecutor.execute(() -> {
            garmentDao.deleteGarment(garment);
        });
    }

    public void updateGarment(Garment garment) {
        GarmentDatabase.databaseWriteExecutor.execute(() -> {
            garmentDao.updateGarment(garment);
        });
    }

}
