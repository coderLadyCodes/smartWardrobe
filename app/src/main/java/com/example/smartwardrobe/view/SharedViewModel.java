package com.example.smartwardrobe.view;

import androidx.lifecycle.ViewModel;

import com.example.smartwardrobe.database.Garment;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private List<Garment> garmentList;
    public List<Garment> getGarmentList() {
        return garmentList;
    }
    public void setGarmentList(List<Garment> garments) {
        garmentList = garments;
    }
}
