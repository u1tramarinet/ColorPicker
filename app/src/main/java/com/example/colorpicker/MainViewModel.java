package com.example.colorpicker;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private ColorStore colorStore = ColorStore.getInstance();

    @NonNull
    private final MutableLiveData<Integer> red = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> green = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> blue = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> argb = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<String> hex = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<ColorStore> showDialog = new MutableLiveData<>();

    public MainViewModel() {
        updateColor();
    }

    public LiveData<Integer> red() {
        return red;
    }

    public void setRed(int red) {
        colorStore.red(red);
        updateColor();
    }

    public LiveData<Integer> green() {
        return green;
    }

    public void setGreen(int green) {
        colorStore.green(green);
        updateColor();
    }

    public LiveData<Integer> blue() {
        return blue;
    }

    public void setBlue(int blue) {
        colorStore.blue(blue);
        updateColor();
    }

    public LiveData<Integer> argb() {
        return argb;
    }

    public void setHex(String hex) {
        colorStore.hex(hex);
        updateColor();
    }

    public LiveData<String> hex() {
        return hex;
    }

    public void requestShowDialog() {
        showDialog.postValue(colorStore);
    }

    public LiveData<ColorStore> showDialog() {
        return showDialog;
    }

    private void updateColor() {
        red.postValue(colorStore.red());
        green.postValue(colorStore.green());
        blue.postValue(colorStore.blue());
        argb.postValue(colorStore.argb());
        hex.postValue(colorStore.hex());
    }
}
