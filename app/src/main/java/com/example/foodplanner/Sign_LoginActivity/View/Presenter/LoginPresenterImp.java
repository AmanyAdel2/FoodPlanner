package com.example.foodplanner.Sign_LoginActivity.View.Presenter;

import com.example.foodplanner.ChooseMeal.view.ChooseView;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.Sign_LoginActivity.View.view.LoginView;

public class LoginPresenterImp implements LoginPresenter{
    LoginView view;
    MealRepositoryImp repositoryImp;
    public LoginPresenterImp(LoginView view, MealRepositoryImp repositoryImp){
        this.view = view;
        this.repositoryImp = repositoryImp;
    }
    @Override
    public void insertMeal(Meal meal) {
        repositoryImp.insert(meal);
    }
}
