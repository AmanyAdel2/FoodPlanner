package com.example.foodplanner.Search.Presenter;

import android.annotation.SuppressLint;

import com.example.foodplanner.Home.View.HomeMealsView;
import com.example.foodplanner.Models.Category;
import com.example.foodplanner.Models.CategoryResponse;
import com.example.foodplanner.Models.Country;
import com.example.foodplanner.Models.CountryResponse;
import com.example.foodplanner.Models.Ingredient;
import com.example.foodplanner.Models.IngredientResponse;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Network.NetworkCallBack;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.Search.view.SearchView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class SearchPresenterImp implements SearchPresenter, NetworkCallBack {
    private SearchView view;
    private MealRepositoryImp repositoryImp;

    public SearchPresenterImp(SearchView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getSearchItem() {
        Observable<CategoryResponse> getCategories = repositoryImp.getCategories();
        Observable<IngredientResponse> getIngredient = repositoryImp.getIngredient();
        Observable<CountryResponse> getCountries = repositoryImp.getCountries();

        getCategories.observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponse -> {
                    view.showCategories(categoryResponse.categories);
                }, throwable -> throwable.printStackTrace()// Handle error properly
                );

        getIngredient.observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientResponse -> {
                    view.showIngredient(ingredientResponse.meals);
                 // Handle error properly
                });

        getCountries.observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryResponse -> {
                    view.showCountries(countryResponse.meals);
                }, throwable -> throwable.printStackTrace()// Handle error properly
                );


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

    @Override
    public void onSuccessMeal(ArrayList<Meal> meals) {

    }
}
