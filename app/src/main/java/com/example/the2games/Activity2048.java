package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.gridlayout.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class Activity2048 extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private final int NUM_FILAS = 4;
    private final int NUM_COLUMNAS = 4;
    private final Random rand = new Random(System.currentTimeMillis());
    private GestureDetector myGestureListener;
    private GridLayout gridLayout2048;
    private Button playBtn;
    private Button backBtn;
    private TextView actualScore;
    private TextView bestScore;
    private ActivityTimer timer;
    private TextView timerView;
    private boolean isGameStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        this.myGestureListener = new GestureDetector(this, this);
        this.gridLayout2048 = findViewById(R.id.gridLayout2048);
        this.playBtn = findViewById(R.id.newGameBtn);
        this.backBtn = findViewById(R.id.backBtn);
        this.bestScore = findViewById(R.id.best);
        this.timerView = findViewById(R.id.timerView);
        this.actualScore = findViewById(R.id.score);
        this.isGameStarted = false;
        addListenersToButtons();
    }

    private void addListenersToButtons() {
        this.playBtn.setOnClickListener(this::startGame);
        this.backBtn.setOnClickListener(this::back2048ToStartMenu);
    }

    public void startGame(View view){
        if (!isGameStarted){
            generateBoxes();
            isGameStarted = true;
            loadTimer();
        }
    }

    public void back2048ToStartMenu(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private void loadTimer(){
        this.timer = new ActivityTimer();
        Thread timerThread = new Thread(this.timer);
        timerThread.start();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String timeToShow = timer.renturnTimeRemainingFormated();
                timerView.setText("Time: "+ timeToShow);
                handler.postDelayed(this, 1000); // Actualizar cada segundo
            }
        });
    }

    private void generateBoxes() {
        try {
            String targetRow = String.valueOf(rand.nextInt(NUM_FILAS));
            String targetColumn = String.valueOf(rand.nextInt(NUM_COLUMNAS));

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
                                boxToMoVE.moverDerecha();
                            }
                        }
                    }
                } else if (direccion.equals("izquierda")){
                    for (int i = 1; i <= NUM_COLUMNAS - 1; i++) {
                        for (int j = 0; j <= NUM_FILAS - 1 ; j++) {
                            String idParsed = String.valueOf(j) +  String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null){
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
                                boxToMoVE.moverAbajo();
                            }
                        }
                    }
                }
            }
            //TODO FIN DEL JUEGO SI DEVUELVE TRUE
            boolean isFull = isGridFull();
        } catch (Exception e){
            Log.d("test", e.getMessage());
        }

    }

    private boolean isGridFull(){
        int totalBoxes = gridLayout2048.getChildCount();
        if (totalBoxes >= 32){
            return true;
        }
        return false;
    }

    public void updateActualScore(int newValue){
        actualScore.setText(newValue);
    }

    public void showDefeatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER :(");
        builder.setMessage("Restart the game?");

        // Agrega el botón para reiniciar la partida
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //restartActivity(restartBtn);
            }
        });

        builder.setNegativeButton("Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                back2048ToStartMenu(backBtn);
            }
        });
        builder.show();
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