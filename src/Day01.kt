import java.lang.Integer.parseInt

fun main() {
    fun part1(input: List<String>): Int {
        var maxSoFar = Int.MIN_VALUE
        var currentCounter = 0
        input.forEach { s ->
            if (s.isEmpty()) {
                maxSoFar = maxSoFar.coerceAtLeast(currentCounter)
                currentCounter = 0
            } else {
                currentCounter += s.toInt()
            }
        }
        return maxSoFar
    }

    fun part2(input: List<String>): Int {
        val biggest = mutableListOf<Int>()

        var maxSoFar = Int.MIN_VALUE
        var currentCounter = 0
        input.forEach { s ->
            if (s.isEmpty()) {
                biggest += currentCounter
                currentCounter = 0
            } else {
                currentCounter += s.toInt()
            }
        }

        biggest.sortDescending()
        return biggest.take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
