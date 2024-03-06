package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.gridlayout.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Activity2048 extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private final Random randomNumGenerator = new Random(System.currentTimeMillis());
    private final int NUM_ROWS = 4;
    private final int NUM_COLUMNS = 4;
    private CountDownTimer timer;
    private int timerMinuts = 10;
    private int timerSeconds = 0;
    private GestureDetector myGestureListener;
    private GridLayout gridLayout2048;
    private Button newGameBtn;
    private Button backBtn;
    private ImageButton previousButton;
    private TextView actualScore;
    private TextView bestScore;
    private TextView timerView;
    private SharedPreferences sharedPreferences;
    private ArrayList<Box> previousBoxes;
    private int previousActualScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048);
        this.myGestureListener = new GestureDetector(this, this);
        this.gridLayout2048 = findViewById(R.id.gridLayout2048);
        this.newGameBtn = findViewById(R.id.newGameBtn);
        this.backBtn = findViewById(R.id.backBtn);
        this.previousButton = findViewById(R.id.previousMove2048);
        this.bestScore = findViewById(R.id.best);
        this.timerView = findViewById(R.id.timerView);
        this.actualScore = findViewById(R.id.score);
        this.previousBoxes = new ArrayList<>();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        setInitialScores();
        addListenersToButtons();
    }

    private void addListenersToButtons() {
        this.newGameBtn.setOnClickListener(this::startGame);
        this.backBtn.setOnClickListener(this::back2048ToStartMenu);
        this.previousButton.setOnClickListener(this::previousMovement);
    }

    public void startGame(View view) {
        setInitialScores();
        emptyLayout();
        generateBoxes();
        generateBoxes();
        startCountdownTimer();
    }

    private void setInitialScores(){
        int bestScore = ActivityLogin.getUserBestScore();
        setBestScore(bestScore);
        updateActualScore(0);
    }

    private void emptyLayout() {
        for (int i = NUM_COLUMNS - 1; i >= 0; i--) {
            for (int j = 0; j <= NUM_ROWS - 1; j++) {
                String idParsed = String.valueOf(j) + String.valueOf(i);
                Box boxToRemove = findViewById(Integer.parseInt(idParsed));
                if (boxToRemove != null) {
                    gridLayout2048.removeView(boxToRemove);
                }
            }
        }
    }

    private void startCountdownTimer() {
        if (timer == null){
            timer = new CountDownTimer((timerMinuts * 60 + timerSeconds) * 1000, 1000) {
                public void onTick(long millisUntilFinished) {

                    if (timerSeconds == 0) {
                        timerMinuts--;
                        timerSeconds = 59;
                    } else {
                        timerSeconds--;
                    }
                    timerView.setText("Time: " + String.format("%02d:%02d", timerMinuts, timerSeconds));

                    if (timerSeconds == 0 && timerMinuts == 0){
                        this.onFinish();
                    }
                }

                public void onFinish() {
                    this.cancel();
                    showDefeatDialog();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    private void resetTimer(){
        timer.cancel();
        timer = null;
        timerSeconds = 0;
        timerMinuts = 10;
        startCountdownTimer();
    }

    private void generateBoxes() {
        int targetRow = randomNumGenerator.nextInt(NUM_ROWS);
        int targetColumn = randomNumGenerator.nextInt(NUM_COLUMNS);

        if (checkIfBoxExists(String.valueOf(targetRow) + String.valueOf(targetColumn))) {
            generateBoxes();
            return;
        }
        Box button2 = new Box(this, "2", targetRow, targetColumn);
        gridLayout2048.addView(button2);
    }

    private boolean checkIfBoxExists(String id) {
        if (findViewById(Integer.parseInt(id)) != null) {
            return true;
        }
        return false;
    }

    @SuppressLint("ResourceType")
    private void moveBoxes(String eje, String direccion) {
        try {
            if (eje.equals("horizontal")) {
                if (direccion.equals("derecha")) {
                    for (int i = NUM_COLUMNS - 2; i >= 0; i--) {
                        for (int j = 0; j <= NUM_ROWS - 1; j++) {
                            String idParsed = String.valueOf(j) + String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null) {
                                boxToMoVE.moverDerecha();
                            }
                        }
                    }
                } else if (direccion.equals("izquierda")) {
                    for (int i = 1; i <= NUM_COLUMNS - 1; i++) {
                        for (int j = 0; j <= NUM_ROWS - 1; j++) {
                            String idParsed = String.valueOf(j) + String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null) {
                                boxToMoVE.moverIzquierda();
                            }
                        }
                    }
                }
            } else if (eje.equals("vertical")) {
                if (direccion.equals("arriba")) {
                    for (int i = 0; i <= NUM_COLUMNS - 1; i++) {
                        for (int j = 1; j <= NUM_ROWS - 1; j++) {
                            String idParsed = String.valueOf(j) + String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null) {
                                boxToMoVE.moverArriba();
                            }
                        }
                    }
                } else if (direccion.equals("abajo")) {
                    for (int i = 0; i <= NUM_COLUMNS - 1; i++) {
                        for (int j = NUM_ROWS - 2; j >= 0; j--) {
                            String idParsed = String.valueOf(j) + String.valueOf(i);
                            Box boxToMoVE = findViewById(Integer.parseInt(idParsed));
                            if (boxToMoVE != null) {
                                boxToMoVE.moverAbajo();
                            }
                        }
                    }
                }
            }
            boolean itsGridFull = isGridFull();
            if (itsGridFull) {
                updateBestScore();
                showDefeatDialog();
            }
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

    }

    private void updateBestScore() {
        int actualValue = Integer.parseInt(getActualScore().getText().toString());

        int bestScoreSaved = ActivityLogin.getUserBestScore();
        String username = ActivityLogin.getUsername();

        if (actualValue > bestScoreSaved) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(username + "_score2048", actualValue);
            editor.apply();
        }
    }

    private boolean isGridFull() {
        int totalBoxes = gridLayout2048.getChildCount();
        if (totalBoxes >= 32) {
            return true;
        }
        return false;
    }

    private void showDefeatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER :(");
        builder.setMessage("Restart the game?");
        builder.setCancelable(false);

        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startGame(newGameBtn);
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

    private void setBestScore(int bestScore) {
        this.bestScore.setText(String.valueOf(bestScore));
    }

    private void savePreviousBoxes() {
        this.previousBoxes.clear();

        for (int i = 0; i <= NUM_ROWS - 1; i++) {
            for (int j = 0; j <= NUM_COLUMNS - 1; j++) {
                String idParsed = String.valueOf(j) + String.valueOf(i);
                Box box = findViewById(Integer.parseInt(idParsed));
                if (box != null) {
                    Box prevBoxToSave = new Box(this, box.getValue(), box.getRowPosition(), box.getColumnPosition());
                    prevBoxToSave.setLayoutParams(box.getLayoutParams());
                    previousBoxes.add(prevBoxToSave);
                }
            }
        }
    }

    private void loadPreviousBoxes(){
        for (Box b : previousBoxes){
            this.gridLayout2048.addView(b);
        }
    }

    public void previousMovement(View view){
        emptyLayout();
        loadPreviousBoxes();
        updateActualScore(getPreviousActualScore());
    }

    public void updateActualScore(int newValue) {
        actualScore.setText(String.valueOf(newValue));
    }

    public void back2048ToStartMenu(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return myGestureListener.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        savePreviousBoxes();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            //horizontal move
            if (deltaX > 0) {
                moveBoxes("horizontal", "derecha");
            } else {
                moveBoxes("horizontal", "izquierda");
            }
        } else {
            //vertical move
            if (deltaY > 0) {
                moveBoxes("vertical", "abajo");
            } else {
                moveBoxes("vertical", "arriba");
            }

        }
        if (!isGridFull()) {
            generateBoxes();
        }
        return true;
    }

    public int getPreviousActualScore() {
        return previousActualScore;
    }

    public void setPreviousActualScore(int previousActualScore) {
        this.previousActualScore = previousActualScore;
    }

    public GridLayout getGridLayout2048() {
        return gridLayout2048;
    }

    public TextView getActualScore() {
        return actualScore;
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