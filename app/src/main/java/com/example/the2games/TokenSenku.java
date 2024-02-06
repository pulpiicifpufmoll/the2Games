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

    public boolean canMove(int idBackgroundPosition) {
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
            columnTokenPosition = Character.getNumericValue(String.valueOf(tokenIdToMove).charAt(0));
        }

        if (rowBackground > rowTokenPositin + 2
                || rowBackground < rowTokenPositin - 2
                || columnBackground > columnTokenPosition + 2
                || columnBackground < columnTokenPosition - 2)
        {
            return false;
        }

        if (!isTokenNextTo(rowTokenPositin, columnTokenPosition)) {
            return false;
        }

        return true;
    }

    private boolean isTokenNextTo(int rowTokenPositin, int columnTokenPosition) {
        String idTokenTop = String.valueOf(rowTokenPositin - 1) + String.valueOf(columnTokenPosition);
        String idTokenBottom = String.valueOf(rowTokenPositin + 1) + String.valueOf(columnTokenPosition);
        String idTokenLeft = String.valueOf(rowTokenPositin) + String.valueOf(columnTokenPosition - 1);
        String idTokenRight = String.valueOf(rowTokenPositin) + String.valueOf(columnTokenPosition + 1);

        if (((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenTop)) != null){
            return true;
        }
        if (((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenBottom)) != null){
            return true;
        }
        if (((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenRight)) != null){
            return true;
        }
        if (((ActivitySenku) getContext()).findViewById(Integer.parseInt(idTokenLeft)) != null){
            return true;
        }
        return false;
    }
}
