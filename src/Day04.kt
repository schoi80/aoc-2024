fun main() {

    fun List<String>.getWords(x: Int, y: Int): List<String> {
        val words = mutableListOf<String>()
        runCatching { words.add(listOf(this[y][x], this[y][x + 1], this[y][x + 2], this[y][x + 3]).joinToString("")) }
        runCatching { words.add(listOf(this[y][x], this[y][x - 1], this[y][x - 2], this[y][x - 3]).joinToString("")) }
        runCatching { words.add(listOf(this[y][x], this[y - 1][x], this[y - 2][x], this[y - 3][x]).joinToString("")) }
        runCatching { words.add(listOf(this[y][x], this[y + 1][x], this[y + 2][x], this[y + 3][x]).joinToString("")) }
        runCatching {
            words.add(listOf(this[y][x], this[y + 1][x + 1], this[y + 2][x + 2], this[y + 3][x + 3]).joinToString(""))
        }
        runCatching {
            words.add(listOf(this[y][x], this[y + 1][x - 1], this[y + 2][x - 2], this[y + 3][x - 3]).joinToString(""))
        }
        runCatching {
            words.add(listOf(this[y][x], this[y - 1][x + 1], this[y - 2][x + 2], this[y - 3][x + 3]).joinToString(""))
        }
        runCatching {
            words.add(listOf(this[y][x], this[y - 1][x - 1], this[y - 2][x - 2], this[y - 3][x - 3]).joinToString(""))
        }
        return words
    }

    fun List<String>.isXmas(x: Int, y: Int): Boolean {
        val words = mutableListOf<String>()
        runCatching {
            words.add(listOf(this[y - 1][x - 1], this[y][x], this[y + 1][x + 1]).joinToString(""))
        }
        runCatching {
            words.add(listOf(this[y - 1][x + 1], this[y][x], this[y + 1][x - 1]).joinToString(""))
        }
        return words.count { it == "MAS" || it == "SAM" } == 2
    }

    fun part1(input: List<String>): Int {
        val words = input.indices.map { y ->
            input[y].indices.map { x ->
                input.getWords(x, y)
            }.flatten()
        }.flatten()
        return words.count { it == "XMAS" || it == "SAMX" } / 2
    }

    fun part2(input: List<String>): Int {
        var count = 0
        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                if (input.isXmas(x, y)) count += 1
            }
        }
        return count
    }

    val input = readInput("sample")
    part1(input).println()
    part2(input).println()
}
