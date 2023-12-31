package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                TokenSenku nuevaFicha = new TokenSenku(this, params, i, j);
                nuevaFicha.setOnClickListener(v -> selectToken(nuevaFicha.getId()));
                layout.addView(nuevaFicha);
            }
        }
    }

    private void selectToken(int tokenId){
        Log.d("test", "Seleccionado: " + tokenId);
        selectedTokenId = tokenId;
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
                    if (selectedTokenId != -1){
                        GridLayout.LayoutParams paramsBack = (GridLayout.LayoutParams) backgroundButton.getLayoutParams();
                        TokenSenku senkuToken = findViewById(selectedTokenId);
                        GridLayout.LayoutParams paramsToken = (GridLayout.LayoutParams) senkuToken.getLayoutParams();
                        paramsToken.rowSpec = paramsBack.rowSpec;
                        paramsToken.columnSpec = paramsBack.columnSpec;
                        senkuToken.setLayoutParams(paramsToken);
                    }
                }
            });

        }
    }

    public void backSenkuToStartMenu(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

}