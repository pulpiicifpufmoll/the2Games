package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

public class ActivitySenku extends AppCompatActivity{

    private GridLayout gridLayoutSenku;
    private Resources resources;
    private String packageName;
    private int selectedTokenId = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.resources = getResources();
        this.packageName = getPackageName();
        setContentView(R.layout.activity_senku);
        GridLayout layout = findViewById(R.id.gridLayoutSenku);
        this.gridLayoutSenku = layout;
        setListenersBackgroundButtons(layout);
        generarFichasIniciales(layout);
    }

    @SuppressLint("ResourceType")
    private void generarFichasIniciales(GridLayout layout) {

        for (int i = 0; i < layout.getRowCount(); i++){
            for (int j = 0; j < layout.getColumnCount(); j++) {
                if (checkEmptyPositionsLayout(i, j)){
                    continue;
                }
                TokenSenku nuevaFicha = new TokenSenku(this, i, j);
                nuevaFicha.setOnClickListener(v -> selectToken(nuevaFicha));
                layout.addView(nuevaFicha);
            }
        }
    }

    private void selectToken(TokenSenku token){
        Log.d("test", "Seleccionado: " + token.getId());
        selectedTokenId = token.getId();
    }

    private boolean checkEmptyPositionsLayout(int i, int j){

        if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == 5) || (i == 0 && j == 6)
        || (i == 1 && j == 0) || (i == 1 && j == 1) || (i == 1 && j == 5) || (i == 1 && j == 6)
        || (i == 5 && j == 0) || (i == 5 && j == 1) || (i == 5 && j == 5) || (i == 5 && j == 6)
        || (i == 6 && j == 0) || (i == 6 && j == 1) || (i == 6 && j == 5) || (i == 6 && j == 6) || (i == 3 && j == 3)){
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
                    Log.d("test", String.valueOf(getBackgroundIdPosition((ImageButton) v)));
                    if (selectedTokenId != -1){
                        int idBackgroundPosition = getBackgroundIdPosition(backgroundButton);
                        TokenSenku senkuTokenToMove = findViewById(selectedTokenId);
                        if (canTokenMove(idBackgroundPosition, selectedTokenId)){
                            //obtenemos params del imgBtnBack pulsado ya que necesitamos su fila y columna para el token
                            GridLayout.LayoutParams paramsBack = (GridLayout.LayoutParams) backgroundButton.getLayoutParams();
                            GridLayout.LayoutParams paramsToken = (GridLayout.LayoutParams) senkuTokenToMove.getLayoutParams();

                            moveToken(senkuTokenToMove, paramsBack, paramsToken, idBackgroundPosition);
                        }
                        selectedTokenId = -1;
                    }
                }
            });

        }
    }

    private boolean canTokenMove(int idBackgroundPosition, int selectedTokenId) {
        int rowBackground;
        int columnBackground;

        if (String.valueOf(idBackgroundPosition).length() < 2){
            rowBackground = 0;
        } else {
            rowBackground = String.valueOf(idBackgroundPosition).charAt(0);
        }

        columnBackground = String.valueOf(idBackgroundPosition).charAt(1);

        //con esto comprobamos si es la primera fila, ya que los Id son de un solo dígito
        if (String.valueOf(selectedTokenId).length() == 1){

            if (rowBackground > 2 || (columnBackground < selectedTokenId - 2 || columnBackground > selectedTokenId + 2)){
                return false;
            }
        } else {

        }

        return true;
    }

    private void moveToken(TokenSenku token, GridLayout.LayoutParams paramsBack, GridLayout.LayoutParams paramsToken, int newPositionId){
        paramsToken.rowSpec = paramsBack.rowSpec;
        paramsToken.columnSpec = paramsBack.columnSpec;
        token.setId(newPositionId);
        token.setLayoutParams(paramsToken);
    }

    public void backSenkuToStartMenu(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    private int getBackgroundIdPosition(ImageButton layoutButton){
        String resourceName = getResources().getResourceEntryName(layoutButton.getId());
        String idParsed = resourceName.substring(resourceName.length() - 2);
        return Integer.parseInt(idParsed);
    }
}