package cc.imorning.ffmpeg;

import android.app.Application;

import cc.imorning.ffmpeg.jni.Jni;

public class FFmpegApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Jni();
    }
}
