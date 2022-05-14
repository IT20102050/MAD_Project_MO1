package com.example.bowwownew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentRVAdapter extends RecyclerView.Adapter<PaymentRVAdapter.ViewHolder> {
    private ArrayList<PaymentRVModel> paymentRVModelArrayList;
    private Context context;
    int lastPos = -1;
    private PaymentClickInterface paymentClickInterface;

    public PaymentRVAdapter(ArrayList<PaymentRVModel> paymentRVModelArrayList, Context context, PaymentClickInterface paymentClickInterface) {
        this.paymentRVModelArrayList = paymentRVModelArrayList;
        this.context = context;
        this.paymentClickInterface = paymentClickInterface;
    }


    @NonNull
    @Override
    public PaymentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRVAdapter.ViewHolder holder, int position) {
        PaymentRVModel paymentRVModel = paymentRVModelArrayList.get(position);
        holder.paymentNameTV.setText(paymentRVModel.getPaymentName());
        holder.paymentCardTV.setText(paymentRVModel.getPaymentCard());
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentClickInterface.onPaymentClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return paymentRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView paymentNameTV, paymentCardTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentNameTV = itemView.findViewById(R.id.idTVPaymentName);
            paymentCardTV = itemView.findViewById(R.id.idTVCard);
        }
    }

    public interface PaymentClickInterface{
        void onPaymentClick(int position);
    }
}
