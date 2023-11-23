package com.example.the2games;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void activity2048(View view){
        Intent intent = new Intent(this, Activity2048.class);
        startActivity(intent);
    }

    public void activitySenku(View view){
        Intent intent = new Intent(this, ActivitySenku.class);
        startActivity(intent);
    }

    //On Pause buena practica pausar animaciones

    //En el tema animaciones, hay diferentes tipos, cuando un cuadrado recorra 2 posiciones
    // o 3, serán diferentes animaciones realmente

}