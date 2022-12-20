typealias ApeId = Int

data class Event(
    val inspectedItem: Long,
    val throwerId: ApeId,
    val receiverId: ApeId
)

data class Ape(
    val id: ApeId,
    val items: List<Long>,
    val worryLevelInc: (Long) -> Long,
    val throwToTrue: ApeId,
    val throwToFalse: ApeId,
    val dividend: Long,
    val numItemsInspected: Int,
    val problemPart: Short
) {
    fun inspect(itemWorryLevel: Long): Long {
        return if (problemPart == 1.toShort()) {
            worryLevelInc(itemWorryLevel).floorDiv(3)
        } else {
            val new = worryLevelInc(itemWorryLevel)
            check(new > itemWorryLevel) {
                "Expected $new > $itemWorryLevel"
            }
            return new
        }
    }

    fun getRecipientOf(item: Long): ApeId {
        return if (item % dividend == 0L) {
            throwToTrue
        } else {
            throwToFalse
        }
    }
}

fun parseOperation(s: List<String>): (Long) -> Long {
    if (s.last() == "old") {
        return { x: Long -> x * x }
    }

    return when (s.first()) {
        "+" -> { x: Long -> x + s.last().toLong() }
        "*" -> { x: Long -> x * s.last().toLong() }
        else -> throw IllegalStateException("$s")
    }
}

fun parseApe(apeAsStr: String, problemPart: Short): Ape {
    val lines = apeAsStr.split("\n")
    val id = lines[0].split(" ")[1].dropLast(1).toInt()
    val startingItems = lines[1].dropWhile { !it.isDigit() }.split(", ").map { it.toLong() }.toList()
    val operation = parseOperation(lines[2].split(" ").takeLast(2))
    val divisibleBy = lines[3].split(" ").last().toInt()
    val throwToIfTestTrue = lines[4].split(" ").last().toInt()
    val throwToIfTestFalse = lines[5].split(" ").last().toInt()

    return Ape(
        id = id,
        items = startingItems,
        worryLevelInc = operation,
        throwToTrue = throwToIfTestTrue,
        throwToFalse = throwToIfTestFalse,
        numItemsInspected = 0,
        dividend = divisibleBy.toLong(),
        problemPart = problemPart
    )
}

fun List<Ape>.print(start: Int = 0, selectOnlyApesWithItems: Boolean) {
    this.drop(start).filter { it.items.isNotEmpty() && selectOnlyApesWithItems}.forEach {
        println("Ape ${it.id}: ${it.items.joinToString(", ")}")
    }
}

fun main() {
    /**
     * Generates all events one ape omits during one round
     */
    fun allEventsInOneRoundFor(ape: Ape): List<Event> {
        return ape.items.map { ape.inspect(it) }.map { inspectedItem ->
            Event(inspectedItem, ape.id, ape.getRecipientOf(inspectedItem))
        }
    }

    /**
     * Applies a list of events to a group of apes
     */
    operator fun List<Ape>.plus(events: List<Event>): List<Ape> {
        return this.map {
            val thrownItems = events
                .filter { e -> e.throwerId == it.id }
                .map { e -> e.inspectedItem }

            val itemsAfterThrowing = if (thrownItems.isEmpty()) it.items else listOf()

            val receivedItems = events
                .filter { e -> e.receiverId == it.id }
                .map { e -> e.inspectedItem }

            it.copy(
                items = itemsAfterThrowing + receivedItems,
                numItemsInspected = it.numItemsInspected + thrownItems.size
            )
        }
    }

    fun part1(apes: List<Ape>): Int {
        var currentApes = apes
        repeat(20) {
            for (i in 0..apes.indices.max()) {
                val newState = currentApes + allEventsInOneRoundFor(currentApes[i])
                currentApes = newState
            }
        }

        currentApes.print(selectOnlyApesWithItems = false)

        return currentApes
            .map { it.numItemsInspected }
            .sortedDescending()
            .take(2)
            .reduce { x, y -> x * y }
    }

    fun part2(apes: List<Ape>): Int {
        var currentApes = apes
        repeat(10000) {
            println("Round $it")
            if (it % 100 == 0) {
                currentApes.print(selectOnlyApesWithItems = true)
            }
            for (i in 0..apes.indices.max()) {
                val newState = currentApes + allEventsInOneRoundFor(currentApes[i])
                currentApes = newState
            }
        }

        currentApes.print(selectOnlyApesWithItems = false)

        return currentApes
            .map { it.numItemsInspected }
            .sortedDescending()
            .take(2)
            .reduce { x, y -> x * y }
    }
    val input = readInputAsString("Day11")

    // test if implementation meets criteria from the description, like:
/*
    val testInput = readInputAsString("Day11_test")
    val testApes = testInput.split("\n\n").map { parseApe(it, 1) }.toList()
    check(part1(testApes) == 10605) {
        "Failed on test input, expected 10605 got ${part1(testApes)}"
    }

    val apes = input.split("\n\n").map { parseApe(it, 1) }.toList()
    val answer = part1(apes)
    check(answer > 87616) {
        "Got $answer but expected above 87616"
    }
*/

    val apesPartTwo = input.split("\n\n").map { parseApe(it, 2) }.toList()
    val answerPartTwo = part2(apesPartTwo)
    check (answerPartTwo > 326366789) {
        "Got $answerPartTwo, expected > 326366789"
    }
    println(answerPartTwo)
}
