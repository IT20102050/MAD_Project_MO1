package com.example.bowwownew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ViewHolder> {
    int lastPos = -1;
    private ArrayList<ProductRVModal> productRVModalArrayList;
    private Context context;
    private ProductClickInterface productClickInterface;

    public ProductRVAdapter(ArrayList<ProductRVModal> productRVModalArrayList, Context context, ProductClickInterface productClickInterface) {
        this.productRVModalArrayList = productRVModalArrayList;
        this.context = context;
        this.productClickInterface = productClickInterface;
    }

    @NonNull
    @Override
    public ProductRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRVAdapter.ViewHolder holder, int position) {
        ProductRVModal productRVModal = productRVModalArrayList.get(position);
        holder.productNameTV.setText(productRVModal.getProductName());
        holder.productPriceTV.setText("Rs. "+productRVModal.getProductPrice());
        Picasso.get().load(productRVModal.getProductImg()).into(holder.productIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productClickInterface.onProductClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position){
        if (position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return productRVModalArrayList.size();
    }

    public interface ProductClickInterface{
        void onProductClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productNameTV,productPriceTV;
        private ImageView productIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTV = itemView.findViewById(R.id.idTVProductName);
            productPriceTV = itemView.findViewById(R.id.idTVPrice);
            productIV = itemView.findViewById(R.id.idIVProduct);
        }
    }


    }
