package studio.crazypuzzlebeta

// Author: Mischa Jegerlehner
// This class will be used for unit tests
class ValidityTools {
    companion object {
        // Author: Mischa Jegerlehner
        // This is a simple unit test that checks if the program can recognize the allowed
        // formats. After A / B testing the maxWidth and maxHeight had to be increased to
        // 4000 pixels because newer phones can make very high-res pictures and to have the
        // limit at only 2000 showed to be very inconvenient for the user
        public fun checkImageSize(width: Int, height: Int): Boolean {
            val minWidth = 400
            val minHeight = 400
            val maxWidth = 4000
            val maxHeight = 4000
            val minSizeValid: Boolean = width >= minWidth && height >= minHeight
            val maxSizeValid: Boolean = width <= maxWidth && height <= maxHeight

            return minSizeValid && maxSizeValid
        }
    }
}