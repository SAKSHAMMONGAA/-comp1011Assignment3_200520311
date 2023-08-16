package com.car.manager.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Vehicle implements Parcelable {
    private String make;
    private String model;
    private String condition;
    private String engineCylinders;
    private int year;
    private int numberOfDoors;
    private double price;
    private String color;
    private String thumbnailImage;
    private String fullSizeImage;
    private String dateSold;

    public Vehicle(String make, String model, String condition, String engineCylinders, int year, int numberOfDoors, double price, String color, String thumbnailImage, String fullSizeImage, String dateSold) {
        this.make = make;
        this.model = model;
        this.condition = condition;
        this.engineCylinders = engineCylinders;
        this.year = year;
        this.numberOfDoors = numberOfDoors;
        this.price = price;
        this.color = color;
        this.thumbnailImage = thumbnailImage;
        this.fullSizeImage = fullSizeImage;
        this.dateSold = dateSold;
    }

    public Vehicle() {

    }

    protected Vehicle(Parcel in) {
        make = in.readString();
        model = in.readString();
        condition = in.readString();
        engineCylinders = in.readString();
        year = in.readInt();
        numberOfDoors = in.readInt();
        price = in.readDouble();
        color = in.readString();
        thumbnailImage = in.readString();
        fullSizeImage = in.readString();
        dateSold = in.readString();
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getEngineCylinders() {
        return engineCylinders;
    }

    public void setEngineCylinders(String engineCylinders) {
        this.engineCylinders = engineCylinders;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getFullSizeImage() {
        return fullSizeImage;
    }

    public void setFullSizeImage(String fullSizeImage) {
        this.fullSizeImage = fullSizeImage;
    }

    public String getDateSold() {
        return dateSold;
    }

    public void setDateSold(String dateSold) {
        this.dateSold = dateSold;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", condition='" + condition + '\'' +
                ", engineCylinders='" + engineCylinders + '\'' +
                ", year=" + year +
                ", numberOfDoors=" + numberOfDoors +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", thumbnailImage='" + thumbnailImage + '\'' +
                ", fullSizeImage='" + fullSizeImage + '\'' +
                ", dateSold='" + dateSold + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(make);
        parcel.writeString(model);
        parcel.writeString(condition);
        parcel.writeString(engineCylinders);
        parcel.writeInt(year);
        parcel.writeInt(numberOfDoors);
        parcel.writeDouble(price);
        parcel.writeString(color);
        parcel.writeString(thumbnailImage);
        parcel.writeString(fullSizeImage);
        parcel.writeString(dateSold);
    }
}
