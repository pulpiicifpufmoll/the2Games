package com.example.the2games;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    private ImageButton logOutButton;
    private Button activity2048;
    private Button activitySenku;
    private Button activityScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        logOutButton = findViewById(R.id.logOutButton);
        activity2048 = findViewById(R.id.btn2048);
        activitySenku = findViewById(R.id.btnSenku);
        activityScore = findViewById(R.id.btnScores);
        addButtonListeners();
    }

    private void addButtonListeners(){
        this.logOutButton.setOnClickListener(this::logOut);
        this.activity2048.setOnClickListener(this::activity2048);
        this.activitySenku.setOnClickListener(this::activitySenku);
        this.activityScore.setOnClickListener(this::activityScore);
    }

    public void logOut(View view){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

    public void activity2048(View view){
        Intent intent = new Intent(this, Activity2048.class);
        startActivity(intent);
    }

    public void activitySenku(View view){
        Intent intent = new Intent(this, ActivitySenku.class);
        startActivity(intent);
    }

    public void activityScore(View view){
        Intent intent = new Intent(this, ActivityScore.class);
        startActivity(intent);
    }

}