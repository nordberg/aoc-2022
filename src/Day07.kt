enum class FileTypes {
    DIRECTORY,
    FILE
}

fun main() {
    data class AocFile(val name: String, val filePath: String, val size: Int?, val fileType: FileTypes) {
        fun totalSize(fileConts: MutableMap<String, MutableSet<AocFile>>): Int {
            return when (fileType) {
                FileTypes.FILE -> size!!
                FileTypes.DIRECTORY -> fileConts[filePath]!!.sumOf { it.totalSize(fileConts) }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val dirTree = mutableMapOf<String, String>()
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
                        dirTree[currentFilePath] = newDir
                        newDir
                    }
                }
            }

            if (currLine.startsWith("dir ")) {
                val (_, dirName) = currLine.split(" ")
                val myFile = AocFile(dirName, "$currentFilePath$dirName", null, FileTypes.DIRECTORY)

                if (!fileConts.containsKey(currentFilePath)) {
                    fileConts[currentFilePath] = mutableSetOf()
                }

                fileConts[currentFilePath]!!.add(myFile)
                dirsWithoutSize.add(myFile)
            }

            if (currLine[0].isDigit()) {
                val (fileSize, fileName) = currLine.split(" ")
                val myFile = AocFile(fileName, "$currentFilePath$fileName", fileSize.toInt(), FileTypes.FILE)

                if (!fileConts.containsKey(currentFilePath)) {
                    fileConts[currentFilePath] = mutableSetOf()
                }

                fileConts[currentFilePath]!!.add(myFile)
            }
        }

        println(fileConts)

        val dirsWithSize = mutableSetOf<AocFile>()

        while (dirsWithoutSize.isNotEmpty()) {
            val dirsToRemove = mutableSetOf<AocFile>()
            for (d in dirsWithoutSize) {
                val filesInDir = fileConts["${d.filePath}/"]!!
                if (filesInDir.none { it.size == null }) {
                    dirsToRemove.add(d)
                    dirsWithSize.add(
                        d.copy(size = filesInDir.sumOf { it.size!! })
                    )
                }
            }
            dirsWithoutSize.removeAll(dirsToRemove)
            break
        }


        return 5
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
