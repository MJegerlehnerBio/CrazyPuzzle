package studio.crazypuzzlebeta.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import studio.crazypuzzlebeta.GameData
import studio.crazypuzzlebeta.MusicService
import studio.crazypuzzlebeta.R

// Author: Mischa Jegerlehner
// This activity is the entry point of the application.
// Here the user can either start a new game or he can look at the highscore.

// Tutorial for Puzzle Code:
// dragosholban.com

// Music:
// ~aether theories~ by Vidian (c) copyright 2018
// Licensed under a Creative Commons Attribution (3.0) license.
// http://dig.ccmixter.org/files/Vidian/57398 Ft: Gurdonark, White-throated Sparrow

// Drops of H2O ( The Filtered Water Treatment ) by J.Lang (c) copyright 2012
// Licensed under a Creative Commons Attribution (3.0) license.
// http://dig.ccmixter.org/files/djlang59/37792 Ft: Airtone
@RequiresApi(Build.VERSION_CODES.M)
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        startService(Intent(this, MusicService::class.java))
        startUploadActivity()
        startHighScoreActivity()
        exitGame()
    }

    // Author: Mischa Jegerlehner
    // Function to start a new game, leads to the picture select screen
    private fun startUploadActivity() {
        btn_newGame.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
            val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.confirm_sound)
            clickSound.setVolume(0.1f, 0.1f)
            clickSound.start()
        }
    }

    // Author: Mischa Jegerlehner
    // Function that leads the highscore screen
    private fun startHighScoreActivity() {
        btn_highscore.setOnClickListener {
            startActivity(Intent(this, HighScoreActivity::class.java))
            val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.confirm_sound_b)
            clickSound.setVolume(0.1f, 0.1f)
            clickSound.start()
        }
    }

    // Author: Mischa Jegerlehner (depricated!)
    // This function is implemented but has been commented out.
    // According to Android development conventions there should never be an exit
    // button in an Android app, which was not known to us at the beginning of this project.
    private fun exitGame() {
    //    btn_exit.setOnClickListener {
    //        finish()
    //    }
    }
}
