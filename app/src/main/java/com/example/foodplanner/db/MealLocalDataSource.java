package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealLocalDataSource {
    Flowable<List<Meal>> getFavMeals();
    LiveData<List<Meal>> getPlanMeals(String day);
    void insert(Meal meal);
    void delete(Meal meal);
    Completable deleteTable();
}
