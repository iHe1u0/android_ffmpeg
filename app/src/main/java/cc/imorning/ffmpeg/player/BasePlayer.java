package cc.imorning.ffmpeg.player;

import cc.imorning.ffmpeg.jni.Jni;

public class BasePlayer extends Jni {
    /**
     * Record play state
     */
    public PlayState playState;

    protected void onStateChanged(BasePlayer.PlayState state) {
        playState = state;
    }

    public enum PlayState {
        PREPARED, PLAYING, PAUSE, STOP, RELEASE
    }
}
