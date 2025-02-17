package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.foodplanner.Models.Meal;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal (Meal meal);
    @Delete
    void deleteMeal (Meal meal);
    @Query("DELETE FROM FavoriteMeals")
    Completable deleteTable();
    @Query("SELECT * FROM FavoriteMeals")
    Flowable<List<Meal>> getAllFavMeal();
    @Query("SELECT * FROM FavoriteMeals where day = :day")
    LiveData<List<Meal>> getMealsByDay(String day);
}
