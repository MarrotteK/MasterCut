package com.example.rune.mastercut;

import android.bluetooth.BluetoothDevice;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.orm.SugarDb;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this,  //Grants all required permissions at runtime
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                    }
                    @Override
                    public void onDenied(String permission) {
                    }
                });

        setContentView(R.layout.activity_main);


        //Then the graph (Utilizing GraphView by Jonas Gehring)
        final GraphView graph = (GraphView) findViewById(R.id.graph);  //GraphView created by Jonas Gehring
        final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
        });

        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        series.setDrawDataPoints(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(30);
        graph.addSeries(series);
        new DataPoint(0,0);

        /**final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
         */

        BeaconScanner scanner = new BeaconScanner();
        scanner.setListener(new BeaconScanner.OnBeaconDetectedListener() {
            long ignoreUntil = 0;
            @Override
            public void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo) {
                if (beaconInfo.getMajor() == 0 && beaconInfo.getMinor() == 1) {
                    if (ignoreUntil > System.currentTimeMillis())
                        return;
                    try {
                        fightEntry fightentry = new fightEntry(TRUE);
                        fightentry.save();
                        double id = fightentry.getId();
                        SugarDb sugarDb = new SugarDb(getApplicationContext());
                        SQLiteDatabase database = sugarDb.getDB();
                        SQLiteStatement query = database.compileStatement("SELECT SUM(RESULT) FROM FIGHT_ENTRY");
                        double total = id - query.simpleQueryForLong();
                        double kdr = (query.simpleQueryForLong() / total);
                        if(total != 0){
                            series.appendData(new DataPoint(id, kdr), TRUE, 30);}
                        else{
                            series.appendData(new DataPoint(id, query.simpleQueryForLong()), TRUE, 30);}
                        query.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ignoreUntil = System.currentTimeMillis() + 3500;
                }
                if (beaconInfo.getMajor() == 0 && beaconInfo.getMinor() == 2) {
                    if (ignoreUntil > System.currentTimeMillis())
                        return;
                    try {
                        fightEntry fightentry = new fightEntry(FALSE);
                        fightentry.save();
                        double id = fightentry.getId();
                        SugarDb sugarDb = new SugarDb(getApplicationContext());
                        SQLiteDatabase database = sugarDb.getDB();
                        SQLiteStatement query = database.compileStatement("SELECT SUM(RESULT) FROM FIGHT_ENTRY");
                        double total = id - query.simpleQueryForLong();
                        double kdr = (query.simpleQueryForLong() / total);
                        if(total != 0){
                        series.appendData(new DataPoint(id, kdr), TRUE, 30);}
                        else{
                            series.appendData(new DataPoint(id, query.simpleQueryForLong()), TRUE, 30);}
                        query.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ignoreUntil = System.currentTimeMillis() + 3500;
                }
            }
        });
        scanner.startScan();
    }
}
