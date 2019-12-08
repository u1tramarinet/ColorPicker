package com.example.colorpicker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ColorCodeChooser extends LinearLayout implements TextWatcher, CompoundButton.OnCheckedChangeListener {
    @NonNull
    private final EditText editText;
    @NonNull
    private final Button copyButton;
    @Nullable
    private String colorCode;

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
        copyButton = findViewById(R.id.copyButton);
        copyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard(getFullColorCode());
            }
        });
    }

    public void setColorCode(String colorCode) {
        if (colorCode.charAt(0) == '#') {
            colorCode = colorCode.substring(1);
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
        colorCode = editable.toString();
        if (listener != null) {
            listener.onColorCodeChanged(this, '#' + colorCode);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    private String getFullColorCode() {
        String input = editText.getText().toString();
        return '#' + input;
    }

    private boolean copyToClipboard(String text) {
        Context context = getContext();
        if ((TextUtils.isEmpty(text)) || (context == null)) {
            return false;
        }

        ClipboardManager manager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager == null) {
            return false;
        }

        manager.setPrimaryClip(ClipData.newPlainText("", text));
        return true;
    }
}
