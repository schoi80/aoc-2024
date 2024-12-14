fun main() {

    fun Grid.getCurrLocation(): Pair<RowCol, Dir> {
        val charSet = setOf('#', '.', 'X')
        this.indices.forEach { r ->
            this[r].indices.forEach { c ->
                if (!charSet.contains(this.get(r, c).getOrThrow()))
                    return RowCol(r, c) to Dir.UP
            }
        }
        error("not possible")
    }

    fun getVisited(input: Grid): Set<Pair<RowCol, Dir>> {
        var (curr, d) = input.getCurrLocation()
        val visited = mutableSetOf<Pair<RowCol, Dir>>()
        while (input.get(curr).isSuccess) {
            visited.add(curr to d)
            val next = curr.next(d)
            if (input.get(next).isFailure) break
            else if (input.get(next).getOrThrow() == '#')
                d = d.rotate()
            else curr = next
        }
        return visited
    }

    fun part1(input: MutableList<String>): Int {
        return getVisited(input).map { it.first }.distinct().size
    }

    fun Grid.containsCycle(start: RowCol, direction: Dir): Boolean {
        var curr = start
        var d = direction
        val visited = mutableSetOf<Pair<RowCol, Dir>>()
        while (this.get(curr).isSuccess) {
            if (visited.contains(curr to d))
                return true
            visited.add(curr to d)
            val next = curr.next(d)
            if (this.get(next).isFailure) break
            else if (this.get(next).getOrThrow() == '#')
                d = d.rotate()
            else curr = next
        }
        return false
    }

    fun part2(input: Grid): Int {
        val (curr, d) = input.getCurrLocation()
        return getVisited(input).map { it.first }.toSet().count {
            input.toMutableList().apply { set(it, '#') }.containsCycle(curr, d)
        }
    }

    val input = readInput("sample").toMutableList()
    part1(input).println()
    part2(input).println()
}