package com.example.smartwardrobe.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Garment.class}, version = 1)
public abstract class GarmentDatabase extends RoomDatabase {
    public abstract GarmentDAO garmentDAO();

}
