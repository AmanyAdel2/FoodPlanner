package com.example.foodplanner.Home.View;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Home.Presenter.HomePresenter;
import com.example.foodplanner.Home.Presenter.HomePresenterImp;
import com.example.foodplanner.MealActivity.view.MealActivity;
import com.example.foodplanner.Models.Category;
import com.example.foodplanner.Models.Country;
import com.example.foodplanner.Models.Ingredient;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.db.MealLocalDataSourceImp;

import java.util.ArrayList;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;


public class HomeFragment extends Fragment implements HomeMealsView {
    ImageView dailyMealImage;
    ImageButton newMeal, favMeal;
    TextView nameOfDailyMeal;
    CardView mealCard;
    ScrollView scrollView;
    private Meal randomMeal;
    HomePresenter presenter;
    private CategoryAdapter categoryAdapter;
    private IngredientAdapter ingredientAdapter;
    private CountryAdapter countryAdapter;
    LinearLayoutManager linearLayoutManager, linearLayoutManager2, linearLayoutManager3;
    RecyclerView categoryRecyclerView, ingredientRecyclerView, countryRecyclerView;
    public static final String PREFERENCE_FILE = "file";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        presenter = new HomePresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(getContext())));
        //presenter.getRandomMeal();
        presenter.getMealRandom();
        presenter.getCategories();
        presenter.getIngredient();
        presenter.getCountries();

        newMeal.setOnClickListener(v -> {
            favMeal.setClickable(true);
            presenter.getMealRandom();
        });

        favMeal.setOnClickListener(v -> {
            if (sharedPreferences.getString("email", "gust").equals("gust")){
                StyleableToast.makeText(getContext(),"You can not add to favorite",Toast.LENGTH_SHORT,R.style.error_toast).show();
            }else {
                addMeal(randomMeal);
                favMeal.setClickable(false);
                StyleableToast.makeText(getContext(), "Add to Favorite Successfully", Toast.LENGTH_SHORT, R.style.success_toast).show();
            }
        });

        mealCard.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MealActivity.class);
            intent.putExtra("meal", randomMeal);
            intent.putExtra("type","random");
            startActivity(intent);
        });
    }

    @Override
    public void showMeals(ArrayList<Meal> meals) {
        randomMeal = meals.get(0);
        Glide.with(requireContext()).load(meals.get(0).getStrMealThumb()).placeholder(R.drawable.loading).error(R.drawable.ic_launcher_foreground).into(dailyMealImage);
        nameOfDailyMeal.setText(meals.get(0).getStrMeal());
    }

    @Override
    public void showCategories(ArrayList<Category> categories) {
        categoryAdapter.setDataSource(categories);
    }

    @Override
    public void showIngredient(ArrayList<Ingredient> ingredients) {
        ingredientAdapter.setDataSource(ingredients);
    }

    @Override
    public void showCountries(ArrayList<Country> countries) {
        countryAdapter.setDataSource(countries);
    }

    @Override
    public void showErrMsg(String error) {
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.error_layout, null);
        scrollView.removeAllViews();
        scrollView.addView(view);
    }

    @Override
    public void addMeal(Meal meal) {
        presenter.addToFav(meal);
    }

    private void init(View view){
        dailyMealImage = view.findViewById(R.id.dailyMealImage);
        nameOfDailyMeal = view.findViewById(R.id.nameOfDailyMeal);
        newMeal = view.findViewById(R.id.newMeal);
        categoryRecyclerView = view.findViewById(R.id.categoriesRV);
        ingredientRecyclerView = view.findViewById(R.id.ingredientRV);
        countryRecyclerView = view.findViewById(R.id.areaRV);
        mealCard = view.findViewById(R.id.cardView);
        favMeal = view.findViewById(R.id.fav);
        scrollView = view.findViewById(R.id.homeview);

        categoryAdapter = new CategoryAdapter(getContext());
        ingredientAdapter = new IngredientAdapter(getContext());
        countryAdapter = new CountryAdapter(getContext());

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);

        linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager2);
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        countryRecyclerView.setLayoutManager(linearLayoutManager3);
        countryRecyclerView.setAdapter(countryAdapter);
    }
}