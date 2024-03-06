package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class ActivitySenku extends AppCompatActivity{

    private GridLayout gridLayoutSenku;
    private List<TokenSenku> actualTokens;
    private List<TokenSenku> previousTokens;
    private int selectedTokenId = -1;
    private Button restartBtn;
    private Button backToMenuBtn;
    private ImageButton previousMovementBtn;
    private TextView movements;
    private TextView timerView;
    private boolean isGameStarted;
    private CountDownTimer timer;
    private int timerMinuts = 10;
    private int timerSeconds = 0;
    private GridLayout.LayoutParams previousPositionParams;
    private int previousTokenId;
    private TokenSenku lastMovedToken;
    private TokenSenku lastDeletedToken;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        this.actualTokens = new ArrayList<>();
        this.restartBtn = findViewById(R.id.restartBtn);
        this.backToMenuBtn = findViewById(R.id.backToMenuBtn);
        this.previousMovementBtn = findViewById(R.id.previousMove);
        this.movements = findViewById(R.id.movementValue);
        this.timerView = findViewById(R.id.timeView);
        this.isGameStarted = false;
        GridLayout layout = findViewById(R.id.gridLayoutSenku);
        this.gridLayoutSenku = layout;
        addListenersToButtons();
        setListenersBackgroundButtons(layout);
        generateInitalTokens(layout);
    }

    private void addListenersToButtons() {
        this.restartBtn.setOnClickListener(this::restartActivity);
        this.backToMenuBtn.setOnClickListener(this::backSenkuToStartMenu);
        this.previousMovementBtn.setOnClickListener(this::previousMovement);
    }

    @SuppressLint("ResourceType")
    private void generateInitalTokens(GridLayout layout) {
        for (int i = 0; i < layout.getRowCount(); i++){
            for (int j = 0; j < layout.getColumnCount(); j++) {
                if (checkEmptyPositionsLayout(i, j)){
                    continue;
                }
                TokenSenku newToken = new TokenSenku(this, i, j);
                newToken.setOnClickListener(v -> selectToken(newToken));
                layout.addView(newToken);
                this.actualTokens.add(newToken);
            }
        }
    }

    private void selectToken(TokenSenku token){
        selectedTokenId = token.getId();
    }

    private void startCountdownTimer() {
        timer = new CountDownTimer((timerMinuts*60+timerSeconds)*1000, 1000) {
            public void onTick(long millisUntilFinished) {

                if(timerSeconds == 0) {
                    timerMinuts--;
                    timerSeconds = 59;
                }else{
                    timerSeconds--;
                }
                timerView.setText("Time: " + String.format("%02d:%02d", timerMinuts, timerSeconds));
            }

            public void onFinish() {
                showDefeatDialog();
            }
        }.start();
    }

    private boolean checkEmptyPositionsLayout(int i, int j){

        if (i == 0 && (j < 2 || j > 4)
        || i == 1 && (j < 2 || j > 4)
        || i == 5 && (j < 2 || j > 4)
        || i == 6 && (j < 2 || j > 4)
        || i == 3 && j == 3){
            return true;
        }
        return false;
    }

    private void setListenersBackgroundButtons(GridLayout layout){
        for (int i = 0; i < layout.getChildCount(); i++) {
            ImageButton backgroundButton = (ImageButton) layout.getChildAt(i);

            backgroundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedTokenId != -1){
                        int idBackgroundPosition = getBackgroundIdPosition(backgroundButton);
                        TokenSenku senkuTokenToMove = findViewById(selectedTokenId);
                        TokenSenku senkuTokenToRemove = senkuTokenToMove.canMove(idBackgroundPosition);
                        if (senkuTokenToRemove != null){
                            //obtenemos params del imgBtnBack pulsado ya que necesitamos su fila y columna para el update de posición del token
                            GridLayout.LayoutParams paramsBack = (GridLayout.LayoutParams) backgroundButton.getLayoutParams();

                            moveToken(senkuTokenToMove, paramsBack, idBackgroundPosition, senkuTokenToRemove);
                        }
                        selectedTokenId = -1;
                        endGame(checkIfIsDefeat(), checkIfIsVictory());
                    }
                }
            });

        }
    }

    public void backSenkuToStartMenu(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private void removeToken(TokenSenku token){
        this.actualTokens.remove(token);
        this.gridLayoutSenku.removeView(token);
    }

    private void updateMovements(boolean isPrevious){
        int updatedValue;

        String actualValue = (String) getMovements().getText();
        if (!isPrevious){
            updatedValue = Integer.valueOf(actualValue) + 1;
        } else {
            updatedValue = Integer.valueOf(actualValue) - 1;
        }
        this.movements.setText(String.valueOf(Math.max(0, updatedValue)));
    }

    private void moveToken(TokenSenku token, GridLayout.LayoutParams paramsBack, int newPositionId, TokenSenku tokenToRemove){
        if (!isGameStarted){
            isGameStarted = true;
            startCountdownTimer();
        }

        this.previousPositionParams = (GridLayout.LayoutParams) token.getLayoutParams();
        this.previousTokenId = token.getId();

        GridLayout.LayoutParams newLayoutParams = new GridLayout.LayoutParams();

        newLayoutParams.rowSpec = paramsBack.rowSpec;
        newLayoutParams.columnSpec = paramsBack.columnSpec;

        token.setId(newPositionId);
        token.setLayoutParams(newLayoutParams);

        this.lastMovedToken = token;
        this.lastDeletedToken = tokenToRemove;

        removeToken(tokenToRemove);

        updateMovements(false);
    }

    private int getBackgroundIdPosition(ImageButton layoutButton){
        String resourceName = getResources().getResourceEntryName(layoutButton.getId());
        String idParsed = resourceName.substring(resourceName.length() - 2);
        return Integer.parseInt(idParsed);
    }

    private void endGame(boolean isDefeat, boolean isVictory){
        if (isVictory){
            showVictoryDialog();
        } else if (isDefeat){
            showDefeatDialog();
        }
    }

    private boolean checkIfIsVictory(){
        if (actualTokens.size() == 1){
            return true;
        }
        return false;
    }

    private boolean checkIfIsDefeat(){
        boolean areMovesAvailables = false;

        for (int i = 0; i < this.gridLayoutSenku.getRowCount(); i++) {
            for (int j = 0; j < this.gridLayoutSenku.getColumnCount(); j++) {
                if (checkEmptyPositionsLayout(i, j)) {
                    continue;
                }
                String idParsed = String.valueOf(i) + String.valueOf(j);
                TokenSenku targetToken = findViewById(Integer.parseInt(idParsed));
                if (targetToken != null){
                    areMovesAvailables = areDirectionsAvailableForTokenMovement(i, j, "TOP");

                    if (!areMovesAvailables) {
                        areMovesAvailables  = areDirectionsAvailableForTokenMovement(i, j, "BOTTOM");
                    }
                    if (!areMovesAvailables) {
                        areMovesAvailables  = areDirectionsAvailableForTokenMovement(i, j, "LEFT");
                    }
                    if (!areMovesAvailables) {
                        areMovesAvailables  = areDirectionsAvailableForTokenMovement(i, j, "RIGHT");
                    }
                }
                if (areMovesAvailables){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean areDirectionsAvailableForTokenMovement(int i, int j, String dir){
        String idParsed;
        TokenSenku tokenNextTo;
        TokenSenku tokenNextTo2Positions;

        switch (dir){
            case "TOP":
                idParsed = String.valueOf(Math.max(i - 1, 0)) + String.valueOf(j);
                tokenNextTo = findViewById(Integer.parseInt(idParsed));

                idParsed = String.valueOf(Math.max(i - 2, 0)) + String.valueOf(j);
                tokenNextTo2Positions = findViewById(Integer.parseInt(idParsed));

                if (tokenNextTo != null && tokenNextTo2Positions == null){
                    return true;
                }
                break;

            case "BOTTOM":
                idParsed = String.valueOf(Math.min(i + 1, 6)) + String.valueOf(j);
                tokenNextTo = findViewById(Integer.parseInt(idParsed));

                idParsed = String.valueOf(Math.min(i + 2, 6)) + String.valueOf(j);
                tokenNextTo2Positions = findViewById(Integer.parseInt(idParsed));

                if (tokenNextTo != null && tokenNextTo2Positions == null){
                    return true;
                }
                break;
            case "LEFT":
                idParsed = String.valueOf(i) + String.valueOf(Math.max(j - 1, 0));
                tokenNextTo = findViewById(Integer.parseInt(idParsed));

                idParsed = String.valueOf(i) + String.valueOf(Math.max(j - 2, 0));
                tokenNextTo2Positions = findViewById(Integer.parseInt(idParsed));

                if (tokenNextTo != null && tokenNextTo2Positions == null){
                    return true;
                }
                break;
            case "RIGHT":
                idParsed = String.valueOf(i) + String.valueOf(Math.min(j + 1, 6));
                tokenNextTo = findViewById(Integer.parseInt(idParsed));

                idParsed = String.valueOf(i) + String.valueOf(Math.min(j + 2, 6));
                tokenNextTo2Positions = findViewById(Integer.parseInt(idParsed));

                if (tokenNextTo != null && tokenNextTo2Positions == null){
                    return true;
                }
                break;
        }
        return false;
    }

    private void restartMenuInfo(){
        timer.cancel();
        timerView.setText("Time: 10:00");
        this.movements.setText("0");
    }

    public void showVictoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VICTORY :D");
        builder.setMessage("Congratulations, you are a senku genius!");
        builder.setCancelable(false);

        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartActivity(restartBtn);
            }
        });

        builder.setNegativeButton("Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backSenkuToStartMenu(backToMenuBtn);
            }
        });
        builder.show();
    }

    public void showDefeatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER :(");
        builder.setMessage("Restart the game?");
        builder.setCancelable(false);

        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartActivity(restartBtn);
            }
        });

        builder.setNegativeButton("Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backSenkuToStartMenu(backToMenuBtn);
            }
        });
        builder.show();
    }

    public void restartActivity(View view){
        for (int i = 0; i < this.gridLayoutSenku.getRowCount(); i++){
            for (int j = 0; j < this.gridLayoutSenku.getColumnCount(); j++) {
                String idParsed = String.valueOf(i) + String.valueOf(j);
                TokenSenku token = findViewById(Integer.parseInt(idParsed));
                if (token != null){
                    removeToken(token);
                }
            }
        }
        setListenersBackgroundButtons(this.gridLayoutSenku);
        generateInitalTokens(this.gridLayoutSenku);
        restartMenuInfo();
        this.isGameStarted = false;
    }

    public void previousMovement(View view) {
        actualTokens.add(lastDeletedToken);

        for (TokenSenku t : this.actualTokens) {
            if (t == lastMovedToken){
                t.setId(previousTokenId);
                t.setLayoutParams(previousPositionParams);
            } else if (t == lastDeletedToken){
                gridLayoutSenku.addView(t);
            }
        }

        updateMovements(true);
    }

    public TextView getMovements() {
        return movements;
    }

    public void setMovements(TextView movements) {
        this.movements = movements;
    }

    public GridLayout getGridLayoutSenku() {
        return gridLayoutSenku;
    }

    public void setGridLayoutSenku(GridLayout gridLayoutSenku) {
        this.gridLayoutSenku = gridLayoutSenku;
    }

    public List<TokenSenku> getActualTokens() {
        return actualTokens;
    }

    public void setActualTokens(List<TokenSenku> actualTokens) {
        this.actualTokens = actualTokens;
    }

    public List<TokenSenku> getPreviousTokens() {
        return previousTokens;
    }

    public void setPreviousTokens(List<TokenSenku> previousTokens) {
        this.previousTokens = previousTokens;
    }
}