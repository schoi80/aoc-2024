import java.util.PriorityQueue

fun main() {

    fun part1(input: MutableList<String>, bytes:Int = 12, size: Int = 7): Int {
        val grid = MutableList(size) { ".".repeat(size) }
        input.take(bytes).forEach {
            it.split(",").map { it.toInt() }.let {
                grid.set(it[1] to it[0], '#')
            }
        }

        var start = 0 to 0
        val visited = mutableSetOf<RowCol>()
        val q = PriorityQueue<Pair<RowCol, Int>>(compareBy { it.second })
        q.add(start to 0)

        while(q.isNotEmpty()) {
            val (c, step) = q.poll()
            if (c in visited)
                continue
            else visited.add(c)

            if (c == size-1 to size-1)
                return step

            listOf(
                c.next(Dir.UP),
                c.next(Dir.DOWN),
                c.next(Dir.LEFT),
                c.next(Dir.RIGHT),
            ).filter { grid.getOrNull(it) == '.' }
                .forEach { q.add(it to step + 1) }
        }

        return 0
    }

    fun part2(input: MutableList<String>, bytes:Int = 12, size: Int = 7): String {
        var c = bytes + 1
        while (part1(input, c, size) != 0)
            c++

        println(c)
        return input[c-1]
    }

    var input = readInput("sample").toMutableList()
    part1(input, 12, 7).println()
    part2(input, 12, 7).println()

    input = readInput("Day18").toMutableList()
    part1(input, 1024, 71).println()
    part2(input, 1024, 71).println()
}
