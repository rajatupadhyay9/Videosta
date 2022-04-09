package com.example.videosta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {
    EditText codeBox;
    Button joinButton, shareButton, logoutButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        codeBox = findViewById(R.id.codebox);
        joinButton = findViewById(R.id.joinbutton);
        shareButton = findViewById(R.id.sharebutton);
        logoutButton = findViewById(R.id.logoutbtn);
        firebaseAuth = FirebaseAuth.getInstance();

        try {
            URL serverurl = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverurl)
                    .setWelcomePageEnabled(false)
                    .build();

            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        joinButton.setOnClickListener(v -> {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(codeBox.getText().toString())
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeetActivity.launch(DashboardActivity.this, options);
        });

        logoutButton.setOnClickListener(v -> {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        });

        shareButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Join my meeting using this code : \n"
                + codeBox.getText().toString() + "\n on videosta app :)");
            startActivity(intent);
        });
    }
}