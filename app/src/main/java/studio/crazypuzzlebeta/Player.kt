package studio.crazypuzzlebeta

import android.graphics.Bitmap

// Author: Mischa Jegerlehner
// This class represents a player inside the puzzle game
class Player {
    // Author: Mischa Jegerlehner
    // This variable is holding the player's name
    public var playerName: String = "Test Player"

    // This variable hold the time this player needed to complete a puzzle
    public var currentTotalTime: Int = 0

    // This variable holds the speed with which the player placed each puzzle piece
    public var currentAverageTime: Int = 0

    // This holds the bitmap of the image currently used as puzzle template
    public lateinit var bitmap: Bitmap
}