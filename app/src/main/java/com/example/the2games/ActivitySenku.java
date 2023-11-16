package com.example.the2games;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ActivitySenku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
    }

    public void backSenkuToStartMenu(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}