package cc.imorning.ffmpeg.player.video.mp4;

import android.view.Surface;

import cc.imorning.ffmpeg.player.BasePlayer;

public class Mp4Player extends BasePlayer {

    public native void playVideo(String path, Surface surface);

}
