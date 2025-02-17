package com.example.foodplanner.MealActivity.view;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Home.Presenter.HomePresenter;
import com.example.foodplanner.Home.Presenter.HomePresenterImp;
import com.example.foodplanner.MealActivity.Presenter.MealPresenter;
import com.example.foodplanner.MealActivity.Presenter.MealPresenterImp;
import com.example.foodplanner.MealActivity.view.IngredientItemAdapter;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;
import kr.co.prnd.readmore.ReadMoreTextView;

public class MealActivity extends AppCompatActivity implements MealView {
    ImageView mealImage;
    ImageButton calenderImage, favImage, back;
    TextView mealName, category, country;
    ReadMoreTextView instruction;
    YouTubePlayerView playerView;
    RecyclerView ingredientRV;
    IngredientItemAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    private Meal meal;
    String videoId, type, mealId;
    MealPresenter presenter;
    public static final String PREFERENCE_FILE = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        init();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        meal = (Meal) intent.getSerializableExtra("meal");
        if (meal != null) {
            Glide.with(getApplicationContext()).load(meal.getStrMealThumb()).placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground).into(mealImage);
            mealName.setText(meal.getStrMeal());
            Log.i("TAG", "onCreate: " + meal.getIdMeal());
            category.setText(meal.getStrCategory());
            country.setText(meal.getStrArea());
            adapter.setDataSource(meal.getIngredientList());
            instruction.setText(meal.getStrInstructions());
            playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    if (meal.getStrYoutube() != null && !meal.getStrYoutube().equals("")) {
                        videoId = meal.getStrYoutube().split("=")[1];
                        youTubePlayer.cueVideo(videoId, 0);
                    } else {
                        youTubePlayer.cueVideo("qdhWz7qAaCU", 0);
                    }
                }
            });
        }

        presenter = new MealPresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(this)));
        mealId = intent.getStringExtra("id");
        presenter.getMealsById(mealId);

        calenderImage.setOnClickListener(v -> {
          addToCalendar(meal);
          StyleableToast.makeText(this,"Add to Calender Successfully",Toast.LENGTH_SHORT,R.style.success_toast).show();
        });

        favImage.setOnClickListener(v -> {
            if (sharedPreferences.getString("email", "gust").equals("gust")){
                StyleableToast.makeText(this,"You can not add to favorite",Toast.LENGTH_SHORT,R.style.error_toast).show();
            }else {
                addMeal(meal);
                favImage.setClickable(false);
                StyleableToast.makeText(this, "Add to Favorite Successfully", Toast.LENGTH_SHORT, R.style.success_toast).show();
            }
        });

        back.setOnClickListener(v -> {
            finish();
        });

    }

    private void init() {
        mealImage = findViewById(R.id.mealImage);
        mealName = findViewById(R.id.mealName);
        category = findViewById(R.id.category);
        country = findViewById(R.id.country);
        instruction = findViewById(R.id.mealInstruction);
        playerView = findViewById(R.id.youtubePlayerView);
        ingredientRV = findViewById(R.id.ingredientRecyclerView);
        calenderImage = findViewById(R.id.calender);
        favImage = findViewById(R.id.favImage);
        back = findViewById(R.id.backButton);
        adapter = new IngredientItemAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ingredientRV.setLayoutManager(linearLayoutManager);
        ingredientRV.setAdapter(adapter);
    }

    @Override
    public void showMeals(ArrayList<Meal> meals) {
        meal = meals.get(0);
        Glide.with(getApplicationContext()).load(meals.get(0).getStrMealThumb()).placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher_foreground).into(mealImage);
        mealName.setText(meals.get(0).getStrMeal());
        Log.i("TAG", "onCreate: " + meals.get(0).getIdMeal());
        category.setText(meals.get(0).getStrCategory());
        country.setText(meals.get(0).getStrArea());
        adapter.setDataSource(meals.get(0).getIngredientList());
        instruction.setText(meals.get(0).getStrInstructions());
        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (meals.get(0).getStrYoutube() != null && !meals.get(0).getStrYoutube().equals("")) {
                    videoId = meals.get(0).getStrYoutube().split("=")[1];
                    youTubePlayer.cueVideo(videoId, 0);
                } else {
                    youTubePlayer.cueVideo("qdhWz7qAaCU", 0);
                }
            }
        });
    }

    @Override
    public void showErrMsg(String error) {

    }

    @Override
    public void addMeal(Meal meal) {
        presenter.addToFav(meal);
    }

    private void addToCalendar(Meal meal) {
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra(CalendarContract.Events.TITLE, meal.getStrMeal());
        i.putExtra(CalendarContract.Events.ALL_DAY, false);
        i.putExtra(CalendarContract.Events.DESCRIPTION,meal.getStrInstructions());
        startActivity(i);
    }
}
