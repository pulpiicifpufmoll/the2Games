package com.example.the2games;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class Activity2048 extends AppCompatActivity {
    private final int percentAppear2 = 95;
    private final int percentAppear4 = 5;
    private GridLayout gridLayout2048;
    private final Random rand = new Random();

    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        this.playButton = findViewById(R.id.newGameBtn);
        this.playButton.setOnClickListener(v -> generateFirstSquares());
        this.gridLayout2048 = findViewById(R.id.gridLayout2048);

    }

    public void back2048ToStartMenu(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private void generateFirstSquares(){
        Log.d("2048", "PLAYYYYYYYYY");
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(rand.nextInt(4));
        params.columnSpec = GridLayout.spec(rand.nextInt(4));

        Box button2 = new Box(this, 2);
        button2.setLayoutParams(params);

        Button backSquare = findIdBackSquareByCellPosition(params.rowSpec, params.columnSpec);
        backSquare.setVisibility(View.INVISIBLE);

        gridLayout2048.addView(button2);
    }

    private Button findIdBackSquareByCellPosition(GridLayout.Spec rowSpec, GridLayout.Spec columnSpec) {
        Resources resources = getResources();
        String packageName = getPackageName();

        int id = resources.getIdentifier("backSquare" + rowSpec + columnSpec, "id", packageName);
        return findViewById(id);
    }
}