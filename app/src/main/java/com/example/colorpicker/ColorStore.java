package com.example.colorpicker;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ColorStore {
    private static ColorStore INSTANCE = new ColorStore();
    @ColorInt int color;
    private final int VALUE_MIN = 0;
    private final int VALUE_MAX = 255;

    private Executor executor = Executors.newSingleThreadExecutor();

    private ColorStore() {
        color = Color.BLACK;
    }

    public static ColorStore getInstance() {
        return INSTANCE;
    }

    public int argb() {
        return color;
    }

    public ColorStore argb(int alpha, int red, int green, int blue) {
        return alpha(alpha)
                .red(red)
                .green(green)
                .blue(blue);
    }

    public int alpha() {
        return Color.alpha(color);
    }

    public ColorStore alpha(int alpha) {
        if (isValueInRange(alpha)) {
            color = Color.argb(alpha, red(), green(), blue());
        }
        return this;
    }

    public int red() {
        return Color.red(color);
    }

    public ColorStore red(int red) {
        if (isValueInRange(red)) {
            color = Color.argb(alpha(), red, green(), blue());
        }
        return this;
    }

    public int green() {
        return Color.green(color);
    }

    public ColorStore green(int green) {
        if (isValueInRange(green)) {
            color = Color.argb(alpha(), red(), green, blue());
        }
        return this;
    }
    public int blue() {
        return Color.blue(color);
    }

    public ColorStore blue(int blue) {
        if (isValueInRange(blue)) {
            color = Color.argb(alpha(), red(), green(), blue);
        }
        return this;
    }

    private boolean isValueInRange(int value) {
        return ((VALUE_MIN <= value) && (value <= VALUE_MAX));
    }
}
