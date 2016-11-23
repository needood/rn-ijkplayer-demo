package com.awesomeproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.media.AudioManager;
import android.util.Log;
import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;
import android.bluetooth.BluetoothHeadset;

public class HeadsetPlugReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        Log.i("TTTTEST","onReceive:"+intent.getAction());
        if (extras != null) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                if(state==0){
                    IjkExoMediaPlayer mMediaPlayer = Global.getMediaPlayer();
                    if(mMediaPlayer!=null){
                        mMediaPlayer.pause();
                    }
                }
            }else
            if (intent.getAction().equals(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE ,-1);
                if (state==BluetoothHeadset.STATE_AUDIO_DISCONNECTED) {
                    IjkExoMediaPlayer mMediaPlayer = Global.getMediaPlayer();
                    if(mMediaPlayer!=null){
                        mMediaPlayer.pause();
                    }
                }
            }else
            if (intent.getAction().equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE ,-1);
                if (state==BluetoothHeadset.STATE_DISCONNECTING||state==BluetoothHeadset.STATE_DISCONNECTED) {
                    IjkExoMediaPlayer mMediaPlayer = Global.getMediaPlayer();
                    if(mMediaPlayer!=null){
                        mMediaPlayer.pause();
                    }
                }
            }
        }
    }
}
