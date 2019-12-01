package com.example.colorpicker;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment implements ValueChooser.OnValueChangeListener {

    private View colorView;
    private ValueChooser valueChooserRed;
    private ValueChooser valueChooserGreen;
    private ValueChooser valueChooserBlue;

    private MainViewModel viewModel;

    public MainFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = MainActivity.obtainMainViewModel(getActivity());
        viewModel.red().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer red) {
                valueChooserRed.setValue(red);
            }
        });
        viewModel.green().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer green) {
                valueChooserGreen.setValue(green);
            }
        });
        viewModel.blue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer blue) {
                valueChooserBlue.setValue(blue);
            }
        });
        viewModel.argb().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer argb) {
                colorView.setBackgroundColor(argb);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        valueChooserRed = view.findViewById(R.id.vcRed);
        valueChooserRed.setOnValueChangedListener(this);
        valueChooserGreen = view.findViewById(R.id.vcGreen);
        valueChooserGreen.setOnValueChangedListener(this);
        valueChooserBlue = view.findViewById(R.id.vcBlue);
        valueChooserBlue.setOnValueChangedListener(this);
        colorView = view.findViewById(R.id.colorView);
    }

    @Override
    public void onValueChanged(@NonNull ValueChooser valueChooser, int value) {
        int id = valueChooser.getId();
        switch (id) {
            case R.id.vcRed:
                viewModel.setRed(value);
                break;
            case R.id.vcGreen:
                viewModel.setGreen(value);
                break;
            case R.id.vcBlue:
                viewModel.setBlue(value);
                break;
        }
    }
}
