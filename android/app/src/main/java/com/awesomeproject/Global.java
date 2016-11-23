package com.awesomeproject;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;

class Global{
    private static IjkExoMediaPlayer mMediaPlayer;
    public static void setMediaPlayer(IjkExoMediaPlayer MediaPlayer){
        mMediaPlayer = MediaPlayer;
    }
    public static IjkExoMediaPlayer getMediaPlayer(){
        return mMediaPlayer;
    }
}
