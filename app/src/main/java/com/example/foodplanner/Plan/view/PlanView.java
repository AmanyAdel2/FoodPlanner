package com.example.foodplanner.Plan.view;

import com.example.foodplanner.Models.Meal;

public interface PlanView {
    void getMealByDay();
    void insertMeal(Meal meal);
    void remove(Meal meal);
}
