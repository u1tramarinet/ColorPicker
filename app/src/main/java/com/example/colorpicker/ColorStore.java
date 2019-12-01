package com.example.colorpicker;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.ColorInt;

public class ColorStore {
    private static ColorStore INSTANCE = new ColorStore();
    private final int VALUE_MIN = 0;
    private final int VALUE_MAX = 255;
    private @ColorInt int color;

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

    public String hex() {
        return Integer.toHexString(argb());
    }

    public ColorStore hex(String hex) {
        if (TextUtils.isEmpty(hex)) return this;

        char sharp = '#';
        if (hex.charAt(0) == sharp) {
            String digits = hex.substring(1);
            int length = digits.length();

            switch (length) {
                case 4:
                case 3:
                    hex = sharp + duplicateEachDigit(digits, 2);
                    break;
                case 2:
                    hex = sharp + duplicateDigit(digits.charAt(0), 2) + duplicateDigit(digits.charAt(1), 6);
                    break;
                case 1:
                    hex = sharp + duplicateDigit(digits.charAt(0), 6);
                    break;
            }
        }

        try {
            color = Color.parseColor(hex);
        } catch (IllegalArgumentException e) {
            // throw
        }
        return this;
    }

    private boolean isValueInRange(int value) {
        return ((VALUE_MIN <= value) && (value <= VALUE_MAX));
    }

    private String duplicateEachDigit(String input, int times) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            output.append(duplicateDigit(input.charAt(i), times));
        }
        return output.toString();
    }

    private String duplicateDigit(char input, int times) {
        StringBuilder output = new StringBuilder();
        for (int j = 0; j < times; j++) {
            output.append(input);
        }
        return output.toString();
    }
}
