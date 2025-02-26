package com.example.foodplanner.Plan.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.foodplanner.ChooseMeal.view.ChooseActivity;
import com.example.foodplanner.Favorite.view.OnItemClickListener;
import com.example.foodplanner.MealActivity.view.MealActivity;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.Plan.Presenter.PlanPresenter;
import com.example.foodplanner.Plan.Presenter.PlanPresenterImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.db.MealLocalDataSourceImp;

import java.util.ArrayList;
import java.util.List;


public class PlanFragment extends Fragment implements PlanView,OnPlanCardClicked {
    Button mondayBtn,tuesdayBtn,wednesdayBtn,thursdayBtn,fridayBtn,saturdayBtn,sundayBtn;
    RecyclerView mondayRV,tuesdayRV,wednesdayRV,thursdayRV,fridayRV,saturdayRV,sundayRV;
    PlanAdapter mondayAdapter,tuesdayAdapter,wednesdayAdapter,thursdayAdapter,fridayAdapter,saturdayAdapter,sundayAdapter;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3,linearLayoutManager4, linearLayoutManager5, linearLayoutManager6, linearLayoutManager7;
    PlanPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        presenter = new PlanPresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(getContext())));
        getMealByDay();

        mondayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseActivity.class);
            intent.putExtra("day","monday");
            startActivity(intent);
        });

        tuesdayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseActivity.class);
            intent.putExtra("day","tuesday");
            startActivity(intent);
        });

        wednesdayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseActivity.class);
            intent.putExtra("day","wednesday");
            startActivity(intent);
        });

        thursdayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseActivity.class);
            intent.putExtra("day","thursday");
            startActivity(intent);
        });

        fridayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseActivity.class);
            intent.putExtra("day","friday");
            startActivity(intent);
        });

        saturdayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseActivity.class);
            intent.putExtra("day","saturday");
            startActivity(intent);
        });

        sundayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChooseActivity.class);
            intent.putExtra("day","sunday");
            startActivity(intent);
        });
    }

    private void init(View view){
        mondayBtn = view.findViewById(R.id.mondayBtn);
        tuesdayBtn = view.findViewById(R.id.tuesdayBtn);
        wednesdayBtn = view.findViewById(R.id.wednesdayBtn);
        thursdayBtn = view.findViewById(R.id.thursdayBtn);
        fridayBtn = view.findViewById(R.id.fridayBtn);
        saturdayBtn = view.findViewById(R.id.saturdayBtn);
        sundayBtn = view.findViewById(R.id.sundayBtn);
        mondayRV = view.findViewById(R.id.mondayRV);
        tuesdayRV = view.findViewById(R.id.tuesdayRV);
        wednesdayRV = view.findViewById(R.id.wednesdayRV);
        thursdayRV = view.findViewById(R.id.thursdayRV);
        fridayRV = view.findViewById(R.id.fridayRV);
        saturdayRV = view.findViewById(R.id.saturdayRV);
        sundayRV = view.findViewById(R.id.sundayRV);

        mondayAdapter = new PlanAdapter(getContext(),this);
        tuesdayAdapter = new PlanAdapter(getContext(),this);
        wednesdayAdapter = new PlanAdapter(getContext(),this);
        thursdayAdapter = new PlanAdapter(getContext(),this);
        fridayAdapter = new PlanAdapter(getContext(),this);
        saturdayAdapter = new PlanAdapter(getContext(),this);
        sundayAdapter = new PlanAdapter(getContext(),this);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mondayRV.setLayoutManager(linearLayoutManager);
        linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        tuesdayRV.setLayoutManager(linearLayoutManager2);
        linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        wednesdayRV.setLayoutManager(linearLayoutManager3);
        linearLayoutManager4 = new LinearLayoutManager(getContext());
        linearLayoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        thursdayRV.setLayoutManager(linearLayoutManager4);
        linearLayoutManager5 = new LinearLayoutManager(getContext());
        linearLayoutManager5.setOrientation(RecyclerView.HORIZONTAL);
        fridayRV.setLayoutManager(linearLayoutManager5);
        linearLayoutManager6 = new LinearLayoutManager(getContext());
        linearLayoutManager6.setOrientation(RecyclerView.HORIZONTAL);
        saturdayRV.setLayoutManager(linearLayoutManager6);
        linearLayoutManager7 = new LinearLayoutManager(getContext());
        linearLayoutManager7.setOrientation(RecyclerView.HORIZONTAL);
        sundayRV.setLayoutManager(linearLayoutManager7);

        mondayRV.setAdapter(mondayAdapter);
        tuesdayRV.setAdapter(tuesdayAdapter);
        wednesdayRV.setAdapter(wednesdayAdapter);
        thursdayRV.setAdapter(thursdayAdapter);
        fridayRV.setAdapter(fridayAdapter);
        saturdayRV.setAdapter(saturdayAdapter);
        sundayRV.setAdapter(sundayAdapter);
    }

    @Override
    public void getMealByDay() {
        LiveData<List<Meal>> monday = presenter.getMealByDay("monday");
        monday.observe(getViewLifecycleOwner(),meals -> {
            mondayAdapter.setDataSource((ArrayList<Meal>) meals);
        });

        LiveData<List<Meal>> tuesday = presenter.getMealByDay("tuesday");
        tuesday.observe(getViewLifecycleOwner(),meals -> {
            tuesdayAdapter.setDataSource((ArrayList<Meal>) meals);
        });

        LiveData<List<Meal>> wednesday = presenter.getMealByDay("wednesday");
        wednesday.observe(getViewLifecycleOwner(),meals -> {
            wednesdayAdapter.setDataSource((ArrayList<Meal>) meals);
        });

        LiveData<List<Meal>> thursday = presenter.getMealByDay("thursday");
        thursday.observe(getViewLifecycleOwner(),meals -> {
            thursdayAdapter.setDataSource((ArrayList<Meal>) meals);
        });

        LiveData<List<Meal>> friday = presenter.getMealByDay("friday");
        friday.observe(getViewLifecycleOwner(),meals -> {
            fridayAdapter.setDataSource((ArrayList<Meal>) meals);
        });

        LiveData<List<Meal>> saturday = presenter.getMealByDay("saturday");
        saturday.observe(getViewLifecycleOwner(),meals -> {
            saturdayAdapter.setDataSource((ArrayList<Meal>) meals);
        });

        LiveData<List<Meal>> sunday = presenter.getMealByDay("sunday");
        sunday.observe(getViewLifecycleOwner(),meals -> {
            sundayAdapter.setDataSource((ArrayList<Meal>) meals);
        });
    }

    @Override
    public void insertMeal(Meal meal) {
        meal.setDay("");
        presenter.insertToPlan(meal);
    }

    @Override
    public void remove(Meal meal) {
        presenter.remove(meal);
    }



    @Override
    public void onDeleteDayCard(Meal meal) {
        remove(meal);
        insertMeal(meal);
    }

    @Override
    public void onClickDayCard(Meal meal) {
        Intent intent = new Intent(getContext(), MealActivity.class);
        intent.putExtra("meal", meal);
        startActivity(intent);
    }
}