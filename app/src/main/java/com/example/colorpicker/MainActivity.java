package com.example.colorpicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements ValueChooser.OnValueChangeListener,
        ColorCodeChooser.OnColorCodeChangedListener {

    private View colorView;
    private ValueChooser valueChooserRed;
    private ValueChooser valueChooserGreen;
    private ValueChooser valueChooserBlue;
    private ColorCodeChooser colorCodeChooser;
    private Button showButton;

    private ColorStore colorStore = ColorStore.getInstance();
    private boolean broadcasting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void onValueChanged(@NonNull ValueChooser valueChooser, int value) {
        if (broadcasting) return;
        int id = valueChooser.getId();
        switch (id) {
            case R.id.vcRed:
                colorStore.red(value);
                break;
            case R.id.vcGreen:
                colorStore.green(value);
                break;
            case R.id.vcBlue:
                colorStore.blue(value);
                break;
        }
        updateValues();
    }

    @Override
    public void onColorCodeChanged(ColorCodeChooser colorCodeChooser, String colorCode) {
        if (broadcasting) return;
        colorStore.hex(colorCode);
        updateValues();
    }

    private void initView() {
        valueChooserRed = findViewById(R.id.vcRed);
        valueChooserRed.setOnValueChangedListener(this);
        valueChooserGreen = findViewById(R.id.vcGreen);
        valueChooserGreen.setOnValueChangedListener(this);
        valueChooserBlue = findViewById(R.id.vcBlue);
        valueChooserBlue.setOnValueChangedListener(this);
        colorView = findViewById(R.id.colorView);
        colorCodeChooser = findViewById(R.id.colorCodeChooser);
        colorCodeChooser.setOnColorCodeChangedListener(this);
        showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(colorStore.red(), colorStore.green(), colorStore.blue());
            }
        });
    }

    private void showDialog(int red, int green, int blue) {
        ColorShowDialogFragment fragment = ColorShowDialogFragment.newInstance(red, green, blue);
        fragment.show(getSupportFragmentManager(), "dialog");
    }

    private void updateValues() {
        broadcasting = true;
        colorView.setBackgroundColor(colorStore.argb());
        valueChooserRed.setValue(colorStore.red());
        valueChooserGreen.setValue(colorStore.green());
        valueChooserBlue.setValue(colorStore.blue());
        colorCodeChooser.setColorCode(colorStore.hex());
        broadcasting = false;
    }
}
