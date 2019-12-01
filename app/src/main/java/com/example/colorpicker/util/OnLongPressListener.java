package com.example.colorpicker.util;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OnLongPressListener implements View.OnLongClickListener {
    private static final int DEFAULT_REPEAT_INTERVAL = 100;

    private final Runnable repeatRunnable = new Runnable() {
        @Override
        public void run() {
            if ((view == null) || !pressing) return;

            if (!onLongPress(view)) {
                view.performClick();
            }

            handler.postDelayed(this, repeatInterval);
        }
    };

    @NonNull
    private final Handler handler = new Handler();

    @Nullable
    private View view = null;
    private boolean pressing = false;
    private int repeatInterval;

    public OnLongPressListener() {
        this(DEFAULT_REPEAT_INTERVAL);
    }

    public OnLongPressListener(int repeatInterval) {
        this.repeatInterval = (repeatInterval > 0) ? repeatInterval : DEFAULT_REPEAT_INTERVAL;
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == null) return false;
        pressing = true;
        this.view = view;
        setOnTouchListener(view);

        handler.post(repeatRunnable);

        return true;
    }

    protected boolean onLongPress(View view) {
        return false;
    }

    private void setOnTouchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    pressing = false;
                }
                return false;
            }
        });
    }
}
