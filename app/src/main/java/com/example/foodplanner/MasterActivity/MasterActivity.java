package com.example.foodplanner.MasterActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.foodplanner.Account.view.AccountFragment;
import com.example.foodplanner.Favorite.view.FavoriteFragment;
import com.example.foodplanner.Home.View.HomeFragment;
import com.example.foodplanner.Plan.view.PlanFragment;
import com.example.foodplanner.R;
import com.example.foodplanner.Search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.muddz.styleabletoast.StyleableToast;


public class MasterActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public static final String PREFERENCE_FILE = "file";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        init();
        SharedPreferences sharedPreferences1 = getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        changeFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.home){
                changeFragment(new HomeFragment());
                return true;
            } else if (id == R.id.favorite) {
                if (sharedPreferences1.getString("email", "gust").equals("gust")){
                    StyleableToast.makeText(this, "You must log in to enjoy this feature", Toast.LENGTH_SHORT,R.style.error_toast).show();
                    return false;
                }else {
                    changeFragment(new FavoriteFragment());
                    return true;
                }
            }else if (id == R.id.search) {
                changeFragment(new SearchFragment());
                return true;
            }else if (id == R.id.plan) {
                if (sharedPreferences1.getString("email", "gust").equals("gust")){
                    StyleableToast.makeText(this, "You must log in to enjoy this feature", Toast.LENGTH_SHORT,R.style.error_toast).show();
                    return false;
                }else {
                    changeFragment(new PlanFragment());
                    return true;
                }
            }else if (id == R.id.account) {
                if (sharedPreferences1.getString("email", "gust").equals("gust")){
                    StyleableToast.makeText(this, "You must log in to enjoy this feature", Toast.LENGTH_SHORT,R.style.error_toast).show();
                    return false;
                }else {
                    changeFragment(new AccountFragment());
                    return true;
                }
            }
            return false;
        });
    }

    private void init(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void changeFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.containerFrameLayout,fragment);
        transaction.commit();
    }

}