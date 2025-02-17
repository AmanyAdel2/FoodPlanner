package com.example.foodplanner.Sign_LoginActivity.View.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodplanner.MasterActivity.MasterActivity;
import com.example.foodplanner.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.muddz.styleabletoast.StyleableToast;

public class SignUpFragment extends Fragment {
    TextInputEditText fullNameFiled, emailFiled, passwordFiled;
    Button signIn;
    ImageView googleSignIn;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    String emailRegex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    String passwordRegex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}";
    Pattern emailPattern , passwordPattern;
    Matcher emailMatcher, passwoedMatcher;
    public static final String PREFERENCE_FILE = "file";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        signIn.setOnClickListener(v -> {
            createNewUser();
        });
    }

    private void createNewUser() {
        String email, password, fullName;
        email = emailFiled.getText().toString();
        password = passwordFiled.getText().toString();
        fullName = fullNameFiled.getText().toString();
        emailMatcher = emailPattern.matcher(email);
        passwoedMatcher = passwordPattern.matcher(password);
        if (TextUtils.isEmpty(email)) {
            emailFiled.setError("Please enter the mail");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordFiled.setError("Please enter the password");
            return;
        }
        if (TextUtils.isEmpty(fullName)) {
            fullNameFiled.setError("Please enter the name");
            return;
        }
        if(!emailMatcher.matches()){
            emailFiled.setError("Incorrect Email");
            return;
        }
        if(!passwoedMatcher.matches()){
            passwordFiled.setError("a digit must occur at least once, a lower case letter must occur at least once, an upper case letter must occur at least once, a special character must occur at least once, no whitespace allowed in the entire string, at least 8 characters");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("email",email);
                map.put("name",fullName);
                map.put("profile","https://png.pngtree.com/png-vector/20190329/ourmid/pngtree-vector-avatar-icon-png-image_889567.jpg");
                database.getReference().child("users").child(email.replaceAll("[\\.#$\\[\\]]", "")).setValue(map);
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFERENCE_FILE,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email",email);
                editor.apply();
                StyleableToast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT,R.style.success_toast).show();
                startActivity(new Intent(getContext(), MasterActivity.class));
                getActivity().finish();
            } else {
                StyleableToast.makeText(getContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_SHORT,R.style.error_toast).show();
            }
        });
    }

    private void init(View view) {
        fullNameFiled = view.findViewById(R.id.fullName);
        emailFiled = view.findViewById(R.id.email);
        passwordFiled = view.findViewById(R.id.password);
        signIn = view.findViewById(R.id.createAccountBtn);
        googleSignIn = view.findViewById(R.id.googleImage);
        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        emailPattern = Pattern.compile(emailRegex);
        passwordPattern = Pattern.compile(passwordRegex);
    }
}