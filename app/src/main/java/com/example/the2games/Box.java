package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;

@SuppressLint("ViewConstructor")
public class Box extends androidx.appcompat.widget.AppCompatButton {
    private int rowPosition;
    private int columnPosition;
    private String value;

    private GridLayout.LayoutParams defaultParams = new GridLayout.LayoutParams();

    @SuppressLint({"ResourceType", "DefaultLocale"})
    public Box(Context context, String value, String targetRow, String targetColumn) {
        super(context);

        this.setClickable(false);
        this.value = value;
        this.rowPosition = Integer.parseInt(targetRow);
        this.columnPosition = Integer.parseInt(targetColumn);

        this.defaultParams.rowSpec = GridLayout.spec(Integer.parseInt(targetRow));
        this.defaultParams.columnSpec = GridLayout.spec(Integer.parseInt(targetColumn));

        float density = getResources().getDisplayMetrics().density;
        this.defaultParams.width = (int) (80 * density);
        this.defaultParams.height = (int) (80  * density);
        this.defaultParams.leftMargin = (int) (3 * density);

        this.setLayoutParams(defaultParams);

        setValueAndStyle(value);
        //this.setBackgroundTintList(setValueAndStyle(value));
        //String laMezlca = String.valueOf(this.rowPosition) + String.valueOf(this.columnPosition);
        //this.setText(laMezlca);

        String concatenatedRowColumn = targetRow + targetColumn;
        this.setId(Integer.parseInt(String.format("%02d", Integer.parseInt(concatenatedRowColumn))));

        this.setTextAppearance(R.style.SquareButtonText);
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

    private void setValueAndStyle(String value){
        switch (value){
            case "2":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box2)));
                this.setText("2");
                break;
            case "4":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box4)));
                this.setText("4");
                break;
            case "8":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box8)));
                this.setText("8");
                break;
            case "16":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box16)));
                this.setText("16");
                break;
            case "32":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box32)));
                this.setText("32");
                break;
            case "64":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box64)));
                this.setText("64");
                break;
            case "128":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box128)));
                this.setText("128");
                break;
            case "256":
                this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.box256)));
                this.setText("256");
                break;
        }
        setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.black)));
    }

    private GridLayout.LayoutParams getNextPosition(String direction){
        try {
            int column = getColumnPosition();
            int row = getRowPosition();
            //TODO esto tendrá que ser 2 4 8 16...
            String updateValueTest = "";
            int updatedId;
            switch (direction){
                case "derecha":
                    while (true){
                        if (column == 3){
                            break;
                        }
                        String idParsed = String.valueOf(row) +  String.valueOf(column + 1);
                        Box nextBoxOfTarget = ((Activity2048) getContext()).findViewById(Integer.parseInt(idParsed));
                        if (nextBoxOfTarget != null){
                            if (this.value.equals(nextBoxOfTarget.getValue())){
                                //TODO irá la movida de multiplicar los valores para dar el nuevo,
                                // luego tocará cambiar el estilo el cuadrado en base al nuevo valor
                                //this.setText(updateValueTest);
                            } else {
                                Log.d("test", "BOX AL LADO!!");
                                break;
                            }
                        }
                        column++;
                        setColumnPosition(column);
                        updateValueTest = String.valueOf(this.rowPosition) + String.valueOf(this.columnPosition);
                        this.setText(updateValueTest);
                        this.setId(Integer.parseInt(updateValueTest));
                        this.defaultParams.columnSpec = GridLayout.spec(column);
                        this.defaultParams.rowSpec = GridLayout.spec(row);
                    }
                    Log.d("test", "NUEVO ID: " +  String.valueOf(Integer.parseInt(updateValueTest)));
                    return getDefaultParams();
                case "izquierda":
                    while (true){
                        if (column == 0){
                            break;
                        }
                        String idParsed = String.valueOf(row) +  String.valueOf(column - 1);
                        Box nextBoxOfTarget = ((Activity2048) getContext()).findViewById(Integer.parseInt(idParsed));

                        if (nextBoxOfTarget != null){
                            if (this.value.equals(nextBoxOfTarget.getValue())){
                                //TODO irá la movida de multiplicar los valores para dar el nuevo,
                                // luego tocará cambiar el estilo el cuadrado en base al nuevo valor
                                //this.setText(updateValueTest);
                            } else {
                                Log.d("test", "BOX AL LADO!!");
                                break;
                            }
                        }
                        column--;
                        setColumnPosition(column);
                        updateValueTest = String.valueOf(this.rowPosition) + String.valueOf(this.columnPosition);
                        this.setText(updateValueTest);
                        this.setId(Integer.parseInt(updateValueTest));
                        this.defaultParams.columnSpec = GridLayout.spec(column);
                        this.defaultParams.rowSpec = GridLayout.spec(row);
                    }
                    Log.d("test", "NUEVO ID: " +  String.valueOf(Integer.parseInt(updateValueTest)));
                    return getDefaultParams();
                case "arriba":
                    while (true){
                        if (row == 0){
                            break;
                        }
                        String idParsed = String.valueOf(row - 1) +  String.valueOf(column);
                        Box nextBoxOfTarget = ((Activity2048) getContext()).findViewById(Integer.parseInt(idParsed));
                        if (nextBoxOfTarget != null){
                            if (this.value.equals(nextBoxOfTarget.getValue())){
                                //TODO irá la movida de multiplicar los valores para dar el nuevo,
                                // luego tocará cambiar el estilo el cuadrado en base al nuevo valor
                                //this.setText(updateValueTest);
                            } else {
                                Log.d("test", "BOX AL LADO!!");
                                break;
                            }
                        }
                        row--;
                        setRowPosition(row);
                        updateValueTest = String.valueOf(this.rowPosition) + String.valueOf(this.columnPosition);
                        this.setText(updateValueTest);
                        this.setId(Integer.parseInt(updateValueTest));
                        this.defaultParams.columnSpec = GridLayout.spec(column);
                        this.defaultParams.rowSpec = GridLayout.spec(row);
                    }
                    Log.d("test", "NUEVO ID: " +  String.valueOf(Integer.parseInt(updateValueTest)));
                    return getDefaultParams();
                case "abajo":
                    while (true){
                        if (row == 3){
                            break;
                        }
                        String idParsed = String.valueOf(row + 1) +  String.valueOf(column);
                        Box nextBoxOfTarget = ((Activity2048) getContext()).findViewById(Integer.parseInt(idParsed));
                        if (nextBoxOfTarget != null){
                            if (this.value.equals(nextBoxOfTarget.getValue())){
                                //TODO irá la movida de multiplicar los valores para dar el nuevo,
                                // luego tocará cambiar el estilo el cuadrado en base al nuevo valor
                                //this.setText(updateValueTest);
                            } else {
                                Log.d("test", "BOX AL LADO!!");
                                break;
                            }
                        }
                        row++;
                        setRowPosition(row);
                        updateValueTest = String.valueOf(this.rowPosition) + String.valueOf(this.columnPosition);
                        this.setText(updateValueTest);
                        this.setId(Integer.parseInt(updateValueTest));
                        this.defaultParams.columnSpec = GridLayout.spec(column);
                        this.defaultParams.rowSpec = GridLayout.spec(row);
                    }
                    Log.d("test", "NUEVO ID: " +  String.valueOf(Integer.parseInt(updateValueTest)));
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
