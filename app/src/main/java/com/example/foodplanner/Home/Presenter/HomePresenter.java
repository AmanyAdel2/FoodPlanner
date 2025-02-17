package com.example.foodplanner.Home.Presenter;

import com.example.foodplanner.Models.Meal;

public interface HomePresenter {
    void getMealRandom();
    void getCategories();
    void getIngredient();
    void getCountries();
    void getRandomMeal();
    void addToFav(Meal meal);
}
