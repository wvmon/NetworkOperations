package com.example.williamv.networkoperations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cm;
    public NetworkReceiver nr;

    Button btn;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        nr = new NetworkReceiver();

        IntentFilter iFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(nr, iFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (nr != null) {
            unregisterReceiver(nr);
        }
    }

    public void onShowNetworkStatus(View v) {
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            if (ni != null && ni.isConnected()) {
                Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            test = (TextView)findViewById(R.id.text);
            btn = (Button)findViewById(R.id.submit);

            NetworkInfo netinfo = cm.getActiveNetworkInfo();

            if (netinfo != null) {
                if (netinfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    btn.setEnabled(true);
                    test.setText("You are connected!!!");
                    test.setBackgroundColor(Color.GREEN);
                }
                else if (netinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    btn.setEnabled(true);
                    test.setText("You are using your data");
                    test.setBackgroundColor(Color.YELLOW);
                }
            }
            else {
                btn.setEnabled(false);
                test.setText("Aww you lost connection!");
                test.setBackgroundColor(Color.RED);
            }
        }
    }
}