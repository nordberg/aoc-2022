import kotlin.math.max
import kotlin.math.min

fun main() {
    fun isCompassing(low1: Int, high1: Int, low2: Int, high2: Int): Boolean {
        if (low1 <= low2 && high1 >= high2) {
            return true;
        }

        if (low2 <= low1 && high2 >= high1) {
            return true;
        }

        return false;
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (firstRange, secondRange) = it.split(",")
            val (low1, high1) = firstRange.split("-")
            val (low2, high2) = secondRange.split("-")
            if (isCompassing(low1.toInt(), high1.toInt(), low2.toInt(), high2.toInt())) {
                1 as Int
            } else {
                0 as Int
            }
        }
    }

    fun isCompassingPart2(low1: Int, high1: Int, low2: Int, high2: Int): Boolean {
        val biggestLow = max(low1, low2)
        val lowestHigh = min(high1, high2)
        return biggestLow <= lowestHigh
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (firstRange, secondRange) = it.split(",")
            val (low1, high1) = firstRange.split("-")
            val (low2, high2) = secondRange.split("-")
            if (isCompassingPart2(low1.toInt(), high1.toInt(), low2.toInt(), high2.toInt())) {
                1 as Int
            } else {
                0 as Int
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
