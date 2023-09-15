package com.example.smartwardrobe.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Garment.class}, version = 1)
public abstract class GarmentDatabase extends RoomDatabase {
    public abstract GarmentDAO garmentDAO();
    private static volatile GarmentDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static GarmentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GarmentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GarmentDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
