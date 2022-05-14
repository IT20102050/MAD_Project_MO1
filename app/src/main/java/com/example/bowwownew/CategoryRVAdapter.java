package com.example.bowwownew;

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

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private Context context;
    int lastPos = -1;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModelArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModelArrayList = categoryRVModelArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }



    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {
        CategoryRVModel categoryRVModal = categoryRVModelArrayList.get(position);
        holder.categoryNameTV.setText(categoryRVModal.getCategoryName());
        Picasso.get().load(categoryRVModal.getCategoryImage()).into(holder.categoryIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });



    }

    //categeory RV adapter
    private void setAnimation(View itemView,int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }

    }



    @Override
    public int getItemCount() {
        return categoryRVModelArrayList.size();    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryNameTV;
        private ImageView categoryIV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTV = itemView.findViewById(R.id.idTVCategoryName);
            categoryIV = itemView.findViewById(R.id.idTVCategory);
        }
    }
}
