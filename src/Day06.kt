fun main() {
    fun part1(input: List<String>): Int {
        val packetSize = 4
        return input.first().toCharArray().withIndex().windowed(packetSize)
            .first {
                it.map { t -> t.value }.toSet().size == packetSize
            }[0].index + packetSize
    }

    fun part2(input: List<String>): Int {
        val packetSize = 14
        return input.first().toCharArray().withIndex().windowed(packetSize)
            .first {
                it.map { t -> t.value }.toSet().size == packetSize
            }[0].index + packetSize
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
