package com.example.foodplanner.Network;

import android.util.Log;

import com.example.foodplanner.Models.CategoryResponse;
import com.example.foodplanner.Models.CountryResponse;
import com.example.foodplanner.Models.IngredientResponse;
import com.example.foodplanner.Models.MealList;
import com.example.foodplanner.Models.MealListResponse;
import com.example.foodplanner.Models.MealResponses;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImp implements  MealRemoteDataSource {

    private static final String url = "https://www.themealdb.com/";

    private MealServices services;
    private static MealRemoteDataSourceImp MealClient;

    public MealRemoteDataSourceImp() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(url).build();
        services = retrofit.create(MealServices.class);
    }

    public static MealRemoteDataSourceImp getInstance() {
        if (MealClient == null) {
            MealClient = new MealRemoteDataSourceImp();
        }
        return MealClient;
    }


    @Override
    public Observable<MealResponses> getRandomMeal() {
        Observable<MealResponses> getRandom = services.getMealsByRandom();
        return getRandom.subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<CategoryResponse> getAllCategories() {
        Observable<CategoryResponse> getCategories = services.getAllCategories();
        return getCategories.subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<IngredientResponse> getAllIngredient() {
        Observable<IngredientResponse> getIngredient = services.getAllIngredients();
        return getIngredient.subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<CountryResponse> getAllCountries() {
        Observable<CountryResponse> getCountries = services.getAllCountries();
        return getCountries.subscribeOn(Schedulers.io());
    }

    @Override
    public void makeNetworkCall(NetworkCallBack networkCallback) {
        /*Call<MealResponses> getRandom = services.getMealsByRandom();
        getRandom.enqueue(new Callback<MealResponses>() {
            @Override
            public void onResponse(Call<MealResponses> call, Response<MealResponses> response) {
                networkCallback.onSuccessMeal(response.body().meals);
            }

            @Override
            public void onFailure(Call<MealResponses> call, Throwable t) {
                networkCallback.onFailure(t.getMessage());
                Log.i("TAG", "OnFailure: "+t.getMessage());
            }
        });*/

        /*Call<CategoryResponse> getCategory = services.getAllCategories();
        getCategory.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                networkCallback.onSuccessAllCategory(response.body().categories);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkCallback.onFailure(t.getMessage());
                Log.i("TAG", "OnFailure: "+t.getMessage());
            }
        });*/

        /*Call<IngredientResponse> getIngredients = services.getAllIngredients();
        getIngredients.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                networkCallback.onSuccessAllIngredients(response.body().meals);
                Log.i("TAG", "onResponse: "+response.body().meals);
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                networkCallback.onFailure(t.getMessage());
                Log.i("TAG", "OnFailure: "+t.getMessage());
            }
        });*/

        /*Call<CountryResponse> getCountries = services.getAllCountries();
        getCountries.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                networkCallback.onSuccessAllCountries(response.body().meals);
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                networkCallback.onFailure(t.getMessage());
                Log.i("TAG", "OnFailure: "+t.getMessage());
            }
        });*/

    }

    @Override
    public Observable<MealResponses> makeNetworkCall( String name, char c) {
        if (c == 'c') {
            Observable<MealResponses> allMealsByCategory = services.getAllMealsByCategory(name);
            return allMealsByCategory.subscribeOn(Schedulers.io());
            /*allMealsByCategory.enqueue(new Callback<MealResponses>() {
                @Override
                public void onResponse(Call<MealResponses> call, Response<MealResponses> response) {
                    filterCallBack.onSuccessMealByFilter(response.body().meals);
                }

                @Override
                public void onFailure(Call<MealResponses> call, Throwable t) {
                    filterCallBack.onFailure(t.getMessage());
                }
            });*/
        }else if (c == 'i') {
            Observable<MealResponses> allMealsByIngredient = services.getAllMealsByIngredient(name);
            return allMealsByIngredient.subscribeOn(Schedulers.io());
            /*allMealsByIngredient.enqueue(new Callback<MealResponses>() {
                @Override
                public void onResponse(Call<MealResponses> call, Response<MealResponses> response) {
                    filterCallBack.onSuccessMealByFilter(response.body().meals);
                }

                @Override
                public void onFailure(Call<MealResponses> call, Throwable t) {
                    filterCallBack.onFailure(t.getMessage());
                }
            });*/
        }else if (c == 'a') {
            Observable<MealResponses> allMealsByArea = services.getAllMealsByArea(name);
            return allMealsByArea.subscribeOn(Schedulers.io());
            /*allMealsByArea.enqueue(new Callback<MealResponses>() {
                @Override
                public void onResponse(Call<MealResponses> call, Response<MealResponses> response) {
                    filterCallBack.onSuccessMealByFilter(response.body().meals);
                }

                @Override
                public void onFailure(Call<MealResponses> call, Throwable t) {
                    filterCallBack.onFailure(t.getMessage());
                }
            });*/
        }
        return null;
    }

    @Override
    public Observable<MealResponses> makeNetworkCall( String id) {
        Observable<MealResponses> getMeal= services.getMealById(id);
        return  getMeal.subscribeOn(Schedulers.io());
        /*getMeal.enqueue(new Callback<MealResponses>() {
            @Override
            public void onResponse(Call<MealResponses> call, Response<MealResponses> response) {
                mealByIdCallBack.onSuccessMealById(response.body().meals);
            }
            @Override
            public void onFailure(Call<MealResponses> call, Throwable t) {
                mealByIdCallBack.onFailure(t.getMessage());
                Log.i("TAG", "OnFailure: "+t.getMessage());
            }
        });*/
    }
}
