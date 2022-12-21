import kotlin.math.sign

fun main() {
    fun addInterestingSignals(cycleCount: Int, signalStrength: Int): Int {
        return when (cycleCount) {
            20 -> {
                println("$cycleCount: $signalStrength")
                signalStrength * cycleCount
            }
            60 -> {
                println("$cycleCount: $signalStrength")
                signalStrength * cycleCount
            }
            100 -> {
                println("$cycleCount: $signalStrength")
                signalStrength * cycleCount
            }
            140 -> {
                println("$cycleCount: $signalStrength")
                signalStrength * cycleCount
            }
            180 -> {
                println("$cycleCount: $signalStrength")
                signalStrength * cycleCount
            }
            220 -> {
                println("$cycleCount: $signalStrength")
                signalStrength * cycleCount
            }
            else -> 0
        }
    }
    fun part1(input: List<String>): Int {
        var signalStrength = 1
        var cycleCount = 0
        var interestingSignal = 0

        for ((index, line) in input.iterator().withIndex()) {
            println("$index: $line ($cycleCount: $signalStrength)")
            if (line == "noop") {
                cycleCount += 1
                interestingSignal += addInterestingSignals(cycleCount, signalStrength)
            } else {
                val (_, addVal) = line.split(" ")
                cycleCount += 1
                interestingSignal += addInterestingSignals(cycleCount, signalStrength)
                cycleCount += 1
                interestingSignal += addInterestingSignals(cycleCount, signalStrength)
                signalStrength += addVal.toInt()
            }
        }

        println(interestingSignal)
        return interestingSignal
    }

    fun part2(input: List<String>): Int {
        return 5
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
