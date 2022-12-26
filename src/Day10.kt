fun main() {
    fun addInterestingSignals(cycleCount: Int, signalStrength: Int): Int {
        return when (cycleCount) {
            20, 60, 100, 140, 180, 220 -> signalStrength * cycleCount
            else -> 0
        }
    }

    fun part1(input: List<String>): Int {
        var signalStrength = 1
        var cycleCount = 0
        var interestingSignal = 0

        input.forEach { line ->
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

        return interestingSignal
    }

    fun part2(input: List<String>): String {
        var signalStrength = 1
        val spriteLocations = mutableListOf(listOf(0, 1, 2))
        var cycleCount = 0
        var interestingSignal = 0

        input.forEach { line ->
            if (line == "noop") {
                cycleCount += 1
                spriteLocations.add(listOf(signalStrength - 1, signalStrength, signalStrength + 1))
                interestingSignal += addInterestingSignals(cycleCount, signalStrength)
            } else {
                val (_, addVal) = line.split(" ")
                cycleCount += 1
                spriteLocations.add(listOf(signalStrength - 1, signalStrength, signalStrength + 1))
                interestingSignal += addInterestingSignals(cycleCount, signalStrength)
                cycleCount += 1
                interestingSignal += addInterestingSignals(cycleCount, signalStrength)
                signalStrength += addVal.toInt()
                spriteLocations.add(listOf(signalStrength - 1, signalStrength, signalStrength + 1))
            }
        }

        val sb = StringBuilder(40*6)
        (1..(40*6)).forEach { atCycle ->
            if (spriteLocations[atCycle].contains(atCycle % 40)) {
                sb.append('#')
            } else {
                sb.append('.')
            }
            if (atCycle % 40 == 0) {
                sb.appendLine()
            }
        }

        return sb.toString()
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day10_test")
    //check(part1(testInput) == 13140)

    val input = readInput("Day10")
    //println(part1(input))
    println(part2(input))
}
