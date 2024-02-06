package com.example.the2games;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

public class TokenSenku extends androidx.appcompat.widget.AppCompatImageButton {



    public TokenSenku(Context context, int i, int j) {
        super(context);

        GridLayout.LayoutParams tokenParams = new GridLayout.LayoutParams();
        tokenParams.rowSpec = GridLayout.spec(i);
        tokenParams.columnSpec = GridLayout.spec(j);
        this.setLayoutParams(tokenParams);
        this.setImageResource(R.drawable.senku_btn_on);

        this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.btnSenkuOnBackground)));

        String concatenatedRowColumn = String.valueOf(i) + String.valueOf(j);

        int idParsed = Integer.parseInt(concatenatedRowColumn);
        this.setId(idParsed);
    }

    public TokenSenku canMove(int idBackgroundPosition) {
        int tokenIdToMove = this.getId();

        int rowBackground;
        int columnBackground;

        int rowTokenPositin;
        int columnTokenPosition;

        //Comprobación por primera fila de espacios d fondo tablero que el id es un dígito
        if (String.valueOf(idBackgroundPosition).length() < 2){
            rowBackground = 0;
            columnBackground = Character.getNumericValue(String.valueOf(idBackgroundPosition).charAt(0));;
        } else {
            rowBackground = Character.getNumericValue(String.valueOf(idBackgroundPosition).charAt(0));
            columnBackground = Character.getNumericValue(String.valueOf(idBackgroundPosition).charAt(1));
        }

        //Comprobación por primera fila de id's de tokens que son de un dígito
        if (String.valueOf(tokenIdToMove).length() < 2){
            rowTokenPositin = 0;
            columnTokenPosition = Character.getNumericValue(String.valueOf(tokenIdToMove).charAt(0));

        } else {
            rowTokenPositin = Character.getNumericValue(String.valueOf(tokenIdToMove).charAt(0));
            columnTokenPosition = Character.getNumericValue(String.valueOf(tokenIdToMove).charAt(1));
        }

        String availableBackgroundPositionClicked = checkClickedBackground(rowBackground, rowTokenPositin, columnBackground, columnTokenPosition);

        if (availableBackgroundPositionClicked.equals("INVALID"))
        {
            return null;
        }

        TokenSenku tokenNextTo = getTokenNextTo(rowTokenPositin, columnTokenPosition, availableBackgroundPositionClicked);

        return tokenNextTo;
    }

    private String checkClickedBackground(int rowBackground, int rowTokenPositin, int columnBackground, int columnTokenPosition){

       if (rowBackground == rowTokenPositin + 1
                || rowBackground == rowTokenPositin - 1
                || columnBackground == columnTokenPosition + 1
                || columnBackground == columnTokenPosition - 1){
           return "INVALID";
        }

       if (rowBackground != rowTokenPositin && columnBackground == columnTokenPosition){
           //Vertical Click
           if (rowBackground == rowTokenPositin + 2){
               return "BOTTOM";
           } else if (rowBackground == rowTokenPositin - 2){
               return "TOP";
           }
       } else if (columnBackground != columnTokenPosition && rowBackground == rowTokenPositin) {
           //Horizontal Click
           if (columnBackground == columnTokenPosition + 2){
               return "RIGHT";
           } else if (columnBackground == columnTokenPosition - 2){
               return "LEFT";
           }
       }
        return "INVALID";
    }

    private TokenSenku getTokenNextTo(int rowTokenPosition, int columnTokenPosition, String position) {
        String idTokenTop = String.valueOf(rowTokenPosition - 1) + String.valueOf(columnTokenPosition);
        String idTokenBottom = String.valueOf(rowTokenPosition + 1) + String.valueOf(columnTokenPosition);
        String idTokenLeft = String.valueOf(rowTokenPosition) + String.valueOf(columnTokenPosition - 1);
        String idTokenRight = String.valueOf(rowTokenPosition) + String.valueOf(columnTokenPosition + 1);

        TokenSenku tokenNextTo = null;

        switch (position){
            case "TOP":
                tokenNextTo = ((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenTop));
                break;
            case "BOTTOM":
                tokenNextTo = ((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenBottom));
                break;
            case "LEFT":
                tokenNextTo = ((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenLeft));
                break;
            case "RIGHT":
                tokenNextTo = ((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenRight));
                break;
        }

        return tokenNextTo;

    }
}
