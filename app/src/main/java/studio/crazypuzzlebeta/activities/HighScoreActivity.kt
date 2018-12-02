package studio.crazypuzzlebeta.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_highscore.*
import studio.crazypuzzlebeta.GameData
import studio.crazypuzzlebeta.ImagingTools
import studio.crazypuzzlebeta.Player
import studio.crazypuzzlebeta.R
import java.lang.Exception

// Author Mischa Jegerlehner
// This is the last activity of this app, when the user finishes his puzzle
// game he will be redirected here and will be displayed in the ranks if he
// manages to be in the top five of the fastest players
@SuppressLint("SetTextI18n")
class HighScoreActivity : AppCompatActivity() {
    // Author: Mischa Jegerlehner
    // This is the string that hold our game data which we would pass on to the database
    private var highscoreData: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)
        GameData.currentPlayer = Player()
        resetAllTextViews()
        assignPlayerRank()
        returnToMainMenu()
    }

    // Author Mischa Jegerlehner (depricated!)
    // Has been implemented but commented out.
    // This function/button conflicted with the MusicService Class and had to be removed
    private fun returnToMainMenu() {
        btn_main_menu.setOnClickListener {
        //    stopService(Intent(this, MusicService::class.java))
        //    startActivity(Intent(this, StartActivity::class.java))
        //    finish()
        }
    }

    // Author Mischa Jegerlehner
    // Here we just reset all highscore labels so that they are blank in the begninning
    // This function has a lot of redundancy and might be in need of optimization
    private fun resetAllTextViews() {
        txt_place1.text = "1.    "
        txt_place1_total.text = "Total Time:           "
        txt_place1_average.text = "Average Speed:         "
        txt_place2.text = "2.    "
        txt_place2_total.text = "Total Time:           "
        txt_place2_average.text = "Average Speed:         "
        txt_place3.text = "3.    "
        txt_place3_total.text = "Total Time:           "
        txt_place3_average.text = "Average Speed:         "
        txt_place4.text = "4.    "
        txt_place4_total.text = "Total Time:           "
        txt_place4_average.text = "Average Speed:         "
        txt_place5.text = "5.    "
        txt_place5_total.text = "Total Time:           "
        txt_place5_average.text = "Average Speed:         "
    }

    // Author Mischa Jegerlehner
    // In this function we set the highscores labels of the top five players
    // at the same time we are preparing the data to be saved in the database.
    // This function has a lot of redundancy and might be in need of optimization
    private fun assignPlayerRank() {
        GameData.players.sortBy { player: Player -> player.currentTotalTime }
        try {
            txt_place1.text = "1.    " + GameData.players[0].playerName
            txt_place1_total.text = "Total Time:           " + GameData.players[0].currentTotalTime
            txt_place1_average.text = "Average Speed:         " + GameData.players[0].currentAverageTime
            place1_puzzle_uri.setImageBitmap(GameData.players[0].bitmap)
            highscoreData = highscoreData + ";" +
                    GameData.players[0].playerName + ";" +
                    GameData.players[0].currentTotalTime + ";" +
                    GameData.players[0].currentAverageTime + ";" +
                    ImagingTools.makeStringifiedByteArray(GameData.players[0].bitmap) + ";"
        } catch (ex: Exception) {
            ex.toString()
        }
        try {
            txt_place2.text = "2.    " + GameData.players[1].playerName
            txt_place2_total.text = "Total Time:           " + GameData.players[1].currentTotalTime
            txt_place2_average.text = "Average Speed:         " + GameData.players[1].currentAverageTime
            place2_puzzle_uri.setImageBitmap(GameData.players[1].bitmap)
            highscoreData = highscoreData + ";" +
                    GameData.players[1].playerName + ";" +
                    GameData.players[1].currentTotalTime + ";" +
                    GameData.players[1].currentAverageTime + ";" +
                    ImagingTools.makeStringifiedByteArray(GameData.players[1].bitmap) + ";"
        } catch (ex: Exception) {
            ex.toString()
        }
        try {
            txt_place3.text = "3.    " + GameData.players[2].playerName
            txt_place3_total.text = "Total Time:           " + GameData.players[2].currentTotalTime
            txt_place3_average.text = "Average Speed:         " + GameData.players[2].currentAverageTime
            place3_puzzle_uri.setImageBitmap(GameData.players[2].bitmap)
            highscoreData = highscoreData + ";" +
                    GameData.players[2].playerName + ";" +
                    GameData.players[2].currentTotalTime + ";" +
                    GameData.players[2].currentAverageTime + ";" +
                    ImagingTools.makeStringifiedByteArray(GameData.players[2].bitmap) + ";"
        } catch (ex: Exception) {
            ex.toString()
        }
        try {
            txt_place4.text = "4.    " + GameData.players[3].playerName
            txt_place4_total.text = "Total Time:           " + GameData.players[3].currentTotalTime
            txt_place4_average.text = "Average Speed:         " + GameData.players[3].currentAverageTime
            place4_puzzle_uri.setImageBitmap(GameData.players[3].bitmap)
            highscoreData = highscoreData + ";" +
                    GameData.players[3].playerName + ";" +
                    GameData.players[3].currentTotalTime + ";" +
                    GameData.players[3].currentAverageTime + ";" +
                    ImagingTools.makeStringifiedByteArray(GameData.players[3].bitmap) + ";"
        } catch (ex: Exception) {
            ex.toString()
        }
        try {
            txt_place5.text = "5.    " + GameData.players[4].playerName
            txt_place5_total.text = "Total Time:           " + GameData.players[4].currentTotalTime
            txt_place5_average.text = "Average Speed:         " + GameData.players[4].currentAverageTime
            place5_puzzle_uri.setImageBitmap(GameData.players[4].bitmap)
            highscoreData = highscoreData + ";" +
                    GameData.players[4].playerName + ";" +
                    GameData.players[4].currentTotalTime + ";" +
                    GameData.players[4].currentAverageTime + ";" +
                    ImagingTools.makeStringifiedByteArray(GameData.players[4].bitmap) + ";"
        } catch (ex: Exception) {
            ex.toString()
        }
    }
}
