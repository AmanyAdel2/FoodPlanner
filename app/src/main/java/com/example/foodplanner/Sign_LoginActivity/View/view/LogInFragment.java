package com.example.foodplanner.Sign_LoginActivity.View.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodplanner.ChooseMeal.Presenter.ChoosePresenterImp;
import com.example.foodplanner.MasterActivity.MasterActivity;
import com.example.foodplanner.Models.Meal;
import com.example.foodplanner.Models.SaveMeals;
import com.example.foodplanner.Network.MealRemoteDataSourceImp;
import com.example.foodplanner.R;
import com.example.foodplanner.Repository.MealRepositoryImp;
import com.example.foodplanner.Sign_LoginActivity.View.Presenter.LoginPresenter;
import com.example.foodplanner.Sign_LoginActivity.View.Presenter.LoginPresenterImp;
import com.example.foodplanner.db.MealLocalDataSourceImp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.muddz.styleabletoast.StyleableToast;


public class LogInFragment extends Fragment implements LoginView{
    TextInputEditText emailFiled, passwordFiled;
    Button login, guest;
    ImageView googleImage;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    GoogleSignInClient googleSignInClient;
    LoginPresenter presenter;
    ArrayList<Meal> saveMeals = new ArrayList<>();
    int RC_SIGN_IN = 20;

    public static final String PREFERENCE_FILE = "file";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPreferences shared = getContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
        if (!shared.getString("email", "gust").equals("gust")) {
            Intent intent = new Intent(view.getContext(), MasterActivity.class);
            startActivity(intent);
        }

        super.onViewCreated(view, savedInstanceState);
        init(view);
        checkBox();
        login.setOnClickListener(v -> {
            loginUser();
        });
        guest.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email","gust");
            editor.apply();
            Intent intent = new Intent(view.getContext(), MasterActivity.class);
            startActivity(intent);
        });
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("573389882437-ah7dc15e0kmsqrc0g62pa8v9gflb8ic9.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(),signInOptions);
        googleImage.setOnClickListener(v -> {
            googleSignIn();
        });

        googleSignInClient = GoogleSignIn.getClient(getContext(), signInOptions);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(PREFERENCE_FILE, 0);
        String email = sharedPreferences1.getString("email","");



    }

    private void loginUser() {
        String email, password;
        email = emailFiled.getText().toString();
        password = passwordFiled.getText().toString();
        if (TextUtils.isEmpty(email)) {
            StyleableToast.makeText(getContext(), "Please enter the email", Toast.LENGTH_LONG,R.style.error_toast).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            StyleableToast.makeText(getContext(), "Please enter the password", Toast.LENGTH_LONG,R.style.error_toast).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email",email);
                        editor.apply();
                        retrieveData(email);
                        StyleableToast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT,R.style.success_toast).show();
                        startActivity(new Intent(getContext(), MasterActivity.class));
                        getActivity().finish();
                    } else {
                        StyleableToast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG,R.style.error_toast).show();
                    }
                });
    }

    private void googleSignIn(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }catch (Exception e){
                Log.i("whathappen", "onActivityResult: "+e.getMessage());
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("email",user.getEmail());
                        map.put("name",user.getDisplayName());
                        map.put("profile","https://png.pngtree.com/png-vector/20190329/ourmid/pngtree-vector-avatar-icon-png-image_889567.jpg");
                        database.getReference().child("users").child(user.getEmail().replaceAll("[\\.#$\\[\\]]", "")).setValue(map);

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email",user.getEmail());
                        editor.apply();
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), MasterActivity.class));
                        getActivity().finish();
                    }else {
                        StyleableToast.makeText(getContext(),"Something wrong Try Again",Toast.LENGTH_SHORT,R.style.error_toast).show();
                    }
                });
    }

    private void checkBox() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE,Context.MODE_PRIVATE);
        String check = sharedPreferences.getString("email","gust");
        if ((!check.equals("gust"))) {
            startActivity(new Intent(getContext(), MasterActivity.class));
            getActivity().finish();
        }
    }

    private void retrieveData(String email){
        databaseReference = database.getReference("SavedMeals");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(email.replaceAll("[\\.#$\\[\\]]", ""))){

                        /*saveMeals.add(dataSnapshot.getValue(SaveMeals.class));
                        Log.i("saved", "onViewCreated: "+saveMeals.size());*/

                        for(DataSnapshot snapshot1: dataSnapshot.child("meals").getChildren()){
                            //Log.i("saved", "onDataChange: "+snapshot1.getValue());
                            saveMeals.add(snapshot1.getValue(Meal.class));
                            insertMeal(snapshot1.getValue(Meal.class));
                            Log.i("saved", "onViewCreated: "+saveMeals.size());
                        }
                        //Log.i("saved", "onDataChange: "+dataSnapshot.child("meals").getValue());
                        //Log.i("saved", "onDataChange: "+ dataSnapshot.child("meals").getChildren());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void init(View view) {
        guest = view.findViewById(R.id.guestBtn);
        emailFiled = view.findViewById(R.id.email);
        passwordFiled = view.findViewById(R.id.password);
        login = view.findViewById(R.id.loginBtn);
        googleImage = view.findViewById(R.id.googleImage);
        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        presenter = new LoginPresenterImp(this, MealRepositoryImp.getInstance(MealRemoteDataSourceImp.getInstance(),
                MealLocalDataSourceImp.getInstance(getContext())));
    }

    @Override
    public void insertMeal(Meal meal) {
        presenter.insertMeal(meal);
    }
}