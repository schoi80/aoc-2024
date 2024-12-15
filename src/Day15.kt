import java.util.*

fun main() {

    fun Char.getDir() = when (this) {
        '^' -> Dir.UP
        'v' -> Dir.DOWN
        '<' -> Dir.LEFT
        '>' -> Dir.RIGHT
        else -> error("blip")
    }

    fun RowCol.distance() = (first * 100) + second

    fun Grid.canMove(r: RowCol, d: Dir): RowCol? {
        if (getOrNull(r.next(d)) == '#')
            return null

        if (getOrNull(r.next(d)) == '.')
            return r.next(d)

        var dr = 0
        var dc = 0
        when (d) {
            Dir.UP -> dr = -1
            Dir.DOWN -> dr = 1
            Dir.LEFT -> dc = -1
            Dir.RIGHT -> dc = 1
        }
        var n = r
        while (true) {
            n = n.first + dr to n.second + dc
            if (getOrNull(n) == '.') {
                return n
            } else if (getOrNull(n) == null || getOrNull(n) == '#') {
                break
            }
        }

        return null
    }

    fun Grid.getStart(): RowCol {
        (0 until rows()).forEach { r ->
            (0 until cols()).forEach { c ->
                if (getOrNull(r to c) == '@')
                    return r to c
            }
        }
        error("blip")
    }

    fun part1(input: MutableList<String>): Int {
        val grid = input.takeWhile { it != "" }.toMutableList()
        val dirs = input.takeLastWhile { it != "" }.joinToString("")
        var robot = grid.getStart()

        dirs.forEach {
            val d = it.getDir()
            val n = grid.canMove(robot, d)
            if (n != null) {
                grid.set(robot, '.')
                robot = robot.next(d)
                grid.set(robot, '@')
                var t = robot
                while (t != n) {
                    t = t.next(d)
                    grid.set(t, 'O')
                }

            }
        }

        var sum = 0
        (0 until grid.rows()).forEach { r ->
            (0 until grid.cols()).forEach { c ->
                if (grid.getOrNull(r to c) == 'O')
                    sum += (r to c).distance()
            }
        }
        return sum
    }

    fun Grid.tryToMove(r: RowCol, d: Dir) {
        val c = getOrNull(r)!!
        val n = getOrNull(r.next(d))
        if (n == '.') {
            set(r, n)
            set(r.next(d), c)
            return
        } else if (n == '#') {
            error("cant move")
        }

        if (d == Dir.LEFT || d == Dir.RIGHT) {
            tryToMove(r.next(d), d)
            tryToMove(r, d)
            return
        } else {
            if (n == '[') {
                tryToMove(r.next(d), d)
                tryToMove(r.next(d).next(Dir.RIGHT), d)
                tryToMove(r, d)
                return
            } else if (n == ']') {
                tryToMove(r.next(d).next(Dir.LEFT), d)
                tryToMove(r.next(d), d)
                tryToMove(r, d)
                return
            }
        }
    }

    fun part2(input: MutableList<String>): Int {
        var grid = input.takeWhile { it != "" }.toMutableList()
        val dirs = input.takeLastWhile { it != "" }.joinToString("")

        // Scale horizontally
        grid = grid.map { it.map { "$it$it" }.joinToString("") }
            .map { it.replace("OO", "[]").replace("@@", "@.") }.toMutableList()
        grid.print()

        var robot = grid.getStart()
        dirs.forEach {
            val d = it.getDir()
            val tGrid = grid.toMutableList()
            runCatching { tGrid.tryToMove(robot, d) }
                .map { robot = robot.next(d) }
                .map { grid = tGrid }
//            grid.print()
        }

        var sum = 0
        (0 until grid.rows()).forEach { r ->
            (0 until grid.cols()).forEach { c ->
                if (grid.getOrNull(r to c) == '[')
                    sum += (r to c).distance()
            }
        }
        return sum
    }

    val input = readInput("Day15").toMutableList()
    part1(input).println()
    part2(input).println()
}