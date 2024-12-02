import kotlin.math.abs

fun main() {
    fun isPairSafe(a: Long, b: Long): Boolean {
        return (abs(a - b) in 1..3)
    }

    fun List<Long>.isSafe(): Boolean {
        if (this.sorted() == this || this.sortedDescending() == this) {
            (1..this.size - 1).forEach {
                if (!isPairSafe(this[it - 1], this[it])) return false
            }
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toLong() } }.count { it.isSafe() }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toLong() } }.count { line ->
            if (line.isSafe()) return@count true
            else {
                line.indices.forEach { i1 ->
                    if (line.filterIndexed { i2, _ -> i2 != i1 }.isSafe())
                        return@count true
                }
                return@count false
            }
        }
    }

    val input = readInput("sample")
    part1(input).println()
    part2(input).println()
}
