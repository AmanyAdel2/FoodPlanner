package com.example.foodplanner.MealListActivity.view;

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
import com.example.foodplanner.MealActivity.view.MealActivity;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.MealViewHolder>{
    private ArrayList<Meal> meals = new ArrayList<>();
    private Context context;
    public MealListAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_item, parent, false);
        MealViewHolder holder = new MealViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        if(meals !=null) {
            Meal meal = meals.get(position);
            holder.mealName.setText(meal.getStrMeal());
            Glide.with(context).load(meal.getStrMealThumb())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.mealPhoto);
            holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MealActivity.class);
                intent.putExtra("id", meal.getIdMeal());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return meals == null ? 0 : meals.size();
    }
    public class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealPhoto;
        TextView mealName;
        CardView cardView;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealPhoto = itemView.findViewById(R.id.mealPhoto);
            mealName = itemView.findViewById(R.id.mealName);
            cardView = itemView.findViewById(R.id.mealCard);
        }
    }
    public void setDataSource(ArrayList<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }
}
