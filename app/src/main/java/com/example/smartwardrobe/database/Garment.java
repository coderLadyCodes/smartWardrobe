package com.example.smartwardrobe.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "garment")
public class Garment {
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "photo")
    private String photo;
    @ColumnInfo(name = "categorization")
    private Categorization categorization;
    @ColumnInfo(name = "color")
    private String color;
    @ColumnInfo(name = "loose")
    private boolean loose;
    @ColumnInfo(name = "comfort")
    private boolean comfort;
    @ColumnInfo(name = "fancy")
    private boolean fancy;
    @ColumnInfo(name = "warmth")
    private Warmth warmth;

    @Ignore
    public Garment() {
    }

    @Ignore
    public Garment(String photo, Categorization categorization, String color, boolean loose, boolean comfort, boolean fancy, Warmth warmth) {
        this.photo = photo;
        this.categorization = categorization;
        this.color = color;
        this.loose = loose;
        this.comfort = comfort;
        this.fancy = fancy;
        this.warmth = warmth;
    }

    public Garment(int id, String photo, Categorization categorization, String color, boolean loose, boolean comfort, boolean fancy, Warmth warmth) {
        this.id = id;
        this.photo = photo;
        this.categorization = categorization;
        this.color = color;
        this.loose = loose;
        this.comfort = comfort;
        this.fancy = fancy;
        this.warmth = warmth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Categorization getCategorization() {
        return categorization;
    }

    public void setCategorization(Categorization categorization) {
        this.categorization = categorization;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isLoose() {
        return loose;
    }

    public void setLoose(boolean loose) {
        this.loose = loose;
    }

    public boolean isComfort() {
        return comfort;
    }

    public void setComfort(boolean comfort) {
        this.comfort = comfort;
    }

    public boolean isFancy() {
        return fancy;
    }

    public void setFancy(boolean fancy) {
        this.fancy = fancy;
    }

    public Warmth getWarmth() {
        return warmth;
    }

    public void setWarmth(Warmth warmth) {
        this.warmth = warmth;
    }
}
