fun main() {
    data class Point(val x: Int, val y: Int)

    fun findVisibleTreesInRow(treeRow: List<Int>): Set<Int> {
        val visibleTrees = mutableSetOf<Int>()

        var highestSoFar = Int.MIN_VALUE

        treeRow.forEachIndexed { index, height ->
            if (height > highestSoFar) {
                visibleTrees.add(index)
                highestSoFar = height
            }
        }

        highestSoFar = Int.MIN_VALUE

        val maxIdx = treeRow.indices.max()
        for (index in maxIdx downTo  0) {
            val height = treeRow[index]
            if (height > highestSoFar) {
                visibleTrees.add(index)
                highestSoFar = height
            }
        }


        visibleTrees.add(0)
        visibleTrees.add(maxIdx)

        return visibleTrees
    }

    fun printForest(maxIndex: Int, visibleTrees: MutableSet<Point>) {
        (0..maxIndex).forEach { y ->
            (0..maxIndex).forEach { x ->
                if (visibleTrees.contains(Point(x, y))) {
                    print("X")
                } else {
                    print("O")
                }
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {

        val visibleTrees = mutableSetOf<Point>()
        val gridOfTrees = mutableListOf<MutableList<Int>>()

        input.forEach { gridOfTrees.add(it.map { t -> t.code }.toMutableList()) }

        val maxIndex = input.indices.max()

        for (y in 0 until gridOfTrees.size) {
            visibleTrees.addAll(findVisibleTreesInRow(gridOfTrees[y]).map { x -> Point(x, y) })
        }

        for (x in 0 until gridOfTrees.size) {
            val col = gridOfTrees.map { it[x] }
            val visibleColIndices = findVisibleTreesInRow(col)
            val zipRowWithCol = (0..maxIndex).zip(visibleColIndices)
            visibleTrees.addAll(zipRowWithCol.map { (_, y) -> Point(x, y) })
        }

        //printForest(maxIndex, visibleTrees)

        return visibleTrees.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)

    val input = readInput("Day07")
    println(part1(input))
    //println(part2(input))
}
