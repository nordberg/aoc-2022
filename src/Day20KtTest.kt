import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day20KtTest {
    @Test
    fun wrapAroundInsideIndexLimitMovingUp() {
        val index = 3
        val indexLimit = 100
        val expected = 4
        val actual = wrapAroundIndex(index, indexLimit, true)
        assertEquals(expected, actual)
    }

    @Test
    fun wrapAroundExceedsLimitMovingUp() {
        val index = 4
        val indexLimit = 3
        val expected = 1
        val actual = wrapAroundIndex(index, indexLimit, true)
        assertEquals(expected, actual)
    }

    @Test
    fun wrapAroundExceedsLimitMultipleTimes() {
        val index = 8
        val indexLimit = 3
        val expected = 3
        val actual = wrapAroundIndex(index, indexLimit, true)
        assertEquals(expected, actual)
    }

    @Test
    fun wrapAroundIndexLowerThan0() {
        val index = -1
        val indexLimit = 3
        val expected = 2
        val actual = wrapAroundIndex(index, indexLimit, false)
        assertEquals(expected, actual)
    }

    @Test
    fun wrapAroundIndexLowerThan0MultipleTimes() {
        val index = -6
        val indexLimit = 3
        val expected = 2
        val actual = wrapAroundIndex(index, indexLimit, false)
        assertEquals(expected, actual)
    }

    @Test
    fun test1000th() {
        val index = 1004
        val indexLimit = 6
        val expected = 3
        val actual = wrapAroundIndex(index, indexLimit, true)
        assertEquals(expected, actual)
    }

    @Test
    fun test2000th() {
        val index = 2004
        val indexLimit = 6
        val expected = 2
        val actual = wrapAroundIndex(index, indexLimit, true)
        assertEquals(expected, actual)
    }

    @Test
    fun test3000th() {
        val index = 3004
        val indexLimit = 6
        val expected = 1
        val actual = wrapAroundIndex(index, indexLimit, true)
        assertEquals(expected, actual)
    }
}