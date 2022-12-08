fun main() {
    data class Tree(val x: Int, val y: Int)

    fun findVisibleTreesInRow(treeRow: List<Int>): Set<Int> {
        val visibleTrees = mutableSetOf(0, treeRow.indices.max())

        var highestSoFar = Int.MIN_VALUE
        treeRow.forEachIndexed { index, height ->
            if (height > highestSoFar) {
                visibleTrees.add(index)
                highestSoFar = height
            }
        }

        highestSoFar = Int.MIN_VALUE
        val maxIdx = treeRow.indices.max()
        for (index in maxIdx downTo 0) {
            val height = treeRow[index]
            if (height > highestSoFar) {
                visibleTrees.add(index)
                highestSoFar = height
            }
        }

        return visibleTrees
    }

    fun part1(input: List<String>): Int {

        val visibleTrees = mutableSetOf<Tree>()
        val gridOfTrees = mutableListOf<MutableList<Int>>()

        input.forEach { gridOfTrees.add(it.map { t -> t.code }.toMutableList()) }

        val maxIndex = input.indices.max()

        for (y in 0 until gridOfTrees.size) {
            visibleTrees.addAll(findVisibleTreesInRow(gridOfTrees[y]).map { x -> Tree(x, y) })
        }

        for (x in 0 until gridOfTrees.size) {
            val col = gridOfTrees.map { it[x] }
            val visibleColIndices = findVisibleTreesInRow(col)
            val zipRowWithCol = (0..maxIndex).zip(visibleColIndices)
            visibleTrees.addAll(zipRowWithCol.map { (_, y) -> Tree(x, y) })
        }

        return visibleTrees.size
    }

    fun findScenicScore(forest: List<List<Int>>, tree: Tree): Int {
        val treeHeight = forest[tree.y][tree.x]

        val visionDist = mutableListOf<Int>()

        for (up in tree.y - 1 downTo 0) {
            if (forest[up][tree.x] >= treeHeight || up == 0) {
                visionDist.add(tree.y - up)
                break
            }
        }

        val forestSize = forest.indices.max()
        for (down in tree.y + 1 .. forestSize) {
            if (forest[down][tree.x] >= treeHeight || down == forestSize) {
                visionDist.add(down - tree.y)
                break
            }
        }

        for (left in tree.x - 1 downTo 0) {
            if (forest[tree.y][left] >= treeHeight || left == 0) {
                visionDist.add(tree.x - left)
                break
            }
        }

        for (right in tree.x + 1 .. forestSize) {
            if (forest[tree.y][right] >= treeHeight || right == forestSize) {
                visionDist.add(right - tree.x)
                break
            }
        }
        return visionDist.reduce { acc, r -> acc * r }
    }

    fun part2(input: List<String>): Int {
        val gridOfTrees = mutableListOf<MutableList<Int>>()
        input.forEach { gridOfTrees.add(it.map { t -> t.code }.toMutableList()) }

        val maxIdx = gridOfTrees.indices.max()

        var maxScenicScore = Int.MIN_VALUE

        for (y in 1 until maxIdx) {
            for (x in 1 until maxIdx) {
                val scenicScore = findScenicScore(gridOfTrees, Tree(x, y))
                maxScenicScore = maxScenicScore.coerceAtLeast(scenicScore)
            }
        }
        return maxScenicScore
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    //println(part1(input))
    println(part2(input))

    fun printForest(maxIndex: Int, visibleTrees: MutableSet<Tree>) {
        (0..maxIndex).forEach { y ->
            (0..maxIndex).forEach { x ->
                if (visibleTrees.contains(Tree(x, y))) {
                    print("X")
                } else {
                    print("O")
                }
            }
            println()
        }
    }

}
