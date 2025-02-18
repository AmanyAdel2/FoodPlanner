package com.example.foodplanner.Network;

import com.example.foodplanner.Models.CategoryResponse;
import com.example.foodplanner.Models.CountryResponse;
import com.example.foodplanner.Models.IngredientResponse;
import com.example.foodplanner.Models.MealResponses;

import io.reactivex.rxjava3.core.Observable;

public interface MealRemoteDataSource {
    Observable<MealResponses> getRandomMeal();
    Observable<CategoryResponse> getAllCategories();
    Observable<IngredientResponse> getAllIngredient();
    Observable<CountryResponse> getAllCountries();
    public void makeNetworkCall(NetworkCallBack networkCallback);
    Observable<MealResponses> makeNetworkCall(String name, char c);
    Observable<MealResponses> makeNetworkCall( String id);
}
