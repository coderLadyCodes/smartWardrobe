package com.example.smartwardrobe.view;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.smartwardrobe.database.Garment;

public class Outfit implements Parcelable {
    private Garment top;
    private Garment bottom;
    private Garment shoes;
    private Garment coat;

    public Outfit(Garment top, Garment bottom, Garment shoes, Garment coat) {
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.coat = coat;
    }

    public Outfit(Garment top, Garment bottom, Garment shoes) {
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
    }

    protected Outfit(Parcel in) {
        top = in.readParcelable(Garment.class.getClassLoader());
        bottom = in.readParcelable(Garment.class.getClassLoader());
        shoes = in.readParcelable(Garment.class.getClassLoader());
        coat = in.readParcelable(Garment.class.getClassLoader());
    }

    public static final Creator<Outfit> CREATOR = new Creator<Outfit>() {
        @Override
        public Outfit createFromParcel(Parcel in) {
            return new Outfit(in);
        }

        @Override
        public Outfit[] newArray(int size) {
            return new Outfit[size];
        }
    };

    public Outfit() {

    }

    ///////////////////////////////////// The setters are not necessary here//////////////////////////////////////
    public Garment getTop() {
        return top;
    }

    public void setTop(Garment top) {
        this.top = top;
    }

    public Garment getBottom() {
        return bottom;
    }

    public void setBottom(Garment bottom) {
        this.bottom = bottom;
    }

    public Garment getShoes() {
        return shoes;
    }

    public void setShoes(Garment shoes) {
        this.shoes = shoes;
    }

    public Garment getCoat() {
        return coat;
    }

    public void setCoat(Garment coat) {
        this.coat = coat;
    }
//////////////////////////////////////////the to string() is not necessary I think/////////////////////////////////////
    @Override
    public String toString() {
        return "Outfit{" +
                "top=" + top +
                ", bottom=" + bottom +
                ", shoes=" + shoes +
                ", coat=" + coat +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(top, i);
        parcel.writeParcelable(bottom, i);
        parcel.writeParcelable(shoes, i);
        parcel.writeParcelable(coat, i);
    }
}
