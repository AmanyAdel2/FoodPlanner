package com.example.foodplanner.Plan.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Models.Meal;

import java.util.List;

public interface PlanPresenter {
    LiveData<List<Meal>> getMealByDay(String day);
    void insertToPlan(Meal meal);
    void remove(Meal meal);
}
