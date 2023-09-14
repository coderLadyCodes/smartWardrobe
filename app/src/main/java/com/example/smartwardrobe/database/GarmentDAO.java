package com.example.smartwardrobe.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface GarmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addGarment(Garment garment);
    @Update
    public void updateGarment(Garment garment);
    @Delete
    public void deleteGarment(Garment garment);
    @Query("select * from garment where id==:id")
    public Garment getGarment(Long id);
    @Query("select * from garment")
    public LiveData<List<Garment>> getAllGarment();
}
