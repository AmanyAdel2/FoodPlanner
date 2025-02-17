package com.example.foodplanner.MealListActivity.view;

import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.MealList;

import java.util.ArrayList;

public interface MealListView {
    public void showMeals(ArrayList<Meal> meals);
    public void showErrMsg(String error);
}
