package com.awesomeproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.media.AudioManager;
import android.util.Log;
import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;

public class PhoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if(intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    IjkExoMediaPlayer mMediaPlayer = Global.getMediaPlayer();
                    if(mMediaPlayer!=null){
                        mMediaPlayer.pause();
                    }
                    //String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    //Log.w("MY_DEBUG_TAG", phoneNumber);
                }
            }
        }
    }
}
