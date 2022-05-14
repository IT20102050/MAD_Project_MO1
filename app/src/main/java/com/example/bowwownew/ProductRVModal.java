package com.example.bowwownew;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductRVModal implements Parcelable {
    private String productName;
    private String productCategory;
    private String productPrice;
    private String productImg;
    private String productID;

    public ProductRVModal(){

    }

    protected ProductRVModal(Parcel in) {
        productName = in.readString();
        productCategory = in.readString();
        productPrice = in.readString();
        productImg = in.readString();
        productID = in.readString();
    }

    public static final Creator<ProductRVModal> CREATOR = new Creator<ProductRVModal>() {
        @Override
        public ProductRVModal createFromParcel(Parcel in) {
            return new ProductRVModal(in);
        }

        @Override
        public ProductRVModal[] newArray(int size) {
            return new ProductRVModal[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public ProductRVModal(String productName, String productCategory, String productPrice, String productImg, String productID) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.productID = productID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(productCategory);
        parcel.writeString(productPrice);
        parcel.writeString(productImg);
        parcel.writeString(productID);
    }
}
