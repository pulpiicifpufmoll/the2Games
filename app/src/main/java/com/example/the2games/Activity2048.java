package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    private TextView actualScore;
    private TextView bestScore;
    private boolean isGameStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        this.myGestureListener = new GestureDetector(this, this);
        this.resources = getResources();
        this.packageName = getPackageName();
        this.gridLayout2048 = findViewById(R.id.gridLayout2048);
        this.playButton = findViewById(R.id.newGameBtn);
        this.bestScore = findViewById(R.id.best);
        this.actualScore = findViewById(R.id.score);
        this.isGameStarted = false;
        this.playButton.setOnClickListener(v -> {
            if (!isGameStarted){
                generateBoxes();
                isGameStarted = true;
            }
        });
    }

    public void back2048ToStartMenu(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private void generateBoxes() {
        try {
            String targetRow = String.valueOf(rand.nextInt(4));
            String targetColumn = String.valueOf(rand.nextInt(4));

            if (checkIfBoxExists(targetRow + targetColumn)){
                Log.d("test", "ya existe");
                return;
            }
            Box button2 = new Box(this, "2", targetRow, targetColumn);
            gridLayout2048.addView(button2);

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

    @SuppressLint("ResourceType")
    private void moveBoxes(String eje, String direccion) {
        try {
            if (eje.equals("horizontal")){
                if (direccion.equals("derecha")){
                    for (int i = NUM_COLUMNAS - 2; i >= 0; i--) {
                        for (int j = 0; j <= NUM_FILAS - 1 ; j++) {
                            String idParsed = String.valueOf(j) +  String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null){
                                Log.d("test", "DETECTED PARA MOVER: " + String.valueOf(boxToMoVE.getId()));
                                boxToMoVE.moverDerecha();
                                //updateActualScore();
                                //Log.d("test", "ACTUAL SCORE: " + getActualScore().getText().toString());
                            }
                        }
                    }
                } else if (direccion.equals("izquierda")){
                    for (int i = 1; i <= NUM_COLUMNAS - 1; i++) {
                        for (int j = 0; j <= NUM_FILAS - 1 ; j++) {
                            String idParsed = String.valueOf(j) +  String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null){
                                Log.d("test", "DETECTED PARA MOVER: " + String.valueOf(boxToMoVE.getId()));
                                boxToMoVE.moverIzquierda();
                            }
                        }
                    }
                }
            } else if (eje.equals("vertical")){
                if (direccion.equals("arriba")){
                    for (int i = 0; i <= NUM_COLUMNAS - 1; i++) {
                        for (int j = 1; j <= NUM_FILAS - 1 ; j++) {
                            String idParsed = String.valueOf(j) +  String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null){
                                Log.d("test", "DETECTED PARA MOVER: " + String.valueOf(boxToMoVE.getId()));
                                boxToMoVE.moverArriba();
                            }
                        }
                    }
                } else if (direccion.equals("abajo")){
                    for (int i = 0; i <= NUM_COLUMNAS - 1; i++) {
                        for (int j = NUM_FILAS - 2; j >= 0 ; j--) {
                            String idParsed = String.valueOf(j) +  String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null){
                                Log.d("test", "DETECTED PARA MOVER: " + String.valueOf(boxToMoVE.getId()));
                                boxToMoVE.moverAbajo();
                            }
                        }
                    }
                }
            }
            boolean isFull = isGridFull();
        } catch (Exception e){
            Log.d("test", e.getMessage());
        }

    }

    private void updateActualScore(){
        TextView actualScore = getActualScore();

        int updatedScore = Integer.parseInt(actualScore.getText().toString()) + 1;

        this.actualScore.setText(updatedScore);
    }

    private boolean isGridFull(){
        int totalBoxes = gridLayout2048.getChildCount();
        if (totalBoxes >= 32){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return myGestureListener.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        if (!isGridFull()){
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                // Deslizamiento horizontal
                if (deltaX > 0) {
                    moveBoxes("horizontal", "derecha");
                } else {
                    moveBoxes("horizontal", "izquierda");
                }
            } else {
                // Deslizamiento vertical
                if (deltaY > 0) {
                    moveBoxes("vertical", "abajo");
                } else {
                    moveBoxes("vertical", "arriba");
                }
            }
            generateBoxes();
        }
        return true;
    }

    public GridLayout getGridLayout2048() {
        return gridLayout2048;
    }

    public TextView getActualScore() {
        return actualScore;
    }

    public void setActualScore(TextView actualScore) {
        this.actualScore = actualScore;
    }

    public TextView getBestScore() {
        return bestScore;
    }

    public void setBestScore(TextView bestScore) {
        this.bestScore = bestScore;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
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