package com.example.foodplanner.Models;

public class IngredientMeasure {
    String strIngredient;
    String strMeasure;

    public IngredientMeasure() {
    }

    public IngredientMeasure(String strIngredient, String strMeasure) {
        this.strIngredient = strIngredient;
        this.strMeasure = strMeasure;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrMeasure() {
        return strMeasure;
    }

    public void setStrMeasure(String strMeasure) {
        this.strMeasure = strMeasure;
    }
}
