package com.example.foodplanner.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveMeals implements Serializable {
    String email;
    ArrayList<Meal> meals;

    public SaveMeals() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }
}
