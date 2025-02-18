package com.example.foodplanner.ChooseMeal.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Models.Meal;

import java.util.List;

public interface ChoosePresenter {
    void  getAllFavMeal();
    void insertToPlan(Meal meal);
    void delete(Meal meal);
}
