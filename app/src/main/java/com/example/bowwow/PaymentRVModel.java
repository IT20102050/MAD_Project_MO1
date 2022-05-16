package com.example.bowwow;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentRVModel implements Parcelable {

    private String PaymentName;
    private String PaymentPhone;
    private String PaymentAddress;
    private String PaymentPlace;
    private String PaymentCard;
    private String PaymentNote;
    private String PaymentId;

    public PaymentRVModel(){

    }

    public PaymentRVModel(String paymentName, String paymentPhone, String paymentAddress, String paymentPlace, String paymentCard, String paymentNote, String paymentId) {
        PaymentName = paymentName;
        PaymentPhone = paymentPhone;
        PaymentAddress = paymentAddress;
        PaymentPlace = paymentPlace;
        PaymentCard = paymentCard;
        PaymentNote = paymentNote;
        PaymentId = paymentId;
    }

    protected PaymentRVModel(Parcel in) {
        PaymentName = in.readString();
        PaymentPhone = in.readString();
        PaymentAddress = in.readString();
        PaymentPlace = in.readString();
        PaymentCard = in.readString();
        PaymentNote = in.readString();
        PaymentId = in.readString();
    }

    public static final Creator<PaymentRVModel> CREATOR = new Creator<PaymentRVModel>() {
        @Override
        public PaymentRVModel createFromParcel(Parcel in) {
            return new PaymentRVModel(in);
        }

        @Override
        public PaymentRVModel[] newArray(int size) {
            return new PaymentRVModel[size];
        }
    };

    public String getPaymentName() {
        return PaymentName;
    }

    public void setPaymentName(String paymentName) {
        PaymentName = paymentName;
    }

    public String getPaymentPhone() {
        return PaymentPhone;
    }

    public void setPaymentPhone(String paymentPhone) {
        PaymentPhone = paymentPhone;
    }

    public String getPaymentAddress() {
        return PaymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        PaymentAddress = paymentAddress;
    }

    public String getPaymentPlace() {
        return PaymentPlace;
    }

    public void setPaymentPlace(String paymentPlace) {
        PaymentPlace = paymentPlace;
    }

    public String getPaymentCard() {
        return PaymentCard;
    }

    public void setPaymentCard(String paymentCard) {
        PaymentCard = paymentCard;
    }

    public String getPaymentNote() {
        return PaymentNote;
    }

    public void setPaymentNote(String paymentNote) {
        PaymentNote = paymentNote;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(PaymentName);
        parcel.writeString(PaymentPhone);
        parcel.writeString(PaymentAddress);
        parcel.writeString(PaymentPlace);
        parcel.writeString(PaymentCard);
        parcel.writeString(PaymentNote);
        parcel.writeString(PaymentId);
    }
}
