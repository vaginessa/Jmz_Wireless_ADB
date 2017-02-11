package com.jmzsoftware.jmzwirelessadb;


import android.content.Context;
import android.net.wifi.WifiManager;

import eu.chainfire.libsuperuser.Shell;

public class Utils {

    public static void enableAdb () {
        final String[] commands = { "setprop service.adb.tcp.port 5555", "stop adbd", "start adbd" };
        Thread runSu = new Thread(new Runnable() {
            @Override
            public void run() {
                Shell.SU.run(commands);
            }
        });
        runSu.start();
    }

    public static void disableAdb () {
        final String[] commands = { "stop adbd" };
        Thread runSu = new Thread(new Runnable() {
            @Override
            public void run() {
                Shell.SU.run(commands);
            }
        });
        runSu.start();
    }

    public static String getIP (Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int ip = mWifiManager.getConnectionInfo().getIpAddress();
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 24) & 0xFF);

    }
}
