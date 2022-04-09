package com.example.videosta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    EditText emailBox, passwordBox, nameBox;
    Button loginBtn, signupBtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailBox = findViewById(R.id.upemailBox);
        passwordBox = findViewById(R.id.uppasswordBox);
        nameBox = findViewById(R.id.upnameBox);
        loginBtn = findViewById(R.id.uploginbutton);
        signupBtn = findViewById(R.id.upcreateaccbutton);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });

        signupBtn.setOnClickListener(v -> {
            String email, password, name;
            email = emailBox.getText().toString();
            password = passwordBox.getText().toString();
            name = nameBox.getText().toString();

            User user = new User();
            user.setName(name); user.setEmail(email); user.setPassword(password);

            if(email.equals("") || password.equals("") || name.equals("")){
                Toast.makeText(SignupActivity.this,
                        "Please Enter All Details !", Toast.LENGTH_LONG).show();
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            // success
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(unused -> {
                                Toast.makeText(SignupActivity.this,
                                        "Account created !", Toast.LENGTH_SHORT).show();
                                    finish();
                                    });

                        } else {
                            // error
                            Toast.makeText(SignupActivity.this,
                                    task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}