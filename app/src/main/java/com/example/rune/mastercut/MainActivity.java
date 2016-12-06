package com.example.rune.mastercut;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                    }
                    @Override
                    public void onDenied(String permission) {
                    }
                });

        Contract.DBHelper mDbHelper = new Contract.DBHelper(this); //Database initialization, Not sure if this works yet =\

        setContentView(R.layout.activity_main);

        BeaconScanner scanner = new BeaconScanner();
        scanner.setListener(new BeaconScanner.OnBeaconDetectedListener(){
            long ignoreUntil = 0;
            @Override
            public void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo) {
                Log.d("BLUETOOTH", "onBeaconDetected starts, at least");
                if (beaconInfo.getMajor() == 0 && beaconInfo.getMinor() == 1) {
                    Log.d("BLUETOOTH", "Beacon Detected");
                    if (ignoreUntil > System.currentTimeMillis())
                        return;
                    try {
                        Toast toast = Toast.makeText(getApplicationContext(), "Win Listener works!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ignoreUntil = System.currentTimeMillis() + 3500;
                }
                if (beaconInfo.getMajor() == 0 && beaconInfo.getMinor() == 2) {
                    Log.d("BLUETOOTH", "Beacon Detected");
                    if (ignoreUntil > System.currentTimeMillis())
                        return;
                    try {
                        Toast toast = Toast.makeText(getApplicationContext(), "Lose Listener works!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ignoreUntil = System.currentTimeMillis() + 3500;
                }
            }
        });
        scanner.startScan();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "It works!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
            }
        });




        //GRAPH STUFF BELOW

        //The Calendar on the X-axis, currently unused
        //Calendar calendar = Calendar.getInstance();
        //Date d1 = calendar.getTime();
        //calendar.add(Calendar.DATE, 1);
        //Date d2 = calendar.getTime();
        //calendar.add(Calendar.DATE, 1);
        //Date d3 = calendar.getTime();



        //Then the graph (Utilizing GraphView by Jonas Gehring)
        GraphView graph = (GraphView) findViewById(R.id.graph);  //GraphView created by Jonas Gehring
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, -0.2),  //Change these to represent dataset
                new DataPoint(2, 0),
                new DataPoint(3, 0.8),
                new DataPoint(4, 1.6)
        });
        series.setDrawDataPoints(true);
        graph.addSeries(series);
        //GraphView.getViewport().setScalable(true);    Should make graph zoom / scrollable, currently broken

    }
}
