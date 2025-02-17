package com.example.foodplanner.Network;

import com.example.foodplanner.Models.CategoryResponse;
import com.example.foodplanner.Models.CountryResponse;
import com.example.foodplanner.Models.IngredientResponse;
import com.example.foodplanner.Models.MealList;
import com.example.foodplanner.Models.MealListResponse;
import com.example.foodplanner.Models.MealResponses;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealServices {
    @GET("api/json/v1/1/random.php")
    Observable<MealResponses> getMealsByRandom();
    @GET("api/json/v1/1/categories.php")
    Observable<CategoryResponse> getAllCategories();
    @GET("api/json/v1/1/list.php?i=list")
    Observable<IngredientResponse> getAllIngredients();
    @GET("api/json/v1/1/list.php?a=list")
    Observable<CountryResponse> getAllCountries();
    @GET("api/json/v1/1/filter.php?")
    Observable<MealResponses> getAllMealsByCategory(@Query("c") String category);
    @GET("api/json/v1/1/filter.php?")
    Observable<MealResponses> getAllMealsByIngredient(@Query("i") String ingredient);
    @GET("api/json/v1/1/filter.php?")
    Observable<MealResponses> getAllMealsByArea(@Query("a") String area);
    @GET("api/json/v1/1/lookup.php?")
    Observable<MealResponses> getMealById(@Query("i") String id);

}
