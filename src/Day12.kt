import kotlin.streams.toList

fun main() {
    data class Point(val x: Int, val y: Int)

    fun getWalkableAdjacent(grid: List<List<Int>>, mid: Point): List<Point> {
        val maxX = grid.first().indices.max()
        check(mid.x in 0..maxX)
        val maxY = grid.indices.max()
        check(mid.y in 0..maxY)

        val adjacentPoints = mutableListOf<Point>()

        if (mid.x > 0) {
            adjacentPoints.add(mid.copy(x = mid.x - 1))
        }

        if (mid.x < maxX) {
            adjacentPoints.add(mid.copy(x = mid.x + 1))
        }

        if (mid.y > 0) {
            adjacentPoints.add(mid.copy(y = mid.y - 1))
        }

        if (mid.y < maxY) {
            adjacentPoints.add(mid.copy(y = mid.y + 1))
        }

        return adjacentPoints.filter {
            val adjPointHeight = if (grid[it.y][it.x] == 'E'.code) 'z'.code else grid[it.y][it.x]
            val currHeight = if (grid[mid.y][mid.x] == 'S'.code) 'a'.code else grid[mid.y][mid.x]
            currHeight <= adjPointHeight + 1
        }
    }

    fun calcDistancesToAdjAndFindNewPointsToVisit(
        input: List<List<Int>>,
        currentPoint: Point,
        stepDistances: List<MutableList<Int>>,
    ): List<Point> {
        val adjacentPoints = getWalkableAdjacent(input, currentPoint)
        val pointsToVisit = mutableListOf<Point>()
        adjacentPoints.forEach {
            stepDistances[it.y][it.x] = stepDistances[currentPoint.y][currentPoint.x] + 1
            pointsToVisit += it
        }
        return pointsToVisit
    }

    fun part1(mountainMap: List<List<Int>>): Int {
        val startY = mountainMap.indexOfFirst { y -> y.indexOfFirst { x -> x == 'S'.code } != -1 }
        val startX = mountainMap[startY].indexOfFirst { x -> x == 'S'.code }
        val startPoint = Point(startX, startY)

        val targetY = mountainMap.indexOfFirst { y -> y.indexOfFirst { x -> x == 'E'.code } != -1 }
        val targetX = mountainMap[startY].indexOfFirst { x -> x == 'E'.code }
        val targetPoint = Point(targetX, targetY)

        val stepDistances = mountainMap.map { i ->
            i.map {
                if (it == 'S'.code) {
                    0
                } else {
                    Int.MAX_VALUE
                }
            }.toMutableList()
        }

        val pointsToVisit = mutableSetOf(startPoint)

        while (stepDistances[targetPoint.y][targetPoint.x] == Int.MAX_VALUE) {

            val toRemove = mutableSetOf<Point>()
            val toAdd = mutableSetOf<Point>()
            pointsToVisit.forEach {
                toRemove.add(it)
                toAdd += calcDistancesToAdjAndFindNewPointsToVisit(mountainMap, it, stepDistances)
            }

            pointsToVisit.removeAll(toRemove)
            pointsToVisit.addAll(toAdd.sortedBy { stepDistances[it.y][it.x] })
            check(pointsToVisit.size > 0)
        }

        return stepDistances[targetPoint.y][targetPoint.x]
    }

    fun stepDistFound(mMap: List<List<Int>>, p: Point): Boolean {
        return mMap[p.y][p.x] < Int.MAX_VALUE
    }

    fun part2(mountainMap: List<List<Int>>): Int {
        val targetPoints = mutableSetOf<Point>()
        mountainMap.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if (setOf('a', 'S').contains(value.toChar())) {
                    targetPoints.add(Point(x, y))
                }
            }
        }

        val targetY = mountainMap.indexOfFirst { y -> y.indexOfFirst { x -> x == 'E'.code } != -1 }
        val targetX = mountainMap[targetY].indexOfFirst { x -> x == 'E'.code }
        val startPoint = Point(targetX, targetY)


        val stepDistances = mountainMap.mapIndexed { y, row ->
            List(row.size) { x ->
                if (x == startPoint.x && y == startPoint.y) {
                    0
                } else {
                    Int.MAX_VALUE
                }
            }.toMutableList()
        }

        val pointsToVisit = mutableSetOf(startPoint)

        while (targetPoints.none { stepDistFound(stepDistances, it) }) {

            val toRemove = mutableSetOf<Point>()
            val toAdd = mutableSetOf<Point>()
            pointsToVisit.forEach {
                toRemove.add(it)
                toAdd += calcDistancesToAdjAndFindNewPointsToVisit(mountainMap, it, stepDistances)
            }

            pointsToVisit.removeAll(toRemove)
            pointsToVisit.addAll(toAdd.sortedBy { stepDistances[it.y][it.x] })
            check(pointsToVisit.size > 0)
        }

        return targetPoints.minOf { stepDistances[it.y][it.x] }
    }

    val input = readInput("Day12").map { it.chars().toList() }
    val minDist = part2(input)
    println(minDist)
}
