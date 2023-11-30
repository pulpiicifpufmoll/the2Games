package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.gridlayout.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class Activity2048 extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private final int percentAppear2 = 95;
    private final int NUM_FILAS = 4;
    private final int NUM_COLUMNAS = 4;
    private final Random rand = new Random(System.currentTimeMillis());
    private Resources resources;
    private GestureDetector myGestureListener;
    private String packageName;
    private GridLayout gridLayout2048;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        this.myGestureListener = new GestureDetector(this, this);
        this.resources = getResources();
        this.packageName = getPackageName();
        this.gridLayout2048 = findViewById(R.id.gridLayout2048);
        this.playButton = findViewById(R.id.newGameBtn);
        this.playButton.setOnClickListener(v -> {
            generateBoxes();
        });
    }

    public void back2048ToStartMenu(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private void generateBoxes() {
        try {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();

            String targetRow = String.valueOf(rand.nextInt(4));
            String targetColumn = String.valueOf(rand.nextInt(4));

            if (checkIfBoxExists(targetRow + targetColumn)){
                Log.d("test", "ya existe");
                return;
            }

            params.rowSpec = GridLayout.spec(Integer.parseInt(targetRow));
            params.columnSpec = GridLayout.spec(Integer.parseInt(targetColumn));
            params.width = GridLayout.LayoutParams.MATCH_PARENT;
            params.height = GridLayout.LayoutParams.MATCH_PARENT;

            Box button2 = new Box(this, "2", params, targetRow, targetColumn);
            gridLayout2048.addView(button2);

            Button backSquare = findIdBackSquareByCellPosition(targetRow, targetColumn);
            backSquare.setVisibility(View.INVISIBLE);

        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }
    }

    private boolean checkIfBoxExists(String id){
        if (findViewById(Integer.parseInt(id)) != null){
            return true;
        }
        return false;
    }

    private Button findIdBackSquareByCellPosition(String rowSpec, String columnSpec) {
        @SuppressLint("DiscouragedApi") int id = resources.getIdentifier("backSquare" + rowSpec + columnSpec, "id", packageName);
        return findViewById(id);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return myGestureListener.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Deslizamiento horizontal
            if (deltaX > 0) {
                Log.d("test","DERECHAZO");
                moverCasillas("horizontal", "derecha");

            } else {
                Log.d("test","IZQUIERDAZOO");

                // Deslizamiento hacia la izquierda

            }
        } else {
            // Deslizamiento vertical
            if (deltaY > 0) {
                Log.d("test","ABAJAZOOOO");
                // Deslizamiento hacia abajo

            } else {
                Log.d("test","ARRIBAZO");
                // Deslizamiento hacia arriba

            }
        }

        return true;
    }

    @SuppressLint("ResourceType")
    private void moverCasillas(String eje, String direccion) {
        if (eje.equals("horizontal")){
            if (direccion.equals("derecha")){
                for (int i = NUM_COLUMNAS - 1; i >= 1; i--) {
                    for (int j = 0; j <= NUM_FILAS - 1 ; j++) {
                        if (findViewById(00) != null){
                            Box x = findViewById(00);
                            x.moverDerecha();
                            Log.d("test", "EURECAAAA");
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }
}