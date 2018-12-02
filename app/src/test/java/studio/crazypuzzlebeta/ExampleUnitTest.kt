package studio.crazypuzzlebeta

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun checkImageSize_isValid() {
        assertEquals(false, ValidityTools.checkImageSize(400, 399))
        assertEquals(false, ValidityTools.checkImageSize(399, 400))
        assertEquals(true, ValidityTools.checkImageSize(400, 400))
        assertEquals(true, ValidityTools.checkImageSize(500, 500))
        assertEquals(true, ValidityTools.checkImageSize(500, 600))
        assertEquals(true, ValidityTools.checkImageSize(600, 500))
        assertEquals(true, ValidityTools.checkImageSize(2000, 2000))
        assertEquals(false, ValidityTools.checkImageSize(2001, 2000))
        assertEquals(false, ValidityTools.checkImageSize(2000, 2001))
    }
}
