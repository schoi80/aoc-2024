fun main() {

    fun Grid.getTrailHead(curr: RowCol, result: MutableSet<RowCol>) {
        val currValue = get(curr).mapCatching { it.digitToInt() }.getOrNull()
        if (currValue == 9) {
            println("Trail ends at $curr")
            result.add(curr)
        }

        if (currValue == null)
            return

        val possible = listOf(
            curr.next(Dir.UP),
            curr.next(Dir.DOWN),
            curr.next(Dir.LEFT),
            curr.next(Dir.RIGHT)
        ).filter { get(it).mapCatching { it.digitToInt() }.getOrNull() == (currValue + 1) }

        possible.forEach { getTrailHead(it, result) }
    }

    fun Grid.getTrailHead2(curr: RowCol) : Int {
        val currValue = get(curr).mapCatching { it.digitToInt() }.getOrNull()
        if (currValue == 9) {
            println("Trail ends at $curr")
            return 1
        }

        if (currValue == null)
            return 0

        val possible = listOf(
            curr.next(Dir.UP),
            curr.next(Dir.DOWN),
            curr.next(Dir.LEFT),
            curr.next(Dir.RIGHT)
        ).filter { get(it).mapCatching { it.digitToInt() }.getOrNull() == (currValue + 1) }

        return possible.sumOf { getTrailHead2(it) }
    }

    fun Grid.findStart(): List<RowCol> {
        val r = this.size
        val c = this[0].length
        val result = mutableListOf<RowCol>()
        (0 until r).forEach { i ->
            (0 until c).forEach { j ->
                if (get(i, j).getOrThrow() == '0')
                    result.add(RowCol(i, j))
            }
        }
        return result
    }

    fun part1(input: MutableList<String>): Int {
        val starts = input.findStart()
        return starts.sumOf {
            val trailHeads = mutableSetOf<RowCol>()
            println("Starting from $it")
            input.getTrailHead(it, trailHeads)
            trailHeads.size.also { it.println() }
        }
    }

    fun part2(input: MutableList<String>): Int {
        val starts = input.findStart()
        return starts.sumOf {
            println("Starting from $it")
            input.getTrailHead2(it)
        }
    }

//    val sample = readInput("sample").toMutableList()
//    check(part1(sample) == 36)
//    check(part2(sample) == 3)

    val input = readInput("Day10").toMutableList()
    part1(input).println()
    part2(input).println()
}