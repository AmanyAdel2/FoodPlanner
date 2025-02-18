package com.example.foodplanner.Network;

import com.example.foodplanner.Models.Meal;

import java.util.ArrayList;

public interface FilterCallBack {
    public void onSuccessMealByFilter(ArrayList<Meal> meals);
    public void onFailure(String error);
}
