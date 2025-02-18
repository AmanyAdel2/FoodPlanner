package com.example.foodplanner.ChooseMeal.view;

import com.example.foodplanner.Models.Meal;

import java.util.List;

public interface ChooseView {
    void getPlanMeal(List<Meal> meals);
    void insertMeal(Meal meal);
    void delete(Meal meal);
}
