package com.example.foodplanner.Search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.foodplanner.MealListActivity.view.MealListActivity;
import com.example.foodplanner.Models.Category;
import com.example.foodplanner.Models.Country;
import com.example.foodplanner.Models.Ingredient;
import com.example.foodplanner.Models.Search;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.Search.Presenter.SearchPresenter;
import com.example.foodplanner.Search.Presenter.SearchPresenterImp;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements SearchView, OnItemSearchClick {
    char flag = 'c';
    String[] area;
    SearchPresenter presenter;
    ArrayList<Search> categoryList = new ArrayList<>();
    ArrayList<Search> ingredientList = new ArrayList<>();
    ArrayList<Search> countryList = new ArrayList<>();
    androidx.appcompat.widget.SearchView search;
    RecyclerView recyclerView;
    ChipGroup chipGroup;
    Chip categoryChip, ingerdientChip, countryChip;
    LinearLayout linearLayout;
    SearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        presenter = new SearchPresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(getContext())));
        presenter.getSearchItem();

        categoryChip.setOnClickListener(v -> {
            flag = 'c';
            adapter.setDataSource(categoryList);
            adapter.notifyDataSetChanged();
        });
        ingerdientChip.setOnClickListener(v -> {
            flag = 'i';
            ingerdientChip.setFilterTouchesWhenObscured(false);
            adapter.setDataSource(ingredientList);
            adapter.notifyDataSetChanged();
        });
        countryChip.setOnClickListener(v -> {
            flag = 'a';
            adapter.setDataSource(countryList);
            adapter.notifyDataSetChanged();
        });

        Observable.create((ObservableOnSubscribe<String>) emitter -> search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        emitter.onNext(newText);
                        return false;
                    }
                })).debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchTerm -> {
                    ArrayList<Search> filtered = filter(searchTerm);
                    adapter.setDataSource(filtered);
                });

    }

    @Override
    public void showCategories(ArrayList<Category> categories) {
        categories.forEach(category -> {
            categoryList.add(new Search(category.getStrCategory(), category.getStrCategoryThumb()));
        });
        adapter.setDataSource(categoryList);
    }

    @Override
    public void showIngredient(ArrayList<Ingredient> ingredients) {
        ingredients.forEach(ingredient -> {
            ingredientList.add(new Search(ingredient.getStrIngredient(), "https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png"));
        });
    }

    @Override
    public void showCountries(ArrayList<Country> countries) {
        for (int i = 0; i < countries.size(); i++) {
            countryList.add(new Search(countries.get(i).getStrArea(), "https://flagsapi.com/" + area[i] + "/shiny/64.png"));
        }
    }

    @Override
    public void showErrMsg(String error) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.error_layout, null);
        linearLayout.removeAllViews();
        linearLayout.addView(view);
    }

    private void init(View view) {
        search = view.findViewById(R.id.search);
        search.clearFocus();
        chipGroup = view.findViewById(R.id.chipGroup);
        categoryChip = view.findViewById(R.id.categoryChip);
        ingerdientChip = view.findViewById(R.id.ingredientChip);
        countryChip = view.findViewById(R.id.countryChip);
        recyclerView = view.findViewById(R.id.searchRv);
        area = view.getResources().getStringArray(R.array.area);
        linearLayout = view.findViewById(R.id.linear_search);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layout);
        adapter = new SearchAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Search> filter(String searchTerm) {
        ArrayList<Search> filtered = new ArrayList<>();
        if (flag == 'c'){
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getSearchName().toLowerCase(Locale.ROOT).startsWith(searchTerm) || categoryList.get(i).getSearchName().toUpperCase(Locale.ROOT).startsWith(searchTerm)) {
                    filtered.add(categoryList.get(i));
                }
            }
        }
        if (flag == 'i'){
            for (int i = 0; i < ingredientList.size(); i++) {
                if (ingredientList.get(i).getSearchName().toLowerCase(Locale.ROOT).startsWith(searchTerm) || ingredientList.get(i).getSearchName().toUpperCase(Locale.ROOT).startsWith(searchTerm)) {
                    filtered.add(ingredientList.get(i));
                }
            }
        }
        if (flag == 'a'){
            for (int i = 0; i < countryList.size(); i++) {
                if (countryList.get(i).getSearchName().toLowerCase(Locale.ROOT).startsWith(searchTerm) || countryList.get(i).getSearchName().toUpperCase(Locale.ROOT).startsWith(searchTerm)) {
                    filtered.add(countryList.get(i));
                }
            }
        }
        return filtered;
    }

    @Override
    public void onItemClick(Search search) {
        Intent intent = new Intent(getContext(), MealListActivity.class);
        intent.putExtra("model", search);
        intent.putExtra("type","Search");
        intent.putExtra("flag",flag);
        startActivity(intent);
    }
}