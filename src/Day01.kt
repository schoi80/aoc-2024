import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Long {
        val (a, b) = input.map {
            it.split(" ").let { it.first().toLong() to it.last().toLong() }
        }.unzip()
        return a.sorted().zip(b.sorted()).map { (x, y) -> abs(x - y) }.sum()
    }

    fun part2(input: List<String>): Long {
        val (a, b) = input.map {
            it.split(" ").let { it.first().toLong() to it.last().toLong() }
        }.unzip()
        val bMap = mutableMapOf<Long, Int>()
        b.forEach { bMap[it] = 1 + (bMap[it] ?: 0) }
        return a.map { it * (bMap[it] ?: 0) }.sum()
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
