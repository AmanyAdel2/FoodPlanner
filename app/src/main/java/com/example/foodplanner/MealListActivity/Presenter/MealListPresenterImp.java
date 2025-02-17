package com.example.foodplanner.MealListActivity.Presenter;


import android.annotation.SuppressLint;

import com.example.foodplanner.MealListActivity.view.MealListView;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.MealResponses;
import com.example.foodplanner.Network.FilterCallBack;
import com.example.foodplanner.Repository.MealRepositoryImp;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;


public class MealListPresenterImp implements MealListPresenter, FilterCallBack {

    private MealListView view;
    private MealRepositoryImp repositoryImp;
    public MealListPresenterImp(MealListView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }

    @Override
    public void onSuccessMealByFilter(ArrayList<Meal> meals) {
        view.showMeals(meals);
    }

    @Override
    public void onFailure(String error) {
        view.showErrMsg(error);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getFilteredMeals(String name, char c) {
        Observable<MealResponses> observable = repositoryImp.getFilteredMeals(name,c);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(mealResponses -> {
            view.showMeals(mealResponses.meals);
        },err-> view.showErrMsg(err.getMessage()));
    }

}
