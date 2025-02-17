package com.example.foodplanner.MealActivity.Presenter;

import com.example.foodplanner.Models.Meal;

public interface MealPresenter {
    public void getMealsById(String id);
    void addToFav(Meal meal);
}
