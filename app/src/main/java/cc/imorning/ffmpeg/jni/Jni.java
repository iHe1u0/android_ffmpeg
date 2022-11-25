package cc.imorning.ffmpeg.jni;

import android.util.Log;

public class Jni {

    private static final String TAG = "JniLoader";

    static {
        final String[] libs = {
                "ffmpeg",
                "avfilter",
                "avformat",
                "avcodec",
                "swscale",
                "swresample",
                "postproc",
                "avutil",
                "mp3lame"

        };
        for (String lib : libs) {
            try {
                System.loadLibrary(lib);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                Log.e(TAG, "load library " + lib + " failed", unsatisfiedLinkError);
            }
        }
    }
}
