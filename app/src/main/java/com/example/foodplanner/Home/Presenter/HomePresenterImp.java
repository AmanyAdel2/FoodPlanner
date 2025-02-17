package com.example.foodplanner.Home.Presenter;

import android.annotation.SuppressLint;

import com.example.foodplanner.Home.View.HomeMealsView;
import com.example.foodplanner.Models.Category;
import com.example.foodplanner.Models.CategoryResponse;
import com.example.foodplanner.Models.Country;
import com.example.foodplanner.Models.CountryResponse;
import com.example.foodplanner.Models.Ingredient;
import com.example.foodplanner.Models.IngredientResponse;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.MealResponses;
import com.example.foodplanner.Network.NetworkCallBack;
import com.example.foodplanner.Repository.MealRepositoryImp;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class HomePresenterImp implements HomePresenter, NetworkCallBack{

    private HomeMealsView view;
    private MealRepositoryImp repositoryImp;
    public HomePresenterImp(HomeMealsView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMealRandom() {
        Observable<MealResponses> getRandom = repositoryImp.getMealRandom();
        getRandom.observeOn(AndroidSchedulers.mainThread()).subscribe(mealResponses -> {
            view.showMeals(mealResponses.meals);
        },err-> view.showErrMsg(err.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCategories() {
        Observable<CategoryResponse> getCategories = repositoryImp.getCategories();
        getCategories.observeOn(AndroidSchedulers.mainThread()).subscribe(categoryResponse -> {
            view.showCategories(categoryResponse.categories);
        },err-> view.showErrMsg(err.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getIngredient() {
        Observable<IngredientResponse> getIngredient = repositoryImp.getIngredient();
        getIngredient.observeOn(AndroidSchedulers.mainThread()).subscribe(ingredientResponse -> {
            view.showIngredient(ingredientResponse.meals);
        },err-> view.showErrMsg(err.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCountries() {
        Observable<CountryResponse> getCountries = repositoryImp.getCountries();
        getCountries.observeOn(AndroidSchedulers.mainThread()).subscribe(countryResponse -> {
            view.showCountries(countryResponse.meals);
        },err-> view.showErrMsg(err.getMessage()));
    }

    @Override
    public void getRandomMeal() {
        repositoryImp.getRandomMeal(this);
    }

    @Override
    public void addToFav(Meal meal) {
        repositoryImp.insert(meal);
    }

    @Override
    public void onSuccessMeal(ArrayList<Meal> meals) {
        view.showMeals(meals);
    }

    @Override
    public void onSuccessAllCategory(ArrayList<Category> categories) {
        view.showCategories(categories);
    }

    @Override
    public void onSuccessAllIngredients(ArrayList<Ingredient> ingredients) {
        view.showIngredient(ingredients);
    }

    @Override
    public void onSuccessAllCountries(ArrayList<Country> countries) {
        view.showCountries(countries);
    }

    @Override
    public void onFailure(String error) {
        view.showErrMsg(error);
    }
}
