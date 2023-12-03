package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;
import android.graphics.Color;
import android.util.Log;
import androidx.gridlayout.widget.GridLayout;

@SuppressLint("ViewConstructor")
public class Box extends androidx.appcompat.widget.AppCompatButton {
    private int rowPosition;
    private int columnPosition;
    private String value;

    private GridLayout.LayoutParams defaultParams = new GridLayout.LayoutParams();

    @SuppressLint({"ResourceType", "DefaultLocale"})
    public Box(Context context, String value, String targetRow, String targetColumn) {
        super(context);

        this.value = value;
        this.rowPosition = Integer.parseInt(targetRow);
        this.columnPosition = Integer.parseInt(targetColumn);

        this.defaultParams.rowSpec = GridLayout.spec(Integer.parseInt(targetRow));
        this.defaultParams.columnSpec = GridLayout.spec(Integer.parseInt(targetColumn));

        float density = getResources().getDisplayMetrics().density;
        this.defaultParams.width = (int) (80 * density);
        this.defaultParams.height = (int) (80  * density);
        this.defaultParams.leftMargin = (int) (3 * density);


        String concatenatedRowColumn = targetRow + targetColumn;
        this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box2)));
        this.setLayoutParams(defaultParams);
        this.setId(Integer.parseInt(String.format("%02d", Integer.parseInt(concatenatedRowColumn))));
        this.setTextAppearance(R.style.SquareButtonText);
        this.setText(value);
    }

    public void moverDerecha(){
        setDefaultParams(getNextPosition("derecha"));
        this.setLayoutParams(this.defaultParams);
    }

    public void moverIzquierda(){
        setDefaultParams(getNextPosition("izquierda"));
        this.setLayoutParams(this.defaultParams);
    }

    public void moverArriba(){
        setDefaultParams(getNextPosition("arriba"));
        this.setLayoutParams(this.defaultParams);
    }

    public void moverAbajo(){
        setDefaultParams(getNextPosition("abajo"));
        this.setLayoutParams(this.defaultParams);
    }

    private GridLayout.LayoutParams getNextPosition(String direction){
        try {
            int column = getColumnPosition();
            int row = getRowPosition();
            switch (direction){
                case "derecha":
                    column++;
                    setColumnPosition(Math.min(column, 3));
                    this.defaultParams.columnSpec = GridLayout.spec(Math.min(column, 3));
                    this.defaultParams.rowSpec = GridLayout.spec(row);
                    return getDefaultParams();
                case "izquierda":
                    column--;
                    setColumnPosition(Math.max(column, 0));
                    this.defaultParams.columnSpec = GridLayout.spec(Math.max(column, 0));
                    this.defaultParams.rowSpec = GridLayout.spec(row);
                    return getDefaultParams();
                case "arriba":
                    row--;
                    setRowPosition((Math.max(row, 0)));
                    this.defaultParams.rowSpec = GridLayout.spec(Math.max(row, 0));
                    this.defaultParams.columnSpec = GridLayout.spec(column);
                    return getDefaultParams();
                case "abajo":
                    row++;
                    setRowPosition((Math.min(row, 3)));
                    this.defaultParams.rowSpec = GridLayout.spec(Math.min(row, 3));
                    this.defaultParams.columnSpec = GridLayout.spec(column);
                    return getDefaultParams();
            }
        } catch (Exception e){
            Log.d("test", e.getMessage());
        }
        return null;
    }

    public GridLayout.LayoutParams getDefaultParams() {
        return defaultParams;
    }

    public void setDefaultParams(GridLayout.LayoutParams defaultParams) {
        this.defaultParams = defaultParams;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }
}
