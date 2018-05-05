package com.skullsoft.smartwifi;

import android.view.View;
import android.widget.Button;

public class BtnStartStopClickListener implements View.OnClickListener {
    private boolean isRunning;

    public BtnStartStopClickListener(boolean isRunning)
    {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        synchronized (this) {
            return isRunning;
        }
    }

    @Override
    public void onClick(View view) {
        synchronized (this) {
            isRunning = !isRunning;

            String btnLabel = isRunning ? "Stop" : "Start";
            ((Button) view).setText(btnLabel);
        }
    }
}
