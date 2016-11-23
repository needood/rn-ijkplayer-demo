package com.awesomeproject;

import android.net.Uri;
import android.content.Context;
import android.os.Bundle;
import android.media.AudioManager;
import com.facebook.react.ReactActivity;
import com.github.xinthink.rnmk.ReactMaterialKitPackage;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import android.media.AudioManager;
import android.widget.Toast;
import android.util.Log;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import java.util.Arrays;
import java.util.List;
import android.content.IntentFilter;
import android.content.Intent;
import android.bluetooth.BluetoothHeadset;

public class MainActivity extends ReactActivity{

  /**
  * Returns the name of the main component registered from JavaScript.
  * This is used to schedule rendering of the component.
  */
  private String TAG = "TTTTEST";
  private HeadsetPlugReceiver headsetPlugReceiver;


  @Override
  public void onCreate(Bundle icicle) {
    Log.d(TAG, "create");
    super.onCreate(icicle);
    IjkMediaPlayer.loadLibrariesOnce(null);
    IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    //registerHeadsetPlugReceiver();
    Intent intent = new Intent(this, MainActivity.class);
    startService(intent);
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
    //unregisterReceiver(headsetPlugReceiver);
    super.onDestroy();
  }







  @Override
  protected String getMainComponentName() {
    return "AwesomeProject";
  }

  /**
  * Returns whether dev mode should be enabled.
  * This enables e.g. the dev menu.
  */
  @Override
  protected boolean getUseDeveloperSupport() {
    return BuildConfig.DEBUG;
  }


  /**
  * A list of packages used by the app. If the app uses additional views
  * or modules besides the default ones, add more packages here.
  */
  @Override
  protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
    new MainReactPackage(),
    new ReactMaterialKitPackage(),
    new AwesomePackage()
    );
  }
}
