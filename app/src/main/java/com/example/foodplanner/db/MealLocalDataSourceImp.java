package com.example.foodplanner.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealLocalDataSourceImp implements MealLocalDataSource{
    private Context context;
    private MealDAO dao;
    private Flowable<List<Meal>> data;
     LiveData<List<Meal>> planData;
    private static MealLocalDataSourceImp dataSourceImp;

    private MealLocalDataSourceImp(Context _context){
        context = _context;
        AppDatabase dataBase = AppDatabase.getInstance(context.getApplicationContext());
        dao = dataBase.getFavMealDAO();
        data = dao.getAllFavMeal();
    }
    public static MealLocalDataSourceImp getInstance(Context context){
        if (dataSourceImp == null){
            dataSourceImp = new MealLocalDataSourceImp(context);
        }
        return dataSourceImp;
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        return data.subscribeOn(Schedulers.io());
    }

    @Override
    public LiveData<List<Meal>> getPlanMeals(String day) {
        planData = dao.getMealsByDay(day);
        return planData;
    }

    @Override
    public void insert(Meal meal) {
        new Thread() {
            @Override
            public void run() {
                dao.insertMeal(meal);
            }
        }.start();
    }

    @Override
    public void delete(Meal meal) {
        new Thread() {
            @Override
            public void run() {
                dao.deleteMeal(meal);
            }
        }.start();
    }

    @Override
    public Completable deleteTable() {
        return dao.deleteTable().subscribeOn(Schedulers.io());
    }
}
