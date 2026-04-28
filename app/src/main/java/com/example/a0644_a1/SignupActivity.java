package com.example.a0644_a1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;

    private static final String PREF_NAME = "cinefast_session_pref_v3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        etName = findViewById(R.id.ETName);
        etEmail = findViewById(R.id.ETEmail);
        etPassword = findViewById(R.id.ETPassword);
        progressBar = findViewById(R.id.progressBar);
        Button btnSignup = findViewById(R.id.btnSignup);
        TextView tvLoginLink = findViewById(R.id.TVloginLink);

        btnSignup.setOnClickListener(v -> registerUser());
        tvLoginLink.setOnClickListener(v -> finish()); // go back to Login
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return;
        }
        if (password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        // Store user data in Firebase Realtime Database
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);

                        dbRef.child("users").child(userId).setValue(userData)
                                .addOnCompleteListener(dbTask -> {
                                    // Save session
                                    SharedPreferences.Editor editor =
                                            getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
                                    editor.putBoolean("is_logged_in", true);
                                    editor.putString("user_email", email);
                                    editor.apply();

                                    Toast.makeText(SignupActivity.this,
                                            "Registration successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                });
                    } else {
                        Toast.makeText(SignupActivity.this,
                                "Signup failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}