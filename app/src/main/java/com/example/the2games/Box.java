package com.example.the2games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

public class Box extends androidx.appcompat.widget.AppCompatButton {

    @SuppressLint("ResourceType")
    public Box(Context context, int value) {
        super(context);
        this.setBackgroundResource(R.style.SquareBoxButtonStyle);
        this.setText(value);
    }

}
