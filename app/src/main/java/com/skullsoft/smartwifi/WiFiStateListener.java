package com.skullsoft.smartwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;


public class WiFiStateListener extends BroadcastReceiver {
    private WiFiStateMachine wiFiStateMachine;
    private IntentFilter wifiStateIntentFilter = new IntentFilter();

    public WiFiStateListener(Context applicationContext, WiFiStateMachine wifiEventsHandler) {
        this.wiFiStateMachine = wifiEventsHandler;

        wifiStateIntentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        applicationContext.registerReceiver(this, wifiStateIntentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
            if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)){
                // wifi connected
                wiFiStateMachine.onWifiConnected();
            } else {
                // wifi connection was lost
                wiFiStateMachine.onWifiDisconnected();
            }
        }
    }
}
