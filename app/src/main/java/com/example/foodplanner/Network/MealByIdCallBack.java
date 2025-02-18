package com.example.foodplanner.Network;

import com.example.foodplanner.Models.Meal;

import java.util.ArrayList;

public interface MealByIdCallBack {
    public void onSuccessMealById(ArrayList<Meal> meals);
    public void onFailure(String error);
}
