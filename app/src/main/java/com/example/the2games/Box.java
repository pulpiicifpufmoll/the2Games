package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.graphics.Color;
import android.util.Log;
import androidx.gridlayout.widget.GridLayout;

@SuppressLint("ViewConstructor")
public class Box extends androidx.appcompat.widget.AppCompatButton {
    private String value;

    @SuppressLint("ResourceType")
    public Box(Context context, String value, GridLayout.LayoutParams params, String targetRow, String targetColumn) {
        super(context);

        this.value = value;
        this.setLayoutParams(params);
        this.setBackgroundColor(R.color.box2);
        this.setId(Integer.parseInt(targetRow + targetColumn));
        this.setTextAppearance(R.style.SquareButtonText);
        this.setText(value);
    }

    public void moverDerecha(){
       // this.paramsLayout.rowSpec = GridLayout.spec(getNextPosition("derecha"));
    }

    public void moverIzquierda(){
       // this.paramsLayout.rowSpec = GridLayout.spec(getNextPosition("izquierda"));
    }

    public void moverArriba(){
       // this.paramsLayout.rowSpec = GridLayout.spec(getNextPosition("arriba"));
    }

    public void moverAbajo(){
        //this.paramsLayout.rowSpec = GridLayout.spec(getNextPosition("abajo"));
    }

    private int getNextPosition(String direction){
        String id = String.valueOf(this.getId());
        int column;
        int row;

        switch (direction){
            case "derecha":
                column = Integer.parseInt(String.valueOf(id.charAt(1)));
                return column + 1;
            case "izquierda":
                column = Integer.parseInt(String.valueOf(id.charAt(1)));
                return column - 1;
            case "arriba":
                row = Integer.parseInt(String.valueOf(id.charAt(0)));
                return row - 1;
            case "abajo":
                row = Integer.parseInt(String.valueOf(id.charAt(0)));
                return row + 1;
        }
        return 0;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
