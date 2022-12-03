fun main() {
    val perms = listOf(
        "ABC",
//        "ACB",
//        "CAB",
//        "BAC",
//        "BCA",
//        "CBA"
    )

    fun rockPaperScissors(oppC: Char, myC: Char): Int {
        return when {
            oppC == myC -> 3

            oppC == 'A' && myC == 'C' -> 0
            oppC == 'B' && myC == 'A' -> 0
            oppC == 'C' && myC == 'B' -> 0

            oppC == 'C' && myC == 'A' -> 6
            oppC == 'A' && myC == 'B' -> 6
            oppC == 'B' && myC == 'C' -> 6

            else -> throw IllegalStateException("$myC vs $oppC")
        }
    }

    fun mapping(c: Char, perm: String): Char {
        return when (c) {
            'X' -> perm[0]
            'Y' -> perm[1]
            'Z' -> perm[2]
            else -> throw IllegalStateException("Got $c")
        }
    }

    fun pointsForRockPaperScissor(c: Char): Int {
        return when (c) {
            'A' -> 1
            'B' -> 2
            'C' -> 3
            else -> throw IllegalStateException("Got $c")
        }
    }

    /**
     * Misread the instructions, thought I had to find the best mapping (XYZ) -> (ABC) to maximize points...
     */
    fun part1(input: List<String>): Int? {
        return perms.maxOfOrNull { p ->
            input.sumOf { s ->
                val (oppMoveStr, myMoveStr) = s.split(" ")
                val myMove = mapping(myMoveStr[0], p)
                rockPaperScissors(oppMoveStr[0], myMove) + pointsForRockPaperScissor(myMove)
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (oppMove, myMove) = it.split(" ")
            val scoreFromOutcome = when (myMove[0]) {
                'X' -> 0
                'Y' -> 3
                'Z' -> 6
                else -> throw IllegalStateException("aaa")
            }

            val scoreFromWhatPlayed = when (oppMove[0]) {
                'A' -> when (scoreFromOutcome) {
                    0 -> 3  // I lost -> I played scissors
                    3 -> 1  // I drew -> I played rock
                    6 -> 2  // I won -> I played paper
                    else -> throw java.lang.IllegalStateException(":((")
                }
                'B' -> when (scoreFromOutcome) {
                    0 -> 1  // I lost -> I played scissors
                    3 -> 2  // I drew -> I played rock
                    6 -> 3  // I won -> I played paper
                    else -> throw java.lang.IllegalStateException(":((")
                }
                'C' -> when (scoreFromOutcome) {
                    0 -> 2  // I lost -> I played scissors
                    3 -> 3  // I drew -> I played rock
                    6 -> 1  // I won -> I played paper
                    else -> throw java.lang.IllegalStateException(":((")
                }
                else -> throw IllegalStateException(":(")
            }
            scoreFromOutcome + scoreFromWhatPlayed
        }
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
