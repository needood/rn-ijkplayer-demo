package com.awesomeproject;

import android.content.IntentFilter;
import android.content.Intent;
import android.bluetooth.BluetoothHeadset;
import android.media.AudioManager;
import android.app.Service;
import android.os.IBinder;

public class MainService extends Service {

  private HeadsetPlugReceiver headsetPlugReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        registerHeadsetPlugReceiver();
    }
    private void registerHeadsetPlugReceiver() {
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AudioManager.ACTION_HEADSET_PLUG);
        intentFilter.addAction(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED);
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        registerReceiver(headsetPlugReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(headsetPlugReceiver);
        super.onDestroy();
    }

}
