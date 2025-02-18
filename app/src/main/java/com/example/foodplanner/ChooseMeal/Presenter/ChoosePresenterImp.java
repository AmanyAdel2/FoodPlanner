package com.example.foodplanner.ChooseMeal.Presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.ChooseMeal.view.ChooseView;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Repository.MealRepositoryImp;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;

public class ChoosePresenterImp implements ChoosePresenter {
    ChooseView view;
    MealRepositoryImp repositoryImp;

    public ChoosePresenterImp(ChooseView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }
    @SuppressLint("CheckResult")
    @Override
    public void  getAllFavMeal() {
        Flowable<List<Meal>> flowable = repositoryImp.getFavMeals();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(meals -> {
            view.getPlanMeal(meals);
        },err-> Log.i("TAG", "getAllFavMeal: "));
    }

    @Override
    public void insertToPlan(Meal meal) {
        repositoryImp.insert(meal);
    }

    @Override
    public void delete(Meal meal) {
        repositoryImp.delete(meal);
    }
}
