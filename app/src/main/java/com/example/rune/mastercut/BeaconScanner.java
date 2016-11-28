package com.example.rune.mastercut;

        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.le.BluetoothLeScanner;
        import android.bluetooth.le.ScanCallback;
        import android.bluetooth.le.ScanRecord;
        import android.bluetooth.le.ScanResult;
        import android.bluetooth.le.ScanSettings;
        import android.util.SparseArray;
        import java.nio.ByteBuffer;
        import java.util.Arrays;
        import java.util.UUID;

/**
 * original file created by loune from loune.net on 26/12/2015,
 * Full tutorial can be found at https://loune.net/2016/04/building-an-android-phone-connected-door-bell-with-light-blue-beans-ibeacon/
 */
public class BeaconScanner {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private OnBeaconDetectedListerner listener;

    public void setListener(OnBeaconDetectedListerner listener) {
        this.listener = listener;
    }

    protected ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            ScanRecord scanRecord = result.getScanRecord();
            SparseArray<byte[]> manufacturerData = scanRecord.getManufacturerSpecificData();
            if (manufacturerData.size() == 0) {
                return;
            }

            int manufacturerId = manufacturerData.keyAt(0);
            byte[] data = manufacturerData.get(manufacturerId);

            if (data.length < 23) {
                return;
            }

            int rssi = result.getRssi();
            double dist = calculateAccuracy(data[22], (double) rssi);
            byte[] uuid = Arrays.copyOfRange(data, 2, 18);
            BeaconInfo info = new BeaconInfo(
                    device.getName(),
                    device.getAddress(),
                    (short)manufacturerId,
                    (short)(((data[0] & 0xFF) << 8) | (data[1] & 0xFF)),
                    getUUIDFromByteArray(uuid),
                    (short)(((data[18] & 0xFF) << 8) | (data[19] & 0xFF)),
                    (short)(((data[20] & 0xFF) << 8) | (data[21] & 0xFF)),
                    data[22],
                    rssi,
                    dist);

            if (listener != null)
                listener.onBeaconDetected(device, info);
        }
    };

    public BeaconScanner() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void startScan() {
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        if (bluetoothLeScanner == null) {
            // bluetooth not enabled?
            return;
        }

        ScanSettings settings = new ScanSettings.Builder()
                .setReportDelay(0)
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();

        bluetoothLeScanner.startScan(null, settings, scanCallback);
    }

    public void endScan() {
        bluetoothLeScanner.stopScan(scanCallback);
        bluetoothLeScanner = null;
    }

    // http://stackoverflow.com/questions/20416218/understanding-ibeacon-distancing
    // returns approximate iOS distance in metres
    protected static double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return accuracy;
        }
    }

    private static UUID getUUIDFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        UUID uuid = new UUID(high, low);
        return uuid;
    }

    public interface OnBeaconDetectedListerner {
        void onBeaconDetected(BluetoothDevice device, BeaconInfo beaconInfo);
    }
}