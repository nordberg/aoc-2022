import kotlin.math.max

fun main() {
    fun getOperation(op: Char): (Long, Long) -> Long {
        return when (op) {
            '/' -> { x: Long, y: Long -> x / y }
            '*' -> { x: Long, y: Long -> x * y }
            '+' -> { x: Long, y: Long -> x + y }
            '-' -> { x: Long, y: Long -> x - y }
            else -> throw IllegalStateException("Got $op as operation")
        }
    }

    fun part1(input: List<String>): Long {
        val monkeysWithNums = mutableMapOf<String, Long?>()
        val monkeyNames = mutableSetOf<String>()
        input.forEach {
            val (monkeyName, monkeyOp) = it.split(": ")
            monkeyNames.add(monkeyName)
            if (monkeyOp.first().isDigit()) {
                monkeysWithNums[monkeyName] = monkeyOp.toLong()
            }
        }

        var sizeForMonkeysWithNums = monkeysWithNums.keys.size
        while (monkeysWithNums.keys != monkeyNames) {
            println("${monkeysWithNums.keys.size}/${monkeyNames.size} ")
            input.forEach {
                val (monkeyName, monkeyOp) = it.split(": ")
                val operation = monkeyOp.split(" ")
                if (operation.size == 3) {
                    if (monkeysWithNums.containsKey(operation[0]) && monkeysWithNums.containsKey(operation[2])) {
                        val applyOperation = getOperation(operation[1].first())
                        val numForMonkey1 = monkeysWithNums[operation[0]]!!
                        val numForMonkey2 = monkeysWithNums[operation[2]]!!
                        val numForNewMonkey = applyOperation(numForMonkey1, numForMonkey2)
                        monkeysWithNums[monkeyName] = numForNewMonkey
                    }
                }
            }

            check(monkeysWithNums.keys.size > sizeForMonkeysWithNums) {
                "Monkey with nums stopped growing"
            }
        }

        println(monkeysWithNums)
        return 5
    }

    fun part2(input: List<String>): Long {
        return 5
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day21")
    println(part1(input))
    //println(part2(input))
}
