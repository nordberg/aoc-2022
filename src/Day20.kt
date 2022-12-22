import kotlin.math.abs

fun wrapAroundIndex(index: Int, indexWrapLimit: Int, movingUp: Boolean): Int {
    if (index in 0..indexWrapLimit) {
        return if (movingUp) {
            if (index == indexWrapLimit) {
                0
            } else {
                index
            }
        } else {
            if (index == 0) {
                indexWrapLimit
            } else {
                index
            }
        }
    }

    if (index > indexWrapLimit) {
        return wrapAroundIndex(index - indexWrapLimit, indexWrapLimit, movingUp)
    }

    return wrapAroundIndex(index + indexWrapLimit, indexWrapLimit, movingUp)
}

fun wrapAroundIndex2(index: Int, indexWrapLimit: Int, movingUp: Boolean): Int {
    val newIndex = if (index > 2 * indexWrapLimit) {
        indexWrapLimit - (index % indexWrapLimit)
    } else if (index < -indexWrapLimit) {
        (indexWrapLimit - ((abs(index) % indexWrapLimit)))
    } else if (index > indexWrapLimit) {
        (index - indexWrapLimit)
    } else if (index < 0) {
        (indexWrapLimit + index)
    } else {
        index
    }


    if (newIndex == 0) {
        return indexWrapLimit
    }

    check(newIndex in 0..indexWrapLimit) {
        "0 <= $newIndex <= $indexWrapLimit"
    }

    if (movingUp) {
        return newIndex - 1
    }

    return newIndex
}


fun main() {

    data class CoordWithId(val value: Int, val id: Int)

    fun part1(input: List<Int>): Int {
        val coordinatesWithId = input.mapIndexed { index, i -> CoordWithId(i, index) }

        val decryptedCoordinates = coordinatesWithId.toMutableList()

        coordinatesWithId.forEach { coordinateWithId ->
            val oldIndexForCoord = decryptedCoordinates.indexOfFirst { it.id == coordinateWithId.id }
            val newIndexForCoord = wrapAroundIndex(
                oldIndexForCoord + coordinateWithId.value,
                coordinatesWithId.indices.max().toInt(),
                coordinateWithId.value > 0
            )

            decryptedCoordinates.removeAt(oldIndexForCoord)
            decryptedCoordinates.add(newIndexForCoord, coordinateWithId)

            check(decryptedCoordinates[newIndexForCoord].id == coordinateWithId.id) {
                "Expected $coordinateWithId at $newIndexForCoord but found ${decryptedCoordinates[newIndexForCoord]}"
            }
        }

        val indexOfZero = decryptedCoordinates.indexOfFirst { it.value == 0 }
        return listOf(1000, 2000, 3000).sumOf {
            decryptedCoordinates[(indexOfZero + it) % decryptedCoordinates.size].value
        }
    }

    fun part2(input: List<String>): Int {
        return 5
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test").map { it.toInt() }
    val testAnswer = part1(testInput)
    check(testAnswer == 3) {
        "Expected 3, got $testAnswer"
    }

    val input = readInput("Day20").map { it.toInt() }
    val answer = part1(input)
    check(answer < 15733) {
        "Expected $answer to be lower than 15733"
    }
    check(answer > 941) {
        "Expected $answer to be higher than 15733"
    }
    check(answer != 7770) {
        "Answer is not 7770"
    }
    check(answer != 4263) {
        "Answer is not 4263"
    }
    check(answer != 8254) {
        "Answer is not 8254"
    }

    println(answer)
    //println(part2(input))
}
