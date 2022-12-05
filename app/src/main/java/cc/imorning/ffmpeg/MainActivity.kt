package cc.imorning.ffmpeg

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cc.imorning.ffmpeg.databinding.ActivityMainBinding
import cc.imorning.ffmpeg.player.audio.mp3.Mp3Player
import cc.imorning.ffmpeg.player.video.mp4.Mp4Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mp3Player: Mp3Player
    private lateinit var mp4Player: Mp4Player
    private val rootPath = Environment.getExternalStorageDirectory().path.plus(File.separator)
    private val audioFile: String = rootPath.plus("audio.mp3")
    private val videoFile: String = rootPath.plus("video.mp4")

    private lateinit var surfaceHolder: SurfaceHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // get permission
        getPermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        surfaceHolder = binding.surfaceView.holder

        var mp3Exist = File(audioFile).exists()
        Log.i(TAG, "onCreate: $mp3Exist")
        if (mp3Exist) {
            mp3Player = Mp3Player()
            MainScope().launch(Dispatchers.IO) {
                mp3Player.playAudio(audioFile)
            }
        }
    }

    fun play(view: View) {
        mp4Player = Mp4Player()
        if (mp3Player != null) {
            mp3Player.releaseAudioTrack()
        }
        binding.play.visibility = View.GONE
        MainScope().launch(Dispatchers.IO) {
            mp4Player.playVideo(videoFile, surfaceHolder.surface)
        }
    }

    private fun getPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        }
    }

    override fun onStop() {
        super.onStop()
        exitProcess(0)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}