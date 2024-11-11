fun main() {
    fun part1(input: List<String>): Int {
        input.forEach { it.println() }
        return 1
    }

    fun part2(input: List<String>): Int {
        input.forEach { it.println() }
        return 1
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
