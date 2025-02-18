package com.example.foodplanner.ChooseMeal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.foodplanner.ChooseMeal.Presenter.ChoosePresenter;
import com.example.foodplanner.ChooseMeal.Presenter.ChoosePresenterImp;
import com.example.foodplanner.Favorite.Presenter.FavoritePresenterImp;
import com.example.foodplanner.Favorite.view.FavoriteAdapter;
import com.example.foodplanner.Favorite.view.OnItemClickListener;
import com.example.foodplanner.MealListActivity.view.MealListAdapter;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.db.MealLocalDataSourceImp;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity implements ChooseView,OnDayCardClick {

    String day ;
    RecyclerView chooseRV;
    ImageButton back;
    ChoosePresenter presenter;
    ChooseAdapter adapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        init();
        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        adapter = new ChooseAdapter(this,this);
        presenter = new ChoosePresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(this)));
        presenter.getAllFavMeal();
        adapter = new ChooseAdapter(this,this);
        gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        chooseRV.setLayoutManager(gridLayoutManager);
        chooseRV.setAdapter(adapter);

        back.setOnClickListener(v -> {
            finish();
        });
    }


    @Override
    public void getPlanMeal(List<Meal> meals) {
        adapter.setDataSource((ArrayList<Meal>) meals);
    }

    @Override
    public void insertMeal(Meal meal) {
        meal.setDay(day);
        presenter.insertToPlan(meal);
    }

    @Override
    public void delete(Meal meal) {
        presenter.delete(meal);
    }


    private void init(){
        chooseRV = findViewById(R.id.chooseRV);
        back = findViewById(R.id.backButton);
    }



    @Override
    public void onAddIconClick(Meal meal) {
        delete(meal);
        insertMeal(meal);
        finish();
    }

    @Override
    public void onCardChoose(Meal meal) {
        delete(meal);
        insertMeal(meal);
        finish();
    }
}