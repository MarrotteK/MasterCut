package com.example.rune.mastercut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Contract.DBHelper mDbHelper = new Contract.DBHelper(this); //Database initialization, Not sure if this works =\

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * BELOW:  BeaconScanner utilization from loune.net
         * Not currently working.....
         *
        BeaconScanner scanner = new BeaconScanner();
        scanner.setListener(new BeaconScanner.OnBeaconDetectedListerner() {
            long ignoreUntil = 0;
            @Override
            public void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo) {
                if (beaconInfo.getMajor() == 0 && beaconInfo.getMinor() == 1) {
                    if (ignoreUntil > System.currentTimeMillis())
                        return;

                    // play sound and show snack bar notification
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(fab, "GET THE DOOR", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    // ignore any beacons for the next 3.5 seconds (as we send bursts in the bean)
                    ignoreUntil = System.currentTimeMillis() + 3500;
                }
            }
        });
        scanner.startScan();
*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Starting Logging Mode...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
