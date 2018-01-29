package com.example.moonsu.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "Main";

    // Intent request code
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;

    // Layout
    private Button btn_Connect;
    private TextView txt_Result;
    Button btn_on;
    private Button btn_off;

    byte[] out = {1,2};


    private BluetoothService btService = null;

    private final Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");

        setContentView(R.layout.activity_main);

        btn_Connect = (Button) findViewById(R.id.btn_connect);
        txt_Result = (TextView) findViewById(R.id.txt_result);
        btn_on = (Button) findViewById(R.id.btn_on);
        btn_off = (Button) findViewById(R.id.btn_off);
        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btService.write("1".getBytes());
            }
        });
        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btService.write("2".getBytes());
            }
        });
        btn_Connect.setOnClickListener(this);

        if(btService == null)
        {
            btService = new BluetoothService(this, mHandler);
        }


    }

    @Override
    public void onClick(View v)
    {
        Toast toast = Toast.makeText(MainActivity.this, "click!!!!!!!!!!!!!!!!!!!.",
                Toast.LENGTH_SHORT);
        toast.show();
        if(btService.getDeviceState())
        {
            // 블루투스가 지원 가능한 기기일 때
            btService.enableBluetooth();
        }
        else
        {
            finish();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    btService.getDeviceInfo(data);
                    Toast toast = Toast.makeText(this, "OK btn click!.",
                            Toast.LENGTH_SHORT);
                    toast.show();

                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때
                    btService.scanDevice();

                    //Next Step
                } else {
                    // 취소 눌렀을 때
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }
}

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK)
                {
                    selectDecive();
                }
                else if(resultCode == RESULT_CANCELED)
                {
                    Toast.makeText(getApplicationContext(),"블루투스를 사용할수 없어 앱을 종료 합니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectDecive()
    {
        Set<BluetoothDevice> mDevice = mBluetoothAdapter.getBondedDevices();
        mPairedDeviceCount = mDevice.size();
        if(mPairedDeviceCount == 0)
        {
            Toast.makeText(getApplication(),"페어링 된 장치가 없다.",Toast.LENGTH_SHORT).show();
            finish();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("블루투스 장치 선택");

        List<String> listItems = new ArrayList<String>();
        for(BluetoothDevice device : mDevice)
        {
            listItems.add(device.getName());
        }
        listItems.add("취소");
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(items == mPairedDeviceCount)
                {

                }
            }
        })
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
    */
