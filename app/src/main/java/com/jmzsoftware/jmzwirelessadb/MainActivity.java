/**
 * Jmz Wireless ADB
 *
 * Copyright 2016 by Jmz Software <support@jmzsoftware.com>
 *
 *
 * Some open source application is free software: you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * Some open source application is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this code.  If not, see https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.jmzsoftware.jmzwirelessadb;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.jmzsoftware.jmzwirelessadb.Utils.disableAdb;
import static com.jmzsoftware.jmzwirelessadb.Utils.enableAdb;
import static com.jmzsoftware.jmzwirelessadb.Utils.getIP;
import static com.jmzsoftware.jmzwirelessadb.Utils.preferences;


public class MainActivity extends AppCompatActivity {

    Context context;
    TextView textView;
    Button adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        adb = (Button) findViewById(R.id.button);
        adb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adb.getText() == getString(R.string.disable_adb)) {
                    if (preferences(context).getInt("adb_title",0)==1){
                        adbDisabled();
                    } else {
                        enableAdb();
                        adbEnabled();
                    }
                } else {
                    enableAdb();
                    adbEnabled();
                }
            }
        });

        textView = (TextView) findViewById(R.id.textView);
    }
    private void adbDisabled(){
        textView.setText("");
        adb.setText(R.string.enable_adb);
    }

    private void adbEnabled(){
        textView.setText(String.format("Run 'adb connect %s:5555' in terminal", getIP(context)));
        adb.setText(R.string.disable_adb);
    }
}
