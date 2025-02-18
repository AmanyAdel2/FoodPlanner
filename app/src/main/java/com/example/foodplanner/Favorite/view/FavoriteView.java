package com.example.foodplanner.Favorite.view;

import com.example.foodplanner.Models.Meal;

import java.util.List;

public interface FavoriteView {
    void getData(List<Meal> meals);
    void removeMeal(Meal meal);
}
