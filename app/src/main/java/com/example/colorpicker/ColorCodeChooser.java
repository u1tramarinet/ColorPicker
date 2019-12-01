package com.example.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ColorCodeChooser extends LinearLayout {
    public ColorCodeChooser(Context context) {
        this(context, null);
    }

    public ColorCodeChooser(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorCodeChooser(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ColorCodeChooser(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(context, R.layout.view_color_code_chooser, this);
    }
}
