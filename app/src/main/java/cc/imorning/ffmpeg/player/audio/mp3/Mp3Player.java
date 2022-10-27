package cc.imorning.ffmpeg.player.audio.mp3;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import cc.imorning.ffmpeg.jni.Jni;
import cc.imorning.ffmpeg.player.BasePlayer;

public class Mp3Player extends BasePlayer {

    private AudioTrack audioTrack;

    public native int playAudio(String path);

    /**
     * 创建 AudioTrack
     * 由 C 反射调用
     *
     * @param sampleRate 采样率
     * @param channels   通道数
     */
    public void createAudioTrack(int sampleRate, int channels) {
        int channelConfig;
        if (channels == 1) {
            channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        } else if (channels == 2) {
            channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
        } else {
            channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        }
        int bufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, AudioFormat.ENCODING_PCM_16BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, channelConfig,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
        audioTrack.play();
        super.onStateChanged(BasePlayer.PlayState.PLAYING);
    }

    /**
     * 播放 AudioTrack
     * 由 C 反射调用
     */
    public void playAudioTrack(byte[] data, int length) {
        if (audioTrack != null && audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
            audioTrack.write(data, 0, length);
        }
    }

    /**
     * 释放 AudioTrack
     * 由 C 反射调用
     */
    public void releaseAudioTrack() {
        if (audioTrack != null) {
            if (audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
                audioTrack.stop();
            }
            audioTrack.release();
            audioTrack = null;
        }
        super.onStateChanged(PlayState.STOP);
    }
}