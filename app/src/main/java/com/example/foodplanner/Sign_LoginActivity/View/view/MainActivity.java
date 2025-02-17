package com.example.foodplanner.Sign_LoginActivity.View.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.foodplanner.Sign_LoginActivity.View.Adapter.FragmentsAdapter;
import com.example.foodplanner.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        tabLayout.setupWithViewPager(viewPager);
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new LogInFragment(), "Sign In");
        adapter.addFragment(new SignUpFragment(), "Sign UP");
        viewPager.setAdapter(adapter);

    }

    private void init(){
        imageView = findViewById(R.id.imageView);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPage);
    }
}