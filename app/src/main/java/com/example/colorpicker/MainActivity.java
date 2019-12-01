package com.example.colorpicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         viewModel = obtainMainViewModel(this);
         viewModel.showDialog().observe(this, new Observer<ColorStore>() {
             @Override
             public void onChanged(ColorStore cs) {
                 showDialog(cs.red(), cs.green(), cs.blue());
             }
         });
    }

    public static MainViewModel obtainMainViewModel(FragmentActivity activity) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    private void showDialog(int red, int green, int blue) {
        ColorShowDialogFragment fragment = ColorShowDialogFragment.newInstance(red, green, blue);
        fragment.show(getSupportFragmentManager(), "dialog");
    }
}
