package com.example.foodplanner.Favorite.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.Favorite.Presenter.FavoritePresenter;
import com.example.foodplanner.Favorite.Presenter.FavoritePresenterImp;
import com.example.foodplanner.MealActivity.view.MealActivity;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.db.MealLocalDataSourceImp;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteView, OnItemClickListener {
    private FavoriteAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    FavoritePresenter presenter;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favRV);
        adapter = new FavoriteAdapter(getContext(), this);
        presenter = new FavoritePresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(getContext())));
        presenter.getAllFavMeal();
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getData(List<Meal> meals) {
        adapter.setDataSource((ArrayList<Meal>) meals);
    }

    @Override
    public void removeMeal(Meal meal) {
        presenter.deleteFromFav(meal);
    }

    @Override
    public void onDeleteMealClick(Meal meal) {
        removeMeal(meal);
    }

    @Override
    public void onItemClick(Meal meal) {
        Intent intent = new Intent(getContext(), MealActivity.class);
        intent.putExtra("meal", meal);
        startActivity(intent);
    }
}