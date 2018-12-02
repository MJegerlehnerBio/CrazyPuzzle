package studio.crazypuzzlebeta.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_upload.*
import studio.crazypuzzlebeta.GameData
import studio.crazypuzzlebeta.R


// Author: Michael Rentka
// Additions: Mischa Jegerlehner
// In this class the user is able to chose an image from a location of his choosing
// when he is satisfied with his selection he can proceed with the puzzle generation
class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        uploadPicture()
        generatePuzzle()
    }

    // Author: Mischa Jegerlehner
    // This function starts the puzzle generation screen
    private fun startGenerateActivity() {
        startActivity(Intent(this, GenerateActivity::class.java))
        randomName()
    }

    // Author: Michael Rentka
    // This function keeps track of the users interaction with the gallery interface, once
    // the user has chosen a picture this function will display the picture in the imageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GameData.IMAGE_REQUESTED && resultCode == Activity.RESULT_OK) {
            imgview_display.setImageURI(data?.data)
            imgview_display.visibility = View.VISIBLE
            val drawable = imgview_display.drawable as BitmapDrawable
            val imageViewBitmap = drawable.bitmap
            GameData.bitmap = imageViewBitmap
            togglePreviewText()
        }
    }

    // Autor: Michael Rentka
    // Opens File Browser where the user chooses a picture for his puzzle session
    private fun uploadPicture() {
        btn_upload.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, GameData.IMAGE_REQUESTED)
            val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.itemize_sound)
            clickSound.setVolume(0.1f, 0.1f)
            clickSound.start()
        }
    }

    // Author: Michael Rentka
    // This function will make sure that the user has to pick an image first
    // before he is able to continue to the puzzle generation process
    private fun generatePuzzle() {
        btn_create_puzzle.setOnClickListener {
            if(imgview_display.visibility == View.VISIBLE) {
                startGenerateActivity()
                val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.confirm_sound)
                clickSound.setVolume(0.1f, 0.1f)
                clickSound.start()
            } else {
                Snackbar.make(constraintLayout, R.string.snackbar_image_not_uploaded, Snackbar.LENGTH_SHORT).show()
                val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.error_sound)
                clickSound.setVolume(0.2f, 0.2f)
                clickSound.start()
            }
        }
    }

    // Autor Michael Rentka
    // This method will apply the chosen picture to the imageView as soon as it has been picked
    private fun togglePreviewText() {
        if(imgview_display.visibility == View.VISIBLE) {
            lbl_preview.visibility = View.INVISIBLE
            val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.jingle_sound)
            clickSound.setVolume(0.2f, 0.2f)
            clickSound.start()
        } else {
            lbl_preview.visibility = View.INVISIBLE
        }
    }

    // Author: Mischa Jegerlehner
    // This method provides the user with a few random names in case he cannot come up with
    // one on his own, makes a fast game easier because you don't have to change the name
    private fun randomName(){
        var randomName = ""
        val randomIndex = GameData.random(0, 10)
        if(randomIndex == 0) {
            randomName = "Davison"
        }
        if(randomIndex == 1) {
            randomName = "Whiteford"
        }
        if(randomIndex == 2) {
            randomName = "Phillips"
        }
        if(randomIndex == 3) {
            randomName = "Chambers"
        }
        if(randomIndex == 4) {
            randomName = "Lambert"
        }
        if(randomIndex == 5) {
            randomName = "Atkinson"
        }
        if(randomIndex == 6) {
            randomName = "Adams"
        }
        if(randomIndex == 7) {
            randomName = "Peterson"
        }
        if(randomIndex == 8) {
            randomName = "Edwards"
        }
        if(randomIndex == 9) {
            randomName = "Brewer"
        }
        if(randomIndex == 10) {
            randomName = "Jacobs"
        }
        GameData.currentPlayer.playerName = randomName
    }
}
