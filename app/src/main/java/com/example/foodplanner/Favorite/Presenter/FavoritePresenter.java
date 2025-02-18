package com.example.foodplanner.Favorite.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Models.Meal;

import java.util.List;

public interface FavoritePresenter {
    void getAllFavMeal();
    void deleteFromFav(Meal meal);
}
