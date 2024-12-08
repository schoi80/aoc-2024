fun main() {

    fun createPair(signals: List<RowCol>): List<Pair<RowCol, RowCol>> {
        return signals.indices.map { i ->
            ((i + 1) until signals.size).map { j -> signals[i] to signals[j] }
        }.flatten()
    }

    fun calDistance(p: Pair<RowCol, RowCol>): Pair<RowCol, RowCol> {
        val (a, b) = p
        val diff = (b.first - a.first) to (b.second - a.second)
        val c = (b.first + diff.first) to (b.second + diff.second)
        val d = (a.first - diff.first) to (a.second - diff.second)
        return c to d
    }

    fun Grid.calDistance2(p: Pair<RowCol, RowCol>): List<RowCol> {
        val (a, b) = p
        val diff = (b.first - a.first) to (b.second - a.second)
        val result = mutableListOf<RowCol>()
        var c: RowCol = b
        while (true) {
            c = (c.first + diff.first) to (c.second + diff.second)
            if (isInbound(c))
                result.add(c)
            else break
        }
        c = a
        while (true) {
            c = (c.first - diff.first) to (c.second - diff.second)
            if (isInbound(c))
                result.add(c)
            else break
        }
        return result
    }

    fun part1(input: Grid): Int {
        val antinode = mutableSetOf<RowCol>()
        var uniqueSignal = mutableMapOf<Char, MutableSet<RowCol>>()
        input.indices.forEach { r ->
            input[r].indices.forEach { c ->
                val rc = RowCol(r, c)
                val signal = input.get(rc).getOrThrow()
                if (signal != '.')
                    uniqueSignal[signal] = (uniqueSignal[signal] ?: mutableSetOf()).apply { add(rc) }
            }
        }
        uniqueSignal.mapValues { createPair(it.value.toList()) }
            .forEach { (_, v) ->
                v.forEach {
                    val (a, b) = calDistance(it)
                    antinode.add(a)
                    antinode.add(b)
                }
            }

        return antinode.count { input.isInbound(it) }
    }

    fun part2(input: MutableList<String>): Int {
        val antinode = mutableSetOf<RowCol>()
        var uniqueSignal = mutableMapOf<Char, MutableSet<RowCol>>()
        input.indices.forEach { r ->
            input[r].indices.forEach { c ->
                val rc = RowCol(r, c)
                val signal = input.get(rc).getOrThrow()
                if (signal != '.') {
                    uniqueSignal[signal] = (uniqueSignal[signal] ?: mutableSetOf()).apply { add(rc) }
                    antinode.add(rc)
                }
            }
        }

        uniqueSignal.mapValues { it -> createPair(it.value.toList()) }
            .forEach { (_, v) -> v.forEach { antinode.addAll(input.calDistance2(it)) } }

        return antinode.size
    }

    val input = readInput("sample").toMutableList()
    part1(input).println()
    part2(input).println()
}