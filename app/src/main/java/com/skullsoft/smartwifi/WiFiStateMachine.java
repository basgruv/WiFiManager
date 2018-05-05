package com.skullsoft.smartwifi;


import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class WiFiStateMachine extends PhoneStateListener{
    private static TelephonyManager telephonyManager;
    static final String CELLS_LIST_FILE_NAME = "cells_list.txt";
    private List<String> knwonCells;

    private boolean inKnownCellRange;
    private boolean wifiEnabled;
    private boolean wifiConnected;

    private WiFiStateListener wifiStateListener;
    private static Context applicationContext;

    public WiFiStateMachine(Context applicationContext) {
        this.applicationContext = applicationContext;
        knwonCells = LoadCells();

        telephonyManager = (TelephonyManager)applicationContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(this, LISTEN_CELL_LOCATION);
    }

    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);

        // handle disconnected phone network or unknown network type
        String cellName = location.toString();
    }

    // handle network events
    public void onWifiEnabled() {

    }

    public void onWifiDisabled() {

    }

    public void onWifiConnected() {

    }

    public void onWifiDisconnected() {

    }
    private static File getCellsFile() {
        return new File(applicationContext.getFilesDir(), CELLS_LIST_FILE_NAME);
    }

    private static ArrayList<String> LoadCells() {
        ArrayList<String> cellsList = new ArrayList<>();

        File cellsFile = getCellsFile();

        if (cellsFile.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(cellsFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferReader = new BufferedReader(inputStreamReader);

                while (fileInputStream.available() > 0) {
                    String line = bufferReader.readLine();
                    cellsList.add(line);
                }

                bufferReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cellsList;
    }

    private static void UpdateCells(ArrayList<String> cellsList) {
        File cellsFile = getCellsFile();
        cellsFile.delete();

        try {
            cellsFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(cellsFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);

            for(String cellName : cellsList) {
                try {
                    bufferWriter.write(cellName + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
