fun main() {

    fun Grid.fenceCount(rc: RowCol): Int {
        return listOf(
            rc.next(Dir.UP),
            rc.next(Dir.DOWN),
            rc.next(Dir.LEFT),
            rc.next(Dir.RIGHT)
        ).count { get(it).getOrNull() != get(rc).getOrNull() }
    }

    fun Grid.getArea(rc: RowCol, visited: MutableSet<RowCol>) {
        visited.add(rc)
        listOf(
            rc.next(Dir.UP),
            rc.next(Dir.DOWN),
            rc.next(Dir.LEFT),
            rc.next(Dir.RIGHT)
        ).filter { !visited.contains(it) && get(it).getOrNull() == get(rc).getOrNull() }
            .forEach { getArea(it, visited) }
    }

    fun part1(input: MutableList<String>): Int {
        val visited = mutableSetOf<RowCol>()
        var total = 0
        input.indices.forEach { r ->
            input[0].indices.forEach { c ->
                if (!visited.contains(r to c)) {
                    val rc = r to c
                    val m = mutableSetOf<RowCol>()
                    input.getArea(rc, m)
                    total += m.size * m.sumOf { input.fenceCount(it) }
                    visited.addAll(m)
                }
            }
        }
        return total
    }

    fun Grid.isCorner(rc: RowCol): Boolean {
        val curr = getOrNull(rc)
        val u = rc.next(Dir.UP).let { getOrNull(it) }
        val d = rc.next(Dir.DOWN).let { getOrNull(it) }
        val l = rc.next(Dir.LEFT).let { getOrNull(it) }
        val r = rc.next(Dir.RIGHT).let { getOrNull(it) }
        val ur = rc.next(Dir.UP).next(Dir.RIGHT).let { getOrNull(it) }
        val ul = rc.next(Dir.UP).next(Dir.LEFT).let { getOrNull(it) }
        val dr = rc.next(Dir.DOWN).next(Dir.RIGHT).let { getOrNull(it) }
        val dl = rc.next(Dir.DOWN).next(Dir.LEFT).let { getOrNull(it) }

        val isTopLeft = (u != curr && l != curr) || (d == curr && r == curr && dr != curr)
        val isTopRight = (u != curr && r != curr) || (d == curr && l == curr && dl != curr)
        val isBottomLeft = (d != curr && l != curr) || (u == curr && r == curr && ur != curr)
        val isBottomRight = (d != curr && r != curr) || (u == curr && l == curr && ul != curr)
        return isTopLeft || isTopRight || isBottomLeft || isBottomRight
    }

    fun Grid.getEdgeCount(area: Set<RowCol>): Int {
        return area.filter { isCorner(it) }.size
    }

    fun part2(input: MutableList<String>): Int {
        val upscaled = input.map { it.map { "$it$it" }.joinToString("") }
            .map { listOf(it, it) }.flatten().toMutableList()
        upscaled.print()

        val visited = mutableSetOf<RowCol>()
        var total = 0
        upscaled.indices.forEach { r ->
            upscaled[0].indices.forEach { c ->
                if (!visited.contains(r to c)) {
                    val rc = r to c
                    val m = mutableSetOf<RowCol>()
                    upscaled.getArea(rc, m)
                    val area = m.size / 4
                    val sides = upscaled.getEdgeCount(m)
                    total += area * sides
                    visited.addAll(m)
                }
            }
        }
        return total
    }

    val input = readInput("sample").toMutableList()
    part1(input).println()
    part2(input).println()
}