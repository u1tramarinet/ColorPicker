package com.example.colorpicker;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ColorCodeChooser extends LinearLayout implements TextWatcher, CompoundButton.OnCheckedChangeListener {
    @NonNull
    private final EditText editText;
    @NonNull
    private final CheckBox checkBox;
    @NonNull
    private final Button copyButton;

    public interface OnColorCodeChangedListener {
        void onColorCodeChanged(ColorCodeChooser colorCodeChooser, String colorCode);
    }

    @Nullable
    private OnColorCodeChangedListener listener;

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

        editText = findViewById(R.id.input);
        editText.addTextChangedListener(this);
        checkBox = findViewById(R.id.alphaCheck);
        copyButton = findViewById(R.id.copyButton);
    }

    public void setColorCode(String colorCode) {
        if (colorCode.charAt(0) == '#') {
            colorCode = colorCode.substring(1);
        }

        if ((colorCode.length() == 8) && !checkBox.isChecked()) {
            colorCode = colorCode.substring(2);
        }

        if (TextUtils.equals(editText.getText().toString(), colorCode)) return;
        editText.setText(colorCode);
    }

    public void setOnColorCodeChangedListener(@Nullable OnColorCodeChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (listener != null) {
            String colorCode = '#' + editable.toString();
            listener.onColorCodeChanged(this, colorCode);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
