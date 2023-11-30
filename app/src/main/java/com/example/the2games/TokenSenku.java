package com.example.the2games;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

public class TokenSenku extends androidx.appcompat.widget.AppCompatImageButton {

    public TokenSenku(Context context, GridLayout.LayoutParams params, int i, int j) {
        super(context);

        this.setLayoutParams(params);
        this.setImageResource(R.drawable.senku_btn_on);
        this.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.btnSenkuOnBackground)));
        int idParsed = Integer.parseInt(String.valueOf(i) + String.valueOf(j));
        this.setId(idParsed);
    }
}
