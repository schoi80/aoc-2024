import java.util.LinkedList

data class Tracker(
    val rc: RowCol,
    val d: Dir,
    val move: Int = 0,
    val turn: Int = 0,
    val visited: MutableSet<RowCol> = mutableSetOf<RowCol>()
) {
    fun next() = copy(rc = rc.next(d), move = move + 1, visited = visited.toMutableSet().also { it.add(rc.next(d)) })
    fun clockwise() = copy(d = d.clockwise(), turn = turn + 1)
    fun counterClockwise() = copy(d = d.counterClockwise(), turn = turn + 1)
    fun getScore() = move + turn * 1000
}

fun main() {

    fun part1(input: MutableList<String>): Int {
        val start = input.sequence().first { input.getOrNull(it) == 'S' }
        val visited = mutableMapOf<Pair<RowCol, Dir>, Tracker>()
        val q = LinkedList<Tracker>()
        q.add(Tracker(start, Dir.LEFT, visited = mutableSetOf(start)))
        val result = mutableSetOf<Tracker>()
        while (q.isNotEmpty()) {
            var t = q.poll()
            if (visited.contains(t.rc to t.d)) {
                if (visited[t.rc to t.d]!!.getScore() < t.getScore())
                    continue
            }
            visited.put(t.rc to t.d, t)

            if (input.getOrNull(t.rc) == 'E')
                result.add(t)
            else {
                val n = t.next()
                if (input.getOrNull(n.rc) != '#')
                    q.add(n)
                q.add(t.clockwise())
                q.add(t.counterClockwise())
            }
        }

        return result.minOf { it.getScore() }
    }

    fun part2(input: MutableList<String>): Pair<Int, Int> {
        val start = input.sequence().first { input.getOrNull(it) == 'S' }
        val visited = mutableMapOf<Pair<RowCol, Dir>, Tracker>()
        val q = LinkedList<Tracker>()
        q.add(Tracker(start, Dir.LEFT, visited = mutableSetOf(start)))
        val result = mutableSetOf<Tracker>()
        while (q.isNotEmpty()) {
            var t = q.poll()
            if (visited.contains(t.rc to t.d)) {
                if (visited[t.rc to t.d]!!.getScore() < t.getScore())
                    continue
            }
            visited.put(t.rc to t.d, t)

            if (input.getOrNull(t.rc) == 'E')
                result.add(t)
            else {
                val n = t.next()
                if (input.getOrNull(n.rc) != '#')
                    q.add(n)
                q.add(t.clockwise())
                q.add(t.counterClockwise())
            }
        }

        val r = result.groupBy { it.getScore() }
        val score = r.minOf { it.key }
        val tiles = r[score]!!.map { it.visited }.fold(mutableSetOf<RowCol>()) { acc, it ->
            acc.addAll(it)
            acc
        }
        return score to tiles.size
    }

    val input = readInput("Day16").toMutableList()
//    part1(input).println()
    part2(input).println()
}
