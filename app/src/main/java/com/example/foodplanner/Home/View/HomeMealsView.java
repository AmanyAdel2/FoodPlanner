package com.example.foodplanner.Home.View;

import com.example.foodplanner.Models.Category;
import com.example.foodplanner.Models.Country;
import com.example.foodplanner.Models.Ingredient;
import com.example.foodplanner.Models.Meal;

import java.util.ArrayList;

public interface HomeMealsView {
    public void showMeals(ArrayList<Meal> meals);
    public void showCategories(ArrayList<Category> categories);
    public void showIngredient(ArrayList<Ingredient> ingredients);
    public void showCountries(ArrayList<Country> countries);
    public void showErrMsg(String error);
    void addMeal(Meal meal);
}
