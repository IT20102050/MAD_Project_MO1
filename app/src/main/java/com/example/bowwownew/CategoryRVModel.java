package com.example.bowwownew;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryRVModel implements Parcelable {
    private String categoryName;
    private String categoryDescription;
    private String categoryDate;
    private String categoryImage;
    private String categoryID;

    public CategoryRVModel(){

    }

    protected CategoryRVModel(Parcel in) {
        categoryName = in.readString();
        categoryDescription = in.readString();
        categoryDate = in.readString();
        categoryImage = in.readString();
        categoryID = in.readString();
    }

    public static final Creator<CategoryRVModel> CREATOR = new Creator<CategoryRVModel>() {
        @Override
        public CategoryRVModel createFromParcel(Parcel in) {
            return new CategoryRVModel(in);
        }

        @Override
        public CategoryRVModel[] newArray(int size) {
            return new CategoryRVModel[size];
        }
    };

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryDate() {
        return categoryDate;
    }

    public void setCategoryDate(String categoryDate) {
        this.categoryDate = categoryDate;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public CategoryRVModel(String categoryName, String categoryDescription, String categoryDate, String categoryImage, String categoryID) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryDate = categoryDate;
        this.categoryImage = categoryImage;
        this.categoryID = categoryID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(categoryName);
        parcel.writeString(categoryDescription);
        parcel.writeString(categoryDate);
        parcel.writeString(categoryImage);
        parcel.writeString(categoryID);
    }
}
