package com.awesomeproject;



import android.widget.Toast;
import android.content.Context;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import android.util.Log;
import android.net.Uri;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.Callback;


import java.util.Map;
import java.util.HashMap;

import java.io.File;

public class LocalMediaModule extends ReactContextBaseJavaModule{
    private String TAG = "TTTTEST";

    public LocalMediaModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }
    @Override
    public String getName() {
        return "LocalMediaAndroid";
    }
    @ReactMethod
    public void getPreview(String path,String title,String album,String artist,Callback successCallback){
        String imageUri = "ic_library_music_black_24dp";
        successCallback.invoke(imageUri);
    }
    @ReactMethod
    public void scan(String path,Callback successCallback) {
        File file = new File(path);
        File[] files = getAllFiles(file);
        if(files!=null){
            WritableArray wArr = new WritableNativeArray();
            for (int i = 0; i < files.length; i++){
                 WritableMap wMap = new WritableNativeMap();
                 wMap.putString("name",files[i].getName());
                 wMap.putString("path",files[i].getPath());
                 wArr.pushMap(wMap);
            }
            successCallback.invoke(wArr);
        }
    }
    private File[] getAllFiles(File root){
        File[] files = root.listFiles();
        return files;
        /*
        if(files != null){
            for (File f : files){
                if(f.isDirectory()){
                    getAllFiles(f);
                }else{
                    System.out.println(f);
                }
            }
        }
        */
    }
}
