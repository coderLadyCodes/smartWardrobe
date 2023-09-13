package com.example.smartwardrobe.repository;

import com.example.smartwardrobe.database.GarmentDAO;


public class GarmentRepository {
    private final GarmentDAO gramentDao;

    public GarmentRepository(GarmentDAO gramentDao) {
        this.gramentDao = gramentDao;
    }
}
