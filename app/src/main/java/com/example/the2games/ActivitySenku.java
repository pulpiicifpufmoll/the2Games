package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivitySenku extends AppCompatActivity{

    private GridLayout gridLayoutSenku;
    private int selectedTokenId = -1;
    private List<TokenSenku> actualTokens;
    private Button restartButton;
    private Button backToMenuBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        this.actualTokens = new ArrayList<>();
        this.restartButton = findViewById(R.id.restartBtn);
        this.backToMenuBtn = findViewById(R.id.backToMenuBtn);
        GridLayout layout = findViewById(R.id.gridLayoutSenku);
        this.gridLayoutSenku = layout;
        addListenersToButtons();
        setListenersBackgroundButtons(layout);
        generateInitalTokens(layout);
    }

    private void addListenersToButtons() {
        this.restartButton.setOnClickListener(this::restartActivity);
        this.backToMenuBtn.setOnClickListener(this::backSenkuToStartMenu);
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

    public boolean checkEmptyPositionsLayout(int i, int j){

        if (i == 0 && (j < 2 || j > 4)
        || i == 1 && (j < 2 || j > 4)
        || i == 5 && (j < 2 || j > 4)
        || i == 6 && (j < 2 || j > 4)
        || i == 3 && j == 3){
            return true;
        }

        //if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == 5) || (i == 0 && j == 6)
        //|| (i == 1 && j == 0) || (i == 1 && j == 1) || (i == 1 && j == 5) || (i == 1 && j == 6)
        //|| (i == 5 && j == 0) || (i == 5 && j == 1) || (i == 5 && j == 5) || (i == 5 && j == 6)
        //|| (i == 6 && j == 0) || (i == 6 && j == 1) || (i == 6 && j == 5) || (i == 6 && j == 6) || (i == 3 && j == 3)){
        //   return true;
        // }
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
                            GridLayout.LayoutParams paramsToken = (GridLayout.LayoutParams) senkuTokenToMove.getLayoutParams();

                            moveToken(senkuTokenToMove, paramsBack, paramsToken, idBackgroundPosition, senkuTokenToRemove);
                        }
                        selectedTokenId = -1;
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

    private void moveToken(TokenSenku token, GridLayout.LayoutParams paramsBack, GridLayout.LayoutParams paramsToken, int newPositionId, TokenSenku tokenToRemove){
        paramsToken.rowSpec = paramsBack.rowSpec;
        paramsToken.columnSpec = paramsBack.columnSpec;
        token.setId(newPositionId);
        token.setLayoutParams(paramsToken);
        removeToken(tokenToRemove);
    }

    private int getBackgroundIdPosition(ImageButton layoutButton){
        String resourceName = getResources().getResourceEntryName(layoutButton.getId());
        String idParsed = resourceName.substring(resourceName.length() - 2);
        return Integer.parseInt(idParsed);
    }

    private boolean checkIfGameEnds(){
        boolean gameCanEnd = false;

        for (int i = 0; i < this.gridLayoutSenku.getRowCount(); i++) {
            for (int j = 0; j < this.gridLayoutSenku.getColumnCount(); j++) {
                if (checkEmptyPositionsLayout(i, j)) {
                    continue;
                }
                String idParsed = String.valueOf(i) + String.valueOf(j);
                TokenSenku targetToken = findViewById(Integer.parseInt(idParsed));
                if (targetToken != null){
                    gameCanEnd = areDirectionsAvailableForTokenMovement(i, j);
                }


            }
        }

        return gameCanEnd;
    }

    private boolean areDirectionsAvailableForTokenMovement(int i, int j){
        //TODO check en cada posición si tiene ficha al lado y si dos posiciones al lado está libre
            // case "TOP":

            // case "BOTTOM":

            // case "LEFT":

            //case "RIGHT":

        return false;
    }

    public void restartActivity(View view){
        for (int i = 0; i < this.gridLayoutSenku.getRowCount(); i++){
            for (int j = 0; j < this.gridLayoutSenku.getColumnCount(); j++) {
                if (checkEmptyPositionsLayout(i, j)){
                    continue;
                }
                String idParsed = String.valueOf(i) + String.valueOf(j);
                TokenSenku token = findViewById(Integer.parseInt(idParsed));
                if (token != null){
                    removeToken(token);
                }
            }
        }
        setListenersBackgroundButtons(this.gridLayoutSenku);
        generateInitalTokens(this.gridLayoutSenku);
    }
}