package com.example.videosta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailBox, passwordBox;
    Button loginBtn, signupBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);
        loginBtn = findViewById(R.id.loginbutton);
        signupBtn = findViewById(R.id.createaccbutton);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }

        loginBtn.setOnClickListener(v -> {
            String email, password;
            email = emailBox.getText().toString();
            password = passwordBox.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,
                                    "Logged In !", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        signupBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }
}