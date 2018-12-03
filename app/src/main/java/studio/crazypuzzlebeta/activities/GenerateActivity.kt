package studio.crazypuzzlebeta.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import kotlinx.android.synthetic.main.activity_generate.*
import studio.crazypuzzlebeta.GameData
import studio.crazypuzzlebeta.R

// Author: Mischa Jegerlehner
// This activtiy displays the screen where you can give your name
// and decide how hard the puzzle should be. You can decide the amount
// of pieces and how visible the solution of the puzzle should be
@SuppressLint("ResourceType")
class GenerateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate)
        applyPuzzleData()
        initPlayer()
        startPuzzleGame()
    }

    // Author: Mischa Jegerlehner
    // Goes to the next activity where the user puts the puzzle together
    private fun startPuzzleActivity() {
        startActivity(Intent(this, PuzzleActivity::class.java))
        finish()
    }

    // Author: Mischa Jegerlehner
    // Finalizes the current settings, adds the new player to the list in
    // the GameData class if the specified name is not already in it, this function
    // makes also sure that the puzzle cannot be below 3x3 to prevent visual glitches
    private fun startPuzzleGame() {
        btn_start.setOnClickListener {
            var isTrue = true
            if(num_xcuts.text.toString().trim().isEmpty() || num_ycuts.text.toString().trim().isEmpty() || num_opacity.text.toString().trim().isEmpty()) {
                Snackbar.make(generate_puzzle_constraint, "No empty Values allowed!", Snackbar.LENGTH_SHORT).show()
                val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.error_sound)
                clickSound.setVolume(0.2f, 0.2f)
                clickSound.start()
                isTrue = false
            }
            if(num_xcuts.text.toString().trim().isEmpty()) {
                num_xcuts.text = Editable.Factory.getInstance().newEditable("4")
            }
            if(num_ycuts.text.toString().trim().isEmpty()) {
                num_ycuts.text = Editable.Factory.getInstance().newEditable("3")
            }
            if(num_opacity.text.toString().trim().isEmpty()) {
                num_opacity.text = Editable.Factory.getInstance().newEditable("50")
            }
            if(num_xcuts.text.toString().toInt() < 3 || num_ycuts.text.toString().toInt() < 3) {
                Snackbar.make(generate_puzzle_constraint, "Your Puzzle must be at least 3x3!", Snackbar.LENGTH_SHORT).show()
                val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.error_sound)
                clickSound.setVolume(0.2f, 0.2f)
                clickSound.start()
                isTrue = false
            }
            if(isTrue){
                GameData.currentPlayer.playerName = txt_player_name_input.text.toString()
                GameData.puzzleCutsX = Integer.parseInt(num_xcuts.text.toString())
                GameData.puzzleCutsY = Integer.parseInt(num_ycuts.text.toString())
                GameData.originalAlpha = Integer.parseInt(num_opacity.text.toString())
                val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.confirm_sound_b)
                clickSound.setVolume(0.1f, 0.1f)
                clickSound.start()
                Handler().postDelayed({
                    startPuzzleActivity()
                }, 250)
            }
        }
    }

    // Author: Mischa Jegerlehner
    // Applies the default values from the GameData class to the labels
    private fun applyPuzzleData() {
        num_xcuts.text = Editable.Factory.getInstance().newEditable(
                GameData.puzzleCutsX.toString()
        )
        num_ycuts.text = Editable.Factory.getInstance().newEditable(
                GameData.puzzleCutsY.toString()
        )
        num_opacity.text = Editable.Factory.getInstance().newEditable(
                GameData.originalAlpha.toString()
        )
    }

    // Author: Mischa Jegerlehner
    // Creates the default player and sets the default value in the lable
    private fun initPlayer() {
        txt_player_name_input.text = Editable.Factory.getInstance().newEditable(
                GameData.currentPlayer.playerName
        )
    }
}
