package com.example.foodplanner.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.Models.Meal;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase = null;

    public abstract MealDAO getFavMealDAO();

    public synchronized static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "FavoriteMeals").build();
        }
        return appDatabase;
    }
}
