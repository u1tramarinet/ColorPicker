package com.example.colorpicker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ColorShowDialogFragment extends DialogFragment {
    private static final String KEY_RED = "red";
    private static final String KEY_GREEN = "green";
    private static final String KEY_BLUE = "blue";

    public static ColorShowDialogFragment newInstance(int red, int green, int blue) {
        ColorShowDialogFragment fragment = new ColorShowDialogFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_RED, red);
        args.putInt(KEY_GREEN, green);
        args.putInt(KEY_BLUE, blue);

        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_dialog_color_show, null);

        Bundle args = getArguments();
        if (args != null) {
            int red = args.getInt(KEY_RED, 0);
            int green = args.getInt(KEY_GREEN, 0);
            int blue = args.getInt(KEY_BLUE, 0);
            View colorView = view.findViewById(R.id.colorView);
            colorView.setBackgroundColor(Color.rgb(red, green, blue));

            TextView colorInfo = view.findViewById(R.id.colorInfo);
            colorInfo.setText(getString(R.string.format_color_info, red, green, blue));
        }

        builder.setView(view);
        builder.setTitle(R.string.title_dialog);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder.create();
    }
}
