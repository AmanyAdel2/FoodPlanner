package com.example.foodplanner.Account.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Account.Presenter.AccountPresenter;
import com.example.foodplanner.Account.Presenter.AccountPresenterImp;
import com.example.foodplanner.MasterActivity.MasterActivity;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.SaveMeals;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.Sign_LoginActivity.View.view.MainActivity;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment implements AccountView {
    private TextView username;
    private TextInputEditText emailField;
    private ImageView userProfile;
    private Button button;
    private Button backUpbtn;
    private ConstraintLayout layout;
    private AccountPresenter presenter;

    private static final String PREFERENCE_FILE = "file";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SaveMeals meal = new SaveMeals();
    private ValueEventListener userListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        presenter = new AccountPresenterImp(this, MealRepositoryImp.getInstance(
                MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(requireContext())
        ));
        presenter.getAllFavMeal();

        String email = getStoredEmail();
        getUserDetails(email);


        // Logout Button
        button.setOnClickListener(v -> {
            // Ensure this method retrieves the current user's email
           // if (!email.equals("guest")) {
                // Clear user session
             //   clearUserSession();

                // Navigate to LogInFragment using NavController
               // NavController navController = Navigation.findNavController(requireView());
                //navController.navigate(R.id.logInFragment2);

                // Optional: Clear back stack to prevent navigating back to AccountFragment
                //navController.popBackStack(R.id.logInFragment2, false);
            //} else {
            //    Toast.makeText(getContext(), "No user is currently logged in.", Toast.LENGTH_SHORT).show();
            //}
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email","gust");
            editor.apply();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
        // Backup Button
        backUpbtn.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
            String email1 = sharedPreferences.getString("email", "guest");

            if (email != null && !email.equals("guest")) {
                presenter.getAllFavMeal();
                Toast.makeText(getContext(), "Backup started...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Guest users cannot back up data.", Toast.LENGTH_SHORT).show();
            }
        });



        // Check network availability
        if (!isNetworkAvailable()) {
            LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View errorView = inflater.inflate(R.layout.error_layout, null);
            layout.removeAllViews();
            layout.addView(errorView);
        }
    }

    @Override
    public void getAllMeal(List<Meal> meals) {
        String email = getStoredEmail();
        meal.setEmail(email);
        meal.setMeals(new ArrayList<>(meals));
    }

    @Override
    public void deleteTable() {
        Log.i("delete", "deleteTable: Done");
    }

    private void init(View view) {
        button = view.findViewById(R.id.logoutBtn);
        backUpbtn = view.findViewById(R.id.backUpBtn);
        database = FirebaseDatabase.getInstance();
        username = view.findViewById(R.id.username);
        emailField = view.findViewById(R.id.useremail);
        userProfile = view.findViewById(R.id.profile_image);
        layout = view.findViewById(R.id.accountview);
    }

    private void getUserDetails(String email) {
        databaseReference = database.getReference("users");
        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(sanitizeEmail(email))) {
                        emailField.setText(dataSnapshot.child("email").getValue(String.class));
                        username.setText(dataSnapshot.child("name").getValue(String.class));
                        String profileUrl = dataSnapshot.child("profile").getValue(String.class);

                        if (profileUrl != null && isAdded()) {
                            Glide.with(requireActivity())
                                    .load(profileUrl)
                                    .placeholder(R.drawable.loading)
                                    .error(R.drawable.ic_launcher_foreground)
                                    .into(userProfile);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.addValueEventListener(userListener);
    }

    private String sanitizeEmail(String email) {
        return email.replaceAll("[\\.#$\\[\\]]", "");
    }

    private String getStoredEmail() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "guest");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void clearUserSession() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", "guest");
        editor.apply();
        presenter.deleteTable();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (databaseReference != null && userListener != null) {
            databaseReference.removeEventListener(userListener);
        }
    }
}