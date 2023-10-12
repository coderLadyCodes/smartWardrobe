package com.example.smartwardrobe.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "garment")
public class Garment implements Parcelable {
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "photo")
    private String photo;
    @ColumnInfo(name = "categorization")
    public Categorization categorization;
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

    protected Garment(Parcel in) {
        categorization = Categorization.valueOf(in.readString());
        warmth = Warmth.valueOf(in.readString());
        id = in.readInt();
        photo = in.readString();
        color = in.readString();
        loose = in.readByte() != 0;
        comfort = in.readByte() != 0;
        fancy = in.readByte() != 0;
    }

    public static final Creator<Garment> CREATOR = new Creator<Garment>() {
        @Override
        public Garment createFromParcel(Parcel in) {
            return new Garment(in);
        }

        @Override
        public Garment[] newArray(int size) {
            return new Garment[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(categorization.name());
        parcel.writeString(warmth.name());
        parcel.writeString(photo);
        parcel.writeString(color);
        parcel.writeByte((byte) (loose ? 1 : 0));
        parcel.writeByte((byte) (comfort ? 1 : 0));
        parcel.writeByte((byte) (fancy ? 1 : 0));
    }
}
