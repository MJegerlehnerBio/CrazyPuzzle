package studio.crazypuzzlebeta.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_puzzle.*
import studio.crazypuzzlebeta.*
import java.lang.Math.*
import java.util.*

// Author: Mischa Jegerlehner
// In this class we enter the actual puzzle game, here the user will drag and drop
// puzzle pieces into the right spot. He can keep track of his chosen name, his spent time,
// as well as his speed on via the labels at the top. When the puzzle is finished the
// game will applaude you and automatically continue to the highscore screen
@SuppressLint("SetTextI18n")
class PuzzleActivity : AppCompatActivity() {
    // Author: Mischa Jegerlehner
    // This is the array of all puzzle pieces
    private var puzzlePiecesArray: ArrayList<PuzzlePiece> = ArrayList()

    // This boolean determines when the game puzzle has been completed
    private var puzzleComplete = false

    // These two variables refer to the amount of puzzle pieces we will have
    private var cutsx = 4
    private var cutsy = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)
        resetAndStartTimer()
        applyPuzzleSessionProperties()
        postImageView()
    }

    // Author: Mischa Jegerlehner
    // This function will lead the user to the final activity wher he can see the highscore
    private fun startHighscoreAcitivity() {
        startActivity(Intent(this, HighScoreActivity::class.java))
        finish()
    }

    // Author: Mischa Jegerlehner
    // This function contains the post method, which enables the drag and drop functionality
    private fun postImageView() {
        imgv_puzzle.post {
            fillPuzzlePieceObjectArray()
            onTouchMovePuzzlePieceObjects()
        }
    }

    // Author: Mischa Jegerlehner
    // Here we reset the stats of the current player and set the labels accordingly
    private fun resetAndStartTimer() {
        GameData.currentPlayer.currentTotalTime = 0
        GameData.currentPlayer.currentAverageTime = 0
        txt_timer.text = "Time: " + GameData.currentPlayer.currentTotalTime.toString()
        txt_average.text = "Average Speed: " + GameData.currentPlayer.currentAverageTime.toString()
        runTimer()
    }

    // Author: Mischa Jegerlehner
    // With this function we emulate a time through a recursive pattern
    private fun runTimer() {
        if(!puzzleComplete) {
            Handler().postDelayed({
                GameData.currentPlayer.currentTotalTime++
                txt_timer.text = "Time: " + GameData.currentPlayer.currentTotalTime.toString()
                runTimer()
            }, 1000)
        }
    }

    // Author: Mischa Jegerlehner
    // Here we tell the game how the puzzle has been configured by the user
    private fun applyPuzzleSessionProperties() {
        txt_player_name.text = GameData.currentPlayer.playerName
        imgv_puzzle.setImageBitmap(GameData.bitmap)
        cutsx = GameData.puzzleCutsX
        cutsy = GameData.puzzleCutsY
        imgv_puzzle.alpha = GameData.originalAlpha /100f
    }

    // Author: Mischa Jegerlehner
    // This function contains the touchListener for each puzzle piece and additionally
    // makes the calculation to position the puzzle pieces in the begining of the game
    @SuppressLint("ClickableViewAccessibility")
    private fun onTouchMovePuzzlePieceObjects() {
        for (piece: PuzzlePiece in puzzlePiecesArray) {
            piece.imageView.setOnTouchListener { view: View, event: MotionEvent ->
                movePuzzlePiece(view, event, piece)
                true
            }
            layout.addView(piece.imageView)
            val lParams = piece.imageView.layoutParams as RelativeLayout.LayoutParams
            lParams.leftMargin = Random().nextInt((layout.width/1.5).toInt() - piece.width)
            lParams.topMargin = layout.height - piece.height
            piece.imageView.layoutParams = lParams
        }
    }

    // Author: Mischa Jegerlehner
    // These mathematical functionalities calculate the new position of each puzzle
    // according to the touch position, the puzzle piece properties an the layoutParameters
    private fun movePuzzlePiece(view: View, event: MotionEvent, puzzlePiece: PuzzlePiece) {
        val pressedX: Float = event.rawX
        val pressedY: Float = event.rawY
        val tolerance: Double = getSnapTolerance(view)
        val layoutParams: RelativeLayout.LayoutParams = view.layoutParams as RelativeLayout.LayoutParams
        val leftMargin: Int = layoutParams.leftMargin
        val topMargin: Int = layoutParams.topMargin
        if(event.action == MotionEvent.ACTION_DOWN) {
            puzzlePiece.imageView.bringToFront()
            puzzlePiece.leftMargin = pressedX - leftMargin
            puzzlePiece.topMargin = pressedY - topMargin
        }
        if(event.action == MotionEvent.ACTION_MOVE) {
            layoutParams.leftMargin = (pressedX - puzzlePiece.leftMargin).toInt()
            layoutParams.topMargin = (pressedY - puzzlePiece.topMargin).toInt()
            view.layoutParams = layoutParams
        }
        if(event.action == MotionEvent.ACTION_UP) {
            puzzlePiece.atOrigin = false
            val xDiff = abs(puzzlePiece.originLeftMargin- layoutParams.leftMargin)
            val yDiff = abs(puzzlePiece.originTopMargin - layoutParams.topMargin)
            if (xDiff <= tolerance && yDiff <= tolerance) {
                layoutParams.leftMargin = puzzlePiece.originLeftMargin.toInt()
                layoutParams.topMargin = puzzlePiece.originTopMargin.toInt()
                puzzlePiece.atOrigin = true
            }
        }
        onPuzzleComplete()
    }

    // Author: Mischa Jegerlehner
    // In this function we check if the puzzle has been completed, if yes we continue
    // to the last screen where the user can see the highscore, if no we simply update
    // all stats and properties and the game continues without any disturbance
    private fun onPuzzleComplete() {
        puzzleComplete = true
        var size = 0
        for (piece: PuzzlePiece in puzzlePiecesArray) {
            if(!piece.atOrigin) {
                puzzleComplete = false
            } else {
                size++
            }
        }
        if(size != 0) {
            GameData.currentPlayer.currentAverageTime = (GameData.currentPlayer.currentTotalTime / size)
        }
        txt_average.text = "Average Speed: " + GameData.currentPlayer.currentAverageTime.toString()
        if(puzzleComplete) {
            GameData.currentPlayer.bitmap = GameData.bitmap
            GameData.players.add(GameData.currentPlayer)
            for (piece: PuzzlePiece in puzzlePiecesArray) {
                piece.isActive(false)
            }
            imgv_solve_the_puzzle.alpha = 0.0f
            imgv_congratulations.alpha = 1.0f
            val clickSound: MediaPlayer = MediaPlayer.create(this, R.raw.applause_sound)
            clickSound.setVolume(0.2f, 0.2f)
            clickSound.start()
            Handler().postDelayed({
                puzzleComplete = false
                startHighscoreAcitivity()
            }, 6500)
        }
    }

    // Author: Mischa Jegerlehner
    // In this function the Objekt that represents the puzzle piece is being
    // prepared for the game, which in this cas is the picture and shuffeling the array
    private fun fillPuzzlePieceObjectArray() {
        val drawable: BitmapDrawable = imgv_puzzle.drawable as BitmapDrawable
        val bitmapTest: Bitmap = drawable.bitmap
        if(ValidityTools.checkImageSize(bitmapTest.width, bitmapTest.height)) {
            puzzlePiecesArray = ImagingTools.splitToPuzzleObjects(cutsx, cutsy, imgv_puzzle)
            for (piece: PuzzlePiece in puzzlePiecesArray) {
                val imagePiece = ImageView(applicationContext)
                imagePiece.setImageBitmap(piece.bitmap)
                piece.imageView = imagePiece
            }
            puzzlePiecesArray.shuffle()
        }
    }

    // Author: Mischa Jegerlehner
    // This snipped makes sure that when the user moves the puzzle piece close enought to
    // the spot where it's supposed to be then the puzzle piece will snap into place
    private fun getSnapTolerance(view: View): Double {
        return sqrt(pow(view.width.toDouble(), 2.0) + pow(view.height.toDouble(), 2.0)) / 10
    }
}
