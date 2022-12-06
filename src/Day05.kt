fun main() {
    fun part1(input: List<String>): String {
//        [P]     [L]         [T]
//        [L]     [M] [G]     [G]     [S]
//        [M]     [Q] [W]     [H] [R] [G]
//        [N]     [F] [M]     [D] [V] [R] [N]
//        [W]     [G] [Q] [P] [J] [F] [M] [C]
//        [V] [H] [B] [F] [H] [M] [B] [H] [B]
//        [B] [Q] [D] [T] [T] [B] [N] [L] [D]
//        [H] [M] [N] [Z] [M] [C] [M] [P] [P]
//        1   2   3   4   5   6   7   8   9
        val piles = mutableListOf(
            mutableListOf('H', 'B', 'V', 'W', 'N', 'M', 'L', 'P'),
            mutableListOf('M', 'Q', 'H'),
            mutableListOf('N', 'D', 'B', 'G', 'F', 'Q', 'M', 'L'),
            mutableListOf('Z', 'T', 'F', 'Q', 'M', 'W', 'G'),
            mutableListOf('M', 'T', 'H', 'P'),
            mutableListOf('C', 'B', 'M', 'J', 'D', 'H', 'G', 'T'),
            mutableListOf('M', 'N', 'B', 'F', 'V', 'R'),
            mutableListOf('P', 'L', 'H', 'M', 'R', 'G', 'S'),
            mutableListOf('P', 'D', 'B', 'C', 'N')
        )

        input.forEach {
            val (numToMove, fromPile, toPile) = it.split(";").map { z -> z.toInt() }
            for (i in 1..numToMove) {
                piles[toPile - 1].add(piles[fromPile - 1].removeLast())
            }
        }
        return piles.map { it.last() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val piles = mutableListOf(
            listOf('H', 'B', 'V', 'W', 'N', 'M', 'L', 'P'),
            listOf('M', 'Q', 'H'),
            listOf('N', 'D', 'B', 'G', 'F', 'Q', 'M', 'L'),
            listOf('Z', 'T', 'F', 'Q', 'M', 'W', 'G'),
            listOf('M', 'T', 'H', 'P'),
            listOf('C', 'B', 'M', 'J', 'D', 'H', 'G', 'T'),
            listOf('M', 'N', 'B', 'F', 'V', 'R'),
            listOf('P', 'L', 'H', 'M', 'R', 'G', 'S'),
            listOf('P', 'D', 'B', 'C', 'N')
        )

        input.forEach {
            val (numToMove, fromPile, toPile) = it.split(";").map { z -> z.toInt() }

            val fromThis = piles[fromPile - 1]

            piles[toPile - 1] += fromThis.takeLast(numToMove)
            piles[fromPile - 1] = fromThis.dropLast(numToMove)
        }

        return piles.map { it.last() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
