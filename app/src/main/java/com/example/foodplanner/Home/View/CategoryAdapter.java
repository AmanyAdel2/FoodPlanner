package com.example.foodplanner.Home.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.MealListActivity.view.MealListActivity;
import com.example.foodplanner.Models.Category;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    ArrayList<Category> categoryArrayList = new ArrayList<>();


    public CategoryAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_item, parent, false);
        CategoryViewHolder holder = new CategoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if(categoryArrayList !=null) {
            Category category = categoryArrayList.get(position);
            holder.categoryName.setText(category.getStrCategory());
            Glide.with(context).load(category.getStrCategoryThumb())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.categoryImage);
            holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MealListActivity.class);
                intent.putExtra("model", category);
                intent.putExtra("type","Category");
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryName;
        CardView cardView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    public void setDataSource(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
        notifyDataSetChanged();
    }
}
