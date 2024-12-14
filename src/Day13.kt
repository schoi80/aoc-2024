fun main() {

    fun getTotal(a: Pair<Int, Int>, b: Pair<Int, Int>, c: Pair<Int, Int>): Pair<Int, Int> {
        val (ax, ay) = a
        val (bx, by) = b
        val (ac, bc) = c
        return ((ax * ac) + (bx * bc)) to ((ay * ac) + (by * bc))
    }

    fun getToken(c: Pair<Int, Int>): Int {
        val (ac, bc) = c
        return (ac * 3) + bc
    }

    fun getToken(a: Pair<Int, Int>, b: Pair<Int, Int>, p: Pair<Int, Int>): Int {
        (0..100).forEach { ac ->
            (0..100).forEach { bc ->
                if (getTotal(a, b, ac to bc) == p) {
                    return getToken(ac to bc)
                }
            }
        }
        return 0
    }

    fun part1(input: MutableList<String>): Int {
        return input.chunked(4).sumOf {
            val a = it[0].split(": ")[1].split(", ").map { it.substring(2).toInt() }.let { it[0] to it[1] }
            val b = it[1].split(": ")[1].split(", ").map { it.substring(2).toInt() }.let { it[0] to it[1] }
            val p = it[2].split(": ")[1].split(", ").map { it.substring(2).toInt() }.let { it[0] to it[1] }
            getToken(a, b, p)
        }
    }

    fun getPushCount(a: Pair<Long, Long>, b: Pair<Long, Long>, p: Pair<Long, Long>): Pair<Long, Long>? {
        val (ax, ay) = a
        val (bx, by) = b
        val (px, py) = p
        val det = ax * by - ay * bx
        if (det == 0L) return null

        val xDet = px * by - py * bx
        val yDet = ax * py - px * ay

        if (xDet % det != 0L || yDet % det != 0L) return null

        val x = xDet / det
        val y = yDet / det

        return if (x < 0 || y < 0) null else x to y
    }

    fun part2(input: MutableList<String>): Long {
        return input.chunked(4).sumOf {
            val a = it[0].split(": ")[1].split(", ").map { it.substring(2).toInt() }
                .let { it[0].toLong() to it[1].toLong() }
            val b = it[1].split(": ")[1].split(", ").map { it.substring(2).toInt() }
                .let { it[0].toLong() to it[1].toLong() }
            val p = it[2].split(": ")[1].split(", ").map { it.substring(2).toInt() }
                .let { it[0].toLong() + 1E13.toLong() to it[1].toLong() + 1E13.toLong() }
            getPushCount(a, b, p)?.let { it.first * 3 + it.second } ?: 0L
        }
    }

    val input = readInput("Day13").toMutableList()
    part1(input).println()
    part2(input).println()
}