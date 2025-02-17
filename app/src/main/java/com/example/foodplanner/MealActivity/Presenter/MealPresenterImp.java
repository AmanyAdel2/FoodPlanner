package com.example.foodplanner.MealActivity.Presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodplanner.MealActivity.view.MealView;
import com.example.foodplanner.MealListActivity.view.MealListView;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.MealResponses;
import com.example.foodplanner.Network.MealByIdCallBack;
import com.example.foodplanner.Repository.MealRepositoryImp;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class MealPresenterImp implements MealPresenter, MealByIdCallBack {
    private MealView view;
    private MealRepositoryImp repositoryImp;

    public MealPresenterImp(MealView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getMealsById(String id) {
        Observable<MealResponses> observable = repositoryImp.getMealsById(id);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(mealResponses -> {
            view.showMeals(mealResponses.meals);
        },err-> Log.i("TAG", "getMealsById: "));
    }

    @Override
    public void addToFav(Meal meal) {
        repositoryImp.insert(meal);
    }

    @Override
    public void onSuccessMealById(ArrayList<Meal> meals) {
        view.showMeals(meals);
    }

    @Override
    public void onFailure(String error) {
        view.showErrMsg(error);
    }
}
