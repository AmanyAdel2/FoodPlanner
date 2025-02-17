package com.example.foodplanner.Repository;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Models.CategoryResponse;
import com.example.foodplanner.Models.CountryResponse;
import com.example.foodplanner.Models.IngredientResponse;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.MealResponses;
import com.example.foodplanner.Network.FilterCallBack;
import com.example.foodplanner.Network.MealByIdCallBack;
import com.example.foodplanner.Network.NetworkCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface MealRepository {
    Observable<MealResponses> getMealRandom();
    Observable<CategoryResponse> getCategories();
    Observable<IngredientResponse> getIngredient();
    Observable<CountryResponse> getCountries();
    void getRandomMeal(NetworkCallBack networkCallBack);
    Observable<MealResponses> getFilteredMeals(String name, char c);
    Observable<MealResponses> getMealsById(String id);
    void insert(Meal meal);
    void delete(Meal meal);
    Completable deleteTable();
    Flowable<List<Meal>> getFavMeals();
    LiveData<List<Meal>> getPlanMeals(String day);
}
