package com.example.foodplanner.MealActivity.view;

import com.example.foodplanner.Models.Meal;

import java.util.ArrayList;

public interface MealView {
    public void showMeals(ArrayList<Meal> meals);
    public void showErrMsg(String error);
    void addMeal(Meal meal);
}
