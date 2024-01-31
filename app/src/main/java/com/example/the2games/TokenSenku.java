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
}
