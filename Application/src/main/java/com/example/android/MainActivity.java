package com.example.android;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.BluetoothLeService;
import com.example.android.bluetoothlegatt.DeviceControlActivity;
import com.example.android.bluetoothlegatt.DeviceScanActivity;
import com.example.android.bluetoothlegatt.R;
import com.example.android.bluetoothlegatt.SampleGattAttributes;
import com.example.android.dialog.DialogButtonCallback;
import com.example.android.dialog.DialogList;
import com.example.android.dialog.DialogMessage;
import com.example.android.dialog.DialogProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainActivity extends Activity {

    private final static String TAG = "APPTEST";
    private final static String PT = "playtango";

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    static Activity activity;
    static Context context;


    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    public boolean isconnected = false;

    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    // private BluetoothGattCharacteristic bluetoothGattCharacteristicHM_10;
    private BluetoothGattCharacteristic _notifyableCharac;
    private BluetoothGattCharacteristic _writeableCharac;
    private BluetoothGattCharacteristic _readableCharac;
    private TextView mConnectionState;
    private TextView mDataField;
    DialogList mScanListDialog;
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);





            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);


            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.e("APPTEST", action);
                String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                if(!data.contains(mDeviceName)) {
                    displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

                    Log.e("APPTEST", "Receive Data : " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                    Log.e(PT, "Receive Data : " + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));


                }

////


            }
        }
    };
    public static void startPlugin() {



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);
        mHandler = new Handler();
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        // Initializes list view adapter.


        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);



    }

    public void onScan(View view) {
        if (!isconnected) {
            mLeDeviceListAdapter = new LeDeviceListAdapter();

            mScanListDialog = new DialogList(MainActivity.this);
            mScanListDialog.setTitle("모듈 리스트");

            mScanListDialog.setAdapter(mLeDeviceListAdapter);
//            @Override
//            protected void onListItemClick(ListView l, View v, int position, long id) {
//                final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
//                if (device == null) return;
//                final Intent intent = new Intent(this, DeviceControlActivity.class);
//                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
//                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
//                if (mScanning) {
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                    mScanning = false;
//                }
//                startActivity(intent);
//            }
            mScanListDialog.setButtonListener(new DialogButtonCallback() {
                @Override
                public void onButtonClicked(int type, int pos) {
                    switch (type) {
                        case DialogButtonCallback.TYPE_LIST_ITEMCLICK:
                            final BluetoothDevice device = mLeDeviceListAdapter.getDevice(pos);
                            Log.e(TAG, "device : " + device );
                            if (device == null) return;

//                    intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
//                    intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                            mDeviceName = device.getName();
                            mDeviceAddress = device.getAddress();
                            if (mScanning) {
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                mScanning = false;
                            }
                            // startActivity(intent);
                            registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
                            if (mBluetoothLeService != null) {
                                final boolean result = mBluetoothLeService.connect(mDeviceAddress);
                                Log.d(TAG, "Connect request result=" + result);
                            }
                            break;
                    }



                    mScanListDialog.dismiss();
                }
            });
            mScanListDialog.show();
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);


        } else {
            mBluetoothLeService.disconnect();
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            isconnected = false;
        }
    }
    public void onSend(View view){
        if(_writeableCharac != null){
            _writeableCharac.setValue("s/i/r/2/3/4/e");
            mBluetoothLeService.writeCharacteristic(_writeableCharac);
            mBluetoothLeService.setCharacteristicNotification(_writeableCharac,true);
            Log.e(PT, "Send Data : " + "s/i/r/2/3/4/e");

        }
    }


    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = MainActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.view_main_item, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.sub);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.title);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.deviceName.setText(deviceName);
            else
                viewHolder.deviceName.setText(R.string.unknown_device);
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if(device.getName() != null) {
                                mLeDeviceListAdapter.addDevice(device);
                            }
                            mLeDeviceListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }


    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data) {
        if (data != null) {
            mDataField.setText(data);
        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {

//        UUID UUID_HM_10 =
//                UUID.fromString(SampleGattAttributes.HM_10);

        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            Log.e(PT, "uuid : " + uuid);
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (int i = 0; i < gattCharacteristics.size(); i++) {
                charas.add(gattCharacteristics.get(i));
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristics.get(i).getUuid().toString();

                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);

                BluetoothGattCharacteristic temp = gattCharacteristics.get(i);
//                if(isCharacteristicReadable(temp)){
//                    // WRITEABLE CHARACTERISTIC
//                    _readableCharac = gattService.getCharacteristic(UUID.fromString(temp.getUuid().toString()));
//                    if (mNotifyCharacteristic != null) {
//                        mBluetoothLeService.setCharacteristicNotification(
//                                mNotifyCharacteristic, false);
//                        mNotifyCharacteristic = null;
//                    }
//                    mBluetoothLeService.readCharacteristic(temp);
//                    Log.e("APPTEST", "readable : " + temp.getUuid().toString() + " // " + temp.getProperties());
//                }

                if(isCharacteristicNotifiable(temp)){
                    // NOTIFY CHARACTERISTIC
                    _notifyableCharac = gattService.getCharacteristic(UUID.fromString(temp.getUuid().toString()));
                    mNotifyCharacteristic = temp;
                    mBluetoothLeService.setCharacteristicNotification(
                            temp, true);

                    Log.e("APPTEST", "notify : " + temp.getUuid().toString() + " // " + temp.getProperties());
                    Log.e(PT, "notify : " + temp.getUuid().toString() + " Properties : " + temp.getProperties());
                }

                if(isCharacteristicWritable(temp)){
                    // WRITEABLE CHARACTERISTIC
                    _writeableCharac = gattService.getCharacteristic(UUID.fromString(temp.getUuid().toString()));
                    Log.e("APPTEST", "writeable : " + temp.getUuid().toString() + " // " + temp.getProperties());
                    Log.e(PT, "writeable : " + temp.getUuid().toString() + " Properties : " + temp.getProperties());
                }

            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);

            //     bluetoothGattCharacteristicHM_10 = gattService.getCharacteristic(UUID.fromString(uuid));




        }

    }



    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }



    /**
     * @return Returns <b>true</b> if property is writable
     */
    public static boolean isCharacteristicWritable(BluetoothGattCharacteristic pChar) {
        return (pChar.getProperties() & (BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) != 0;
    }

    /**
     * @return Returns <b>true</b> if property is Readable
     */
    public static boolean isCharacteristicReadable(BluetoothGattCharacteristic pChar) {
        return ((pChar.getProperties() & BluetoothGattCharacteristic.PROPERTY_READ) > 0);
    }

    /**
     * @return Returns <b>true</b> if property is supports notification
     */
    public boolean isCharacteristicNotifiable(BluetoothGattCharacteristic pChar) {
        return (pChar.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0;
    }


}
