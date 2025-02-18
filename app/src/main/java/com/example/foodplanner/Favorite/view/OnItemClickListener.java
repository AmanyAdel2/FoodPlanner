package com.example.foodplanner.Favorite.view;


import com.example.foodplanner.Models.Meal;

public interface OnItemClickListener {
    void onDeleteMealClick(Meal meal);
    void onItemClick(Meal meal);
}
