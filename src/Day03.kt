fun main() {
    fun prio(c: Char): Int {
        val priority = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return priority.indexOf(c) + 1
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (firstHalf, secondHalf) = it.chunked(it.length / 2)
            println("$firstHalf $secondHalf")

            val intersection = firstHalf.toCharArray().intersect(secondHalf.toHashSet())
            prio(intersection.first())
        }
    }

    fun part2(input: List<String>): Int {
        val groupsOfThree = input.chunked(3)
        return groupsOfThree.sumOf {
            val firstIntersection = it[0].toCharArray().toHashSet() intersect it[1].toCharArray().toHashSet()
            val secondIntersection = it[2].toCharArray().toHashSet() intersect firstIntersection
            prio(secondIntersection.first())
        }
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day03")
    //println(part1(input))
    println(part2(input))
}
