package com.jmzsoftware.jmzwirelessadb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.preference.PreferenceManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import static com.jmzsoftware.jmzwirelessadb.Utils.disableAdb;
import static com.jmzsoftware.jmzwirelessadb.Utils.enableAdb;
import static com.jmzsoftware.jmzwirelessadb.Utils.getIP;

@TargetApi(Build.VERSION_CODES.N)
public class AdbTile extends TileService {

    @Override
    public void onTileAdded(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());
        if (!prefs.contains("adb_tile")){
            prefs.edit().putInt("adb_tile",0).apply();
        }
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("adb_tile").apply();
        super.onTileRemoved();
    }

    @Override
    public void onClick(){
        Tile tile = getQsTile();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());
        Context context = getApplicationContext();
        switch (prefs.getInt("adb_tile",0)){
            case 0:
                enableAdb();
                Toast.makeText(context, String.format("Run 'adb connect %s:5555' in terminal", getIP(context))
                        , Toast.LENGTH_LONG).show();
                tile.setLabel(String.format("%s:5555", getIP(context)));
                tile.setIcon(Icon.createWithResource(context,R.drawable.ic_qs_network_adb_on));
                prefs.edit().putInt("adb_tile",1).apply();
                tile.updateTile();
                break;
            case 1:
                disableAdb();
                tile.setLabel(getString(R.string.adb_tile_title));
                tile.setIcon(Icon.createWithResource(context,R.drawable.ic_qs_network_adb_off));
                prefs.edit().putInt("adb_tile",0).apply();
                tile.updateTile();
        }
    }

}
