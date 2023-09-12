package com.example.smartwardrobe.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface GarmentDAO {
    @Insert
    public void addGarment(Garment garment);
    @Update
    public void updateGarment(Garment garment);
    @Delete
    public void deleteGarment(Garment garment);
    @Query("select * from garment")
    public List<Garment> getAllGarment();
    @Query("select * from garment where id==:id")
    public Garment getGarment(Long id);
}
