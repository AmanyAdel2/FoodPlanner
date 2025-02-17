package com.example.foodplanner.Account.view;

import com.example.foodplanner.Models.Meal;

import java.util.List;

public interface AccountView {
    void  getAllMeal(List<Meal> meals);
    void  deleteTable();
}
