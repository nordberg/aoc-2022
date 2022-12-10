import java.lang.IllegalArgumentException
import kotlin.math.abs

enum class Direction {
    UP,
    UPRIGHT,
    UPLEFT,
    DOWN,
    DOWNRIGHT,
    DOWNLEFT,
    LEFT,
    RIGHT
}

data class Move(val direction: Direction, val steps: Int)

data class Point(val x: Int, val y: Int) {
    fun distanceTo(otherPoint: Point): Int {
        return abs(x - otherPoint.x) + abs(y - otherPoint.y)
    }
}

data class Rope(val head: Point, val tail: Point) {
    operator fun plus(move: Move): List<Rope> {
        val newPositions = mutableListOf<Rope>()
        var updatingRope = this.copy()
        for (step in 1..move.steps) {
            val newHead = applyMoveToKnot(move.direction, updatingRope.head)

            val headToTailDist = newHead.distanceTo(updatingRope.tail)
            val tailNeedsToMove =
                ((headToTailDist > 1 && (newHead.x == updatingRope.tail.x || newHead.y == updatingRope.tail.y) || headToTailDist > 2))
            val newTail = if (tailNeedsToMove) {
                val dirToMove = Direction.values().reduce { acc, dir ->
                    val distIfMoveAcc = newHead.distanceTo(applyMoveToKnot(acc, updatingRope.tail))
                    val distIfMoveDir = newHead.distanceTo(applyMoveToKnot(dir, updatingRope.tail))
                    val bestDiagonalMove = applyDiagonalMoves(updatingRope.tail, newHead)
                    val newPointIfBestDiagonalMove = applyMoveToKnot(bestDiagonalMove, updatingRope.tail)
                    val distIfBestDiagonal = newPointIfBestDiagonalMove.distanceTo(newHead)

                    val listOfDists = listOf(distIfMoveAcc, distIfBestDiagonal, distIfMoveDir)
                    when (listOfDists.sorted().first { it > 0 }) {
                        distIfMoveAcc -> acc
                        distIfMoveDir -> dir
                        distIfBestDiagonal -> bestDiagonalMove
                        else -> throw IllegalStateException()
                    }
                }
                applyMoveToKnot(dirToMove, updatingRope.tail)
            } else {
                tail
            }

            updatingRope = Rope(newHead, newTail)
            newPositions.add(updatingRope)
            //println("Move was \n\t${move}, \nold rope was \n\t$this\n and new rope was \n\t$updatingRope")
        }

        return newPositions
    }

    private fun applyDiagonalMoves(point: Point, targetPoint: Point): Direction {
        val validPairs = listOf(
            Direction.UPLEFT,
            Direction.DOWNLEFT,
            Direction.DOWNRIGHT,
            Direction.UPRIGHT
        )

        val directionMinimizingDistanceToTarget = validPairs.reduce { acc, it ->
            val distAcc = applyMoveToKnot(acc, point).distanceTo(targetPoint)
            val distIt = applyMoveToKnot(it, point).distanceTo(targetPoint)
            if (distAcc < distIt) {
                acc
            } else {
                it
            }
        }

        return directionMinimizingDistanceToTarget
    }

    private fun applyMoveToKnot(direction: Direction, point: Point): Point {
        return when (direction) {
            Direction.UP -> Point(point.x, point.y - 1)
            Direction.DOWN -> Point(point.x, point.y + 1)
            Direction.LEFT -> Point(point.x - 1, point.y)
            Direction.RIGHT -> Point(point.x + 1, point.y)
            Direction.UPRIGHT -> Point(point.x + 1, point.y - 1)
            Direction.DOWNRIGHT -> Point(point.x + 1, point.y + 1)
            Direction.DOWNLEFT -> Point(point.x - 1, point.y + 1)
            Direction.UPLEFT -> Point(point.x - 1, point.y - 1)
        }
    }
}

fun main() {
    fun part1(moves: List<Move>): Int {
        var currentPos = Rope(Point(0, 0), Point(0, 0))
        val visitedPointsOnMap = moves.flatMap {
            val newRopes = currentPos + it
            currentPos = newRopes.last()
            newRopes.map { i -> i.tail }
        }.toSet() + setOf(Point(0, 0))

        println(visitedPointsOnMap.size)
        return visitedPointsOnMap.size
    }

    fun part2(input: List<String>): Int {
        return 4
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val moves = parseMoves(testInput)

    check(part1(moves) == 13)

    val input = readInput("Day09")
    println(part1(parseMoves(input)))
    println(part2(input))
}

private fun parseMoves(input: List<String>): List<Move> {
    val moves = input.map {
        val (direction, dist) = it.split(" ")
        val dirDir = when (direction) {
            "R" -> Direction.RIGHT
            "L" -> Direction.LEFT
            "U" -> Direction.UP
            "D" -> Direction.DOWN
            else -> throw IllegalArgumentException("$direction is error")
        }
        Move(dirDir, dist.toInt())
    }.toList()
    return moves
}
