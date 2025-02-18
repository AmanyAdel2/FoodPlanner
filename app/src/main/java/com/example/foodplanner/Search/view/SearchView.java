package com.example.foodplanner.Search.view;

import com.example.foodplanner.Models.Category;
import com.example.foodplanner.Models.Country;
import com.example.foodplanner.Models.Ingredient;

import java.util.ArrayList;

public interface SearchView {
    public void showCategories(ArrayList<Category> categories);
    public void showIngredient(ArrayList<Ingredient> ingredients);
    public void showCountries(ArrayList<Country> countries);
    public void showErrMsg(String error);
}
