package com.example.gymfitness.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymfitness.R;
import com.example.gymfitness.activities.intro.OnBroading_2a;
import com.example.gymfitness.activities.setup.SetUpStartActivity;
import com.example.gymfitness.data.database.FitnessDB;
import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;

    private void addControls() {
        sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch);
        addControls();
        // create database
        new Thread(new Runnable() {
            @Override
            public void run() {
                FitnessDB.getInstance(getApplicationContext()).workoutDAO().getAllWorkouts();
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int onboarded = sharedPreferences.getInt("status", 0);
                    if (onboarded == 1) {
                        Intent intent = new Intent(getApplicationContext(), SetUpStartActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("status", 1);
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), OnBroading_2a.class);
                        startActivity(intent);
                        finish();
                    }
            }
        }, 2000);

    }
}
