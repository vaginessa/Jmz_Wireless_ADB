package me.msfjarvis.wirelessadb;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.preference.PreferenceManager;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import static me.msfjarvis.wirelessadb.Utils.disableAdb;
import static me.msfjarvis.wirelessadb.Utils.enableAdb;
import static me.msfjarvis.wirelessadb.Utils.getIP;
import static me.msfjarvis.wirelessadb.Utils.preferences;
import static me.msfjarvis.wirelessadb.Utils.ADB_TILE_PREF_KEY;

@TargetApi(Build.VERSION_CODES.N)
public class AdbTile extends TileService {

    @Override
    public void onTileAdded(){
        SharedPreferences prefs = preferences(getApplicationContext());
        if (!prefs.contains(ADB_TILE_PREF_KEY)){
            prefs.edit().putInt(ADB_TILE_PREF_KEY,0).apply();
        }
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(ADB_TILE_PREF_KEY).apply();
        super.onTileRemoved();
    }

    @Override
    public void onClick(){
        Tile tile = getQsTile();
        Context context = getApplicationContext();
        SharedPreferences prefs = preferences(context);
        switch (prefs.getInt(ADB_TILE_PREF_KEY,0)){
            case 0:
                enableAdb();
                Toast.makeText(context, String.format("Run 'adb connect %s:5555' in terminal", getIP(context))
                        , Toast.LENGTH_LONG).show();
                tile.setLabel(String.format("%s:5555", getIP(context)));
                tile.setIcon(Icon.createWithResource(context,R.drawable.ic_qs_network_adb_on));
                prefs.edit().putInt(ADB_TILE_PREF_KEY,1).apply();
                tile.updateTile();
                break;
            case 1:
                disableAdb();
                tile.setLabel(getString(R.string.adb_tile_title));
                tile.setIcon(Icon.createWithResource(context,R.drawable.ic_qs_network_adb_off));
                prefs.edit().putInt(ADB_TILE_PREF_KEY,0).apply();
                tile.updateTile();
        }
    }

}
