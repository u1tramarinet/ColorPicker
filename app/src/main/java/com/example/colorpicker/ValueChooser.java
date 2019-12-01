package com.example.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.colorpicker.util.OnLongPressListener;

public class ValueChooser extends ConstraintLayout implements SeekBar.OnSeekBarChangeListener, TextWatcher, View.OnClickListener {
    private static final int INVALID_VALUE = -1;
    @NonNull
    private final EditText editText;
    @NonNull
    private final SeekBar seekBar;

    private int value;

    private int max;

    private boolean synchronizing = false;

    public interface OnValueChangeListener {
        void onValueChanged(@NonNull ValueChooser valueChooser, int value);
    }
    @Nullable
    private OnValueChangeListener listener;

    public ValueChooser(Context context) {
        this(context, null);
    }

    public ValueChooser(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValueChooser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_value_chooser, this);

        TextView textView = findViewById(R.id.title);
        editText = findViewById(R.id.input);
        seekBar = findViewById(R.id.seekBar);

        Button buttonMinus10 = findViewById(R.id.buttonMinus10);
        buttonMinus10.setOnClickListener(this);
        buttonMinus10.setOnLongClickListener(new OnLongPressListener());
        Button buttonMinus1 = findViewById(R.id.buttonMinus1);
        buttonMinus1.setOnClickListener(this);
        buttonMinus1.setOnLongClickListener(new OnLongPressListener());
        Button buttonPlus1 = findViewById(R.id.buttonPlus1);
        buttonPlus1.setOnClickListener(this);
        buttonPlus1.setOnLongClickListener(new OnLongPressListener());
        Button buttonPlus10 = findViewById(R.id.buttonPlus10);
        buttonPlus10.setOnClickListener(this);
        buttonPlus10.setOnLongClickListener(new OnLongPressListener());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ValueChooser, defStyleAttr, 0);

        String title;
        int color;

        try {
            title = a.getString(R.styleable.ValueChooser_title);
            value = a.getInt(R.styleable.ValueChooser_value, 0);
            max = a.getInt(R.styleable.ValueChooser_max, 100);
            color = a.getColor(R.styleable.ValueChooser_color, INVALID_VALUE);
        } finally {
            a.recycle();
        }

        if (title != null) {
            textView.setText(title);
        }

        seekBar.setMax(max);
        updateAndApplyValue();

        if (color != INVALID_VALUE) {
            setBackgroundColor(color);
//            seekBar.setBackgroundColor(color);
//            Drawable drawable = seekBar.getThumb();
//            seekBar.setThumb(new ColorDrawable(color));
        }

        editText.addTextChangedListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    public void setValue(int value) {
        if (this.value == value) return;
        this.value = value;
        updateAndApplyValue();
    }

    public int getValue() {
        return value;
    }

    public void setMax(int max) {
        if (this.max == max) return;
        this.max = max;
        updateAndApplyValue();
    }

    public int getMax() {
        return max;
    }

    public void setOnValueChangedListener(@Nullable OnValueChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int after = value;
        switch (id) {
            case R.id.buttonMinus10:
                after += -10;
                break;
            case R.id.buttonMinus1:
                after += -1;
                break;
            case R.id.buttonPlus1:
                after += 1;
                break;
            case R.id.buttonPlus10:
                after += 10;
                break;
            default:
                return;
        }
        setValue(after);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (synchronizing) return;

        synchronizing = true;
        int v;
        try {
             v = Integer.parseInt(editable.toString());
        } catch (NumberFormatException e) {
            v = seekBar.getProgress();
        }
        setValue(v);
        synchronizing= false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (synchronizing) return;

        synchronizing = true;
        setValue(progress);
        synchronizing= false;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void updateAndApplyValue() {
        if (value > max) {
            value = max;
        } else if (value < 0) {
            value = 0;
        }
        seekBar.setMax(max);

        if (value != seekBar.getProgress()) {
            seekBar.setProgress(value);
        }

        if (!(String.valueOf(value)).equals(editText.getText().toString())) {
            editText.setText(String.valueOf(value));
        }

        if (listener != null) {
            Log.d(ValueChooser.class.getSimpleName(), "onValueChanged/in value=" + value);
            listener.onValueChanged(this, value);
        }
    }
}
