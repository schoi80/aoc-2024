typealias Robot = Pair<Pair<Int, Int>, Pair<Int, Int>>

fun main() {

    fun Robot.move(iteration: Int, row: Int, col: Int): RowCol {
        var dr = (this.first.first + (this.second.first * iteration)) % row
        var dc = (this.first.second + (this.second.second * iteration)) % col
        if (dr < 0) dr += row
        if (dc < 0) dc += col
        return dr to dc
    }

    fun part1(input: MutableList<String>, row: Int = 7, col: Int = 11, i: Int = 100): Int {
        val rh = row / 2
        val ch = col / 2
        return input.map {
            it.split(" ").let {
                val p = it[0].substring(2).split(",").let { it[1].toInt() to it[0].toInt() }
                val v = it[1].substring(2).split(",").let { it[1].toInt() to it[0].toInt() }
                p to v
            }.move(i, row, col)
        }.fold(mutableListOf(0, 0, 0, 0)) { acc, rc ->
            if ((rc.first < rh) && (rc.second < ch)) acc[0] += 1
            if ((rc.first < rh) && (rc.second > ch)) acc[1] += 1
            if ((rc.first > rh) && (rc.second < ch)) acc[2] += 1
            if ((rc.first > rh) && (rc.second > ch)) acc[3] += 1
            acc
        }.fold(1) { acc, q -> acc * q }
    }

    fun printGrid(grid: MutableList<MutableList<Int>>) {
        println("**************************************************************************")
        grid.forEach {
            it.map { if (it == 0) " " else it.toString() }.joinToString("").println()
        }
        println("**************************************************************************")
    }

    fun part2(input: MutableList<String>, row: Int = 7, col: Int = 11): Int {
        val robots = input.map {
            it.split(" ").let {
                val p = it[0].substring(2).split(",").let { it[1].toInt() to it[0].toInt() }
                val v = it[1].substring(2).split(",").let { it[1].toInt() to it[0].toInt() }
                p to v
            }
        }

        var count = 0

        while (true) {
            val grid = MutableList(row) { MutableList<Int>(col) { 0 } }
            count += 1
            robots.map { it.move(count, row, col) }.forEach { (r, c) -> grid[r][c] += 1 }
            if (grid.count { it.count { it <= 1 } == col } == row) {
                printGrid(grid)
                break
            }
        }

        return count
    }

    val input = readInput("Day14").toMutableList()
    part1(input, 103, 101, 100).println()
    part2(input, 103, 101).println()
}