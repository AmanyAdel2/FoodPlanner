package com.example.foodplanner.Plan.Presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.ChooseMeal.view.ChooseView;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Plan.view.PlanView;
import com.example.foodplanner.Repository.MealRepositoryImp;

import java.util.List;

public class PlanPresenterImp implements PlanPresenter{
    PlanView view;
    MealRepositoryImp repositoryImp;

    public PlanPresenterImp(PlanView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }
    @Override
    public LiveData<List<Meal>> getMealByDay(String day) {
        return repositoryImp.getPlanMeals(day);
    }

    @Override
    public void insertToPlan(Meal meal) {
        repositoryImp.insert(meal);
    }

    @Override
    public void remove(Meal meal) {
        repositoryImp.delete(meal);
    }
}
