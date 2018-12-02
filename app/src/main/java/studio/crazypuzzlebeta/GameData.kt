package studio.crazypuzzlebeta

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.util.*

// Author: Mischa Jegerlehner & Michael Rentka
// This class represents the game data while the game is running, while not implemented
// yet this class will also be responsible for making the GameData persistent. In the current
// scope of the project this has not been planned yet, but it has been prepared here
class GameData {
    companion object {
        // Author: Michael Rentka
        // This variable is used for the image upload to resolve the gallery pick
        public const val IMAGE_REQUESTED: Int = 101

        // Author: Mischa Jegerlehner
        // This variable is an array of all players in the game
        public val players: ArrayList<Player> = ArrayList()

        // This is a temporary value for the player that is currently playing
        public var currentPlayer: Player = Player()

        // This holds the bitmap of the image currently used as puzzle template
        public lateinit var bitmap: Bitmap

        // These two variables tell the game how many puzzle pieces to create for this session
        public var puzzleCutsX: Int = 4
        public var puzzleCutsY: Int = 3

        // This variable determines how visible the original image will be during the game
        public var originalAlpha: Int = 50

        // This is a variable that holds the file which we would use as database
        public lateinit var file : File

        // Author: Mischa Jegerlehner (unimplemented!)
        // Creates a directory and file which will server as our database
        public fun conectToDatabase(context: Context) {
            val letDirectory = File(context.filesDir, "LET")
            letDirectory.mkdirs()
            file = File(letDirectory, "database.db")
        }

        // Author: Mischa Jegerlehner (unimplemented!)
        // Whith this function we will be able to delete your saved highscores
        public fun clearDatabase() {
            file.writeText("")
        }

        // Author: Mischa Jegerlehner (unimplemented!)
        // With this function we can write new data to the database
        public fun writeToDatabase(data: String, append: Boolean) {
            if(!append) {
                file.writeText(data)
            } else {
                file.appendText(data)
            }
        }

        // Author: Mischa Jegerlehner (unimplemented!)
        // Small function that returns the contents of the database file
        public fun readFromDatabase(): String {
            return FileInputStream(file).bufferedReader().use {
                it.readText()
            }
        }

        // Author: Mischa Jegerlehner (unimplemented!)
        // This function will read the database.db file and assign the received properties
        // to the according elements within the game context
        public fun readAndWriteFromDatabase() {
            val inputAsString = FileInputStream(file).bufferedReader().use {
                it.readText()
            }
            try {
                GameData.players[0].playerName = inputAsString.split(";")[0]
                GameData.players[0].currentTotalTime = inputAsString.split(";")[1].toInt()
                GameData.players[0].currentAverageTime = inputAsString.split(";")[2].toInt()
                val byteArray = inputAsString.split(";")[3].toByteArray()
                GameData.players[0].bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (ex: Exception) {
                ex.toString()
            }
            try {
                GameData.players[1].playerName = inputAsString.split(";")[4]
                GameData.players[1].currentTotalTime = inputAsString.split(";")[5].toInt()
                GameData.players[1].currentAverageTime = inputAsString.split(";")[6].toInt()
                val byteArray = inputAsString.split(";")[7].toByteArray()
                GameData.players[1].bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (ex: Exception) {
                ex.toString()
            }
            try {
                GameData.players[2].playerName = inputAsString.split(";")[8]
                GameData.players[2].currentTotalTime = inputAsString.split(";")[9].toInt()
                GameData.players[2].currentAverageTime = inputAsString.split(";")[10].toInt()
                val byteArray = inputAsString.split(";")[11].toByteArray()
                GameData.players[2].bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (ex: Exception) {
                ex.toString()
            }
            try {
                GameData.players[3].playerName = inputAsString.split(";")[12]
                GameData.players[3].currentTotalTime = inputAsString.split(";")[13].toInt()
                GameData.players[3].currentAverageTime = inputAsString.split(";")[14].toInt()
                val byteArray = inputAsString.split(";")[15].toByteArray()
                GameData.players[3].bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (ex: Exception) {
                ex.toString()
            }
            try {
                GameData.players[4].playerName = inputAsString.split(";")[16]
                GameData.players[4].currentTotalTime = inputAsString.split(";")[17].toInt()
                GameData.players[4].currentAverageTime = inputAsString.split(";")[18].toInt()
                val byteArray = inputAsString.split(";")[19].toByteArray()
                GameData.players[4].bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            } catch (ex: Exception) {
                ex.toString()
            }
        }

        // Author: Mischa Jegerlehner
        // This is a utility function which returns a random integere within a specifiable range
        public fun random(from: Int, to: Int) : Int {
            return Random().nextInt(to - from) + from
        }
    }
}