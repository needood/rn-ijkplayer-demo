package com.awesomeproject;



import android.widget.Toast;
import android.content.Context;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import android.util.Log;
import android.net.Uri;
import android.app.NotificationManager;
import java.util.Map;
import java.util.HashMap;
import android.app.Notification;
import android.support.v4.app.TaskStackBuilder;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.session.MediaSession;

public class PlayerModule extends ReactContextBaseJavaModule implements IjkExoMediaPlayer.OnPreparedListener , IjkExoMediaPlayer.OnCompletionListener{

    private int mId = 1;
    private static String TAG = "TTTTEST";
    private MediaSession mMediaSession;
    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";
    private Callback mCallback;

    public PlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    @Override
    public String getName() {
        return "PlayerAndroid";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }
    @ReactMethod
    public void play(String message,Callback successCallback) {
        IjkExoMediaPlayer mMediaPlayer = Global.getMediaPlayer();
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
        }
        if(mMediaSession==null){
            mMediaSession = new MediaSession(getReactApplicationContext(), TAG);
        }
        mCallback = successCallback;
        playAudio(message);
    }
    private void playAudio(String message) {
        try {
            IjkExoMediaPlayer mMediaPlayer = createMediaPlayer(getReactApplicationContext(),Uri.parse(message));
            Global.setMediaPlayer(mMediaPlayer);

        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }

    }

    @Override
    public void onPrepared(IMediaPlayer player){
        Log.d(TAG, "start");
        player.start();
        buildNotifcation("test","test");
    }
    @Override
    public void onCompletion(IMediaPlayer player){
        Log.d(TAG, "completion");
        mCallback.invoke();
    }
    public IjkExoMediaPlayer createMediaPlayer(Context context,Uri uri) {
        try {

            IjkExoMediaPlayer mp = new IjkExoMediaPlayer(context);
            mp.setDataSource(context,uri);
            mp.prepareAsync();
            Log.d(TAG, "prepare");
            mp.setOnPreparedListener(this);
            mp.setOnCompletionListener(this);
            return mp;
        } catch (Exception ex){
            Log.d(TAG, "create failed:", ex);
        }
        return null;
    }
    private void buildNotifcation(String title,String text){
        Context context = getReactApplicationContext();
            Notification.Builder mBuilder = new Notification.Builder(context)

            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_library_music_black_24dp)
    //.addAction(R.drawable.ic_prev, "Previous", prevPendingIntent) // #0
    //.addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)  // #1
    //.addAction(R.drawable.ic_next, "Next", nextPendingIntent)     // #2
            .setStyle(new Notification.MediaStyle()
            //.setShowActionsInCompactView(1 /* #1: pause button */)
            .setMediaSession(mMediaSession.getSessionToken()))
            .setContentTitle(title)
            .setContentText(text);
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mId, mBuilder.build());

    }
}
