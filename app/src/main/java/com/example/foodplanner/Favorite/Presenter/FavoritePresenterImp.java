package com.example.foodplanner.Favorite.Presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Favorite.view.FavoriteView;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Repository.MealRepositoryImp;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;

public class FavoritePresenterImp implements FavoritePresenter{
    FavoriteView view;
    MealRepositoryImp repositoryImp;

    public FavoritePresenterImp(FavoriteView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getAllFavMeal() {
        Flowable<List<Meal>> flowable = repositoryImp.getFavMeals();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(meals -> {
            view.getData(meals);
        },err-> Log.i("TAG", "getAllFavMeal: "));
    }

    @Override
    public void deleteFromFav(Meal meal) {
        repositoryImp.delete(meal);
    }
}
