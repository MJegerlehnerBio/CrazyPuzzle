package studio.crazypuzzlebeta

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import java.util.*

// Author: Mischa Jegerlehner
// This music service makes sure that the background music still plays even when we
// leave the app (which happens for example when we open the gallery in the UploadActivity)
class MusicService: Service() {
    // Author: Mischa Jegerlehner
    // This variable holds the mediaPlayer which we use for playing sounds
    private lateinit var mediaPlayer: MediaPlayer

    // this variable holds the sound files which we want to play in the mediaPlayer
    private var soundFile: Int = R.raw.aether_theories_digccmixter_org

    override fun onBind(p0: Intent?): IBinder? {
        TODO("not implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        pickFirstSongRandomly()
        playSoundTheme(soundFile)
        changeSoundOnRestart()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSoundTheme()
    }

    // Author: Mischa Jegerlehner
    // Randomly picks one of the two available themes and sets the sound file accordingly
    private fun pickFirstSongRandomly() {
        if(GameData.random(0, 100) >= 50) {
            soundFile = R.raw.aether_theories_digccmixter_org
        } else {
            soundFile = R.raw.h20_theme
        }
    }

    // Author: Mischa Jegerlehner
    // This function keeps track if a song is currently plaing and if not
    // it will play the next song that is in the sound cue
    private fun changeSoundOnRestart() {
        Handler().postDelayed({
            if(!mediaPlayer.isPlaying) {
                if(soundFile == R.raw.aether_theories_digccmixter_org) {
                    soundFile = R.raw.h20_theme
                } else {
                    soundFile = R.raw.aether_theories_digccmixter_org
                }
                playSoundTheme(soundFile)
            }
            changeSoundOnRestart()
        }, 1000)
    }

    // Author: Mischa Jegerlehner
    // Plays the specified sound with a specific volume throug the mediaPlayer
    private fun playSoundTheme(soundFile: Int) {
        mediaPlayer = MediaPlayer.create(this, soundFile)
        mediaPlayer.setVolume(0.5f, 0.5f)
        mediaPlayer.start()
    }

    // Author: Mischa Jegerlehner
    // Stops the sound of the music service
    private fun stopSoundTheme() {
        mediaPlayer.stop()
    }
}
