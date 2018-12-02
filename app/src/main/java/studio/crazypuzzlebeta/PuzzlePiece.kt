package studio.crazypuzzlebeta

import android.graphics.Bitmap
import android.widget.ImageView

// Author: Mischa Jegerlehner
// This class represents one single puzzle piece
class PuzzlePiece {
    // Author: Mischa Jegerlehner
    // This variable holds the original distance form the left side of the screen to the puzzle piece
    public var originLeftMargin: Float = 0.0f

    // This varibal holds the original distance from the top side of the screeb to the puzzle piece
    public var originTopMargin: Float = 0.0f

    // These two values determine the height and the with of this puzzle piece
    public var height: Int = 0
    public var width: Int = 0

    // This variable holds the new distance form the left side of the screen to the puzzle piece
    public var leftMargin: Float = 0.0f

    // This variable holds the new distance from the top side of the screeb to the puzzle piece
    public var topMargin: Float = 0.0f

    // This variable holds imageView for that specific puzzle piece
    public lateinit var imageView: ImageView

    // This variable holds bitmap for that specific puzzle piece
    public lateinit var bitmap: Bitmap

    // this variable tells if the puzzle piece is in the correct spot
    public var atOrigin: Boolean = false

    // Author: Mischa Jegerlehner
    // With this function we can toggle if the imageView should be enabled (movable) or not
    public fun isActive(active: Boolean) {
        this.imageView.isEnabled = active
    }
}