enum class FileTypes {
    DIRECTORY,
    FILE
}

fun main() {
    data class AocFile(val name: String, val filePath: String, val size: Int?, val fileType: FileTypes)

    fun part1(input: List<String>): Int {
        val fileConts = mutableMapOf<String, MutableSet<AocFile>>()

        val dirsWithoutSize = mutableSetOf<AocFile>()

        var currentFilePath = "/"

        for (i in input.indices) {
            val currLine = input[i]
            if (currLine.startsWith("$ cd")) {
                val targetDir = currLine.split(" ")[2]
                currentFilePath = when (targetDir) {
                    "/" -> {
                        "/"
                    }

                    ".." -> {
                        currentFilePath.dropLast(1).dropLastWhile { it != '/' }
                    }

                    else -> {
                        val newDir = "$currentFilePath$targetDir/"
                        val dirFile = AocFile(targetDir, newDir, null, FileTypes.DIRECTORY)
                        fileConts.putIfAbsent(currentFilePath, mutableSetOf())
                        fileConts[currentFilePath]!!.add(dirFile)
                        dirsWithoutSize.add(dirFile)
                        newDir
                    }
                }
            }

            if (currLine.startsWith("dir ")) {
                val (_, dirName) = currLine.split(" ")
                val myFile = AocFile(dirName, "$currentFilePath$dirName/", null, FileTypes.DIRECTORY)

                fileConts.putIfAbsent(currentFilePath, mutableSetOf())
                fileConts[currentFilePath]!!.add(myFile)

                dirsWithoutSize.add(myFile)
            }

            if (currLine[0].isDigit()) {
                val (fileSize, fileName) = currLine.split(" ")
                val myFile = AocFile(fileName, "$currentFilePath$fileName/", fileSize.toInt(), FileTypes.FILE)
                fileConts.putIfAbsent(currentFilePath, mutableSetOf())
                fileConts[currentFilePath]!!.add(myFile)
            }
        }

        val dirsWithSize = mutableSetOf<AocFile>()

        while (dirsWithoutSize.isNotEmpty()) {
            val dirsPathsWithSize = dirsWithSize.map { it.filePath }.toSet()
            val dirsToRemove = mutableSetOf<AocFile>()
            for (d in dirsWithoutSize) {
                val filesInDir = fileConts[d.filePath]!!.map {
                    if (it.filePath in dirsPathsWithSize) {
                        dirsWithSize.first { d -> d.filePath == it.filePath }
                    } else {
                        it
                    }
                }

                if (filesInDir.none { it.size == null }) {
                    dirsToRemove.add(d)
                    dirsWithSize.add(
                        d.copy(size = filesInDir.sumOf { it.size!! })
                    )
                }
            }
            dirsWithoutSize.removeAll(dirsToRemove)
        }

        return dirsWithSize.filter { it.size!! <= 100000 }.sumOf { it.size!! }
    }

    fun part2(input: List<String>): Int {
        return 5
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
