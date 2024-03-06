package com.example.the2games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ActivityScore extends AppCompatActivity {
    private ImageButton homeButton;
    private RecyclerView mRecyclerView;
    private ArrayList<Score> mScoreData;
    private ScoreAdapter mAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        homeButton = findViewById(R.id.scoreHomeButton);
        this.homeButton.setOnClickListener(this::returnToMenu);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mRecyclerView = findViewById(R.id.scoreRecyclerView);
        mScoreData = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeData();

        mAdapter = new ScoreAdapter(this, mScoreData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initializeData() {
        mScoreData.clear();

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().endsWith("_user")) {
                String usernameKey = entry.getKey();
                String username = sharedPreferences.getString(usernameKey, "");
                int score2048 = sharedPreferences.getInt(username + "_score2048", 0);
                Score score = new Score(username, score2048);
                mScoreData.add(score);
            }
        }

        //Ordenar scores de manera descendente
        Collections.sort(mScoreData, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return Integer.compare(o2.getBestScore(), o1.getBestScore());
            }
        });
    }

    public void returnToMenu(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
