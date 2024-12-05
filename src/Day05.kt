fun main() {

    fun List<String>.inOrder(after: Map<String, Set<String>>, before: Map<String, Set<String>>): Boolean {
        this.indices.forEach { i ->
            val a = after[this[i]] ?: setOf()
            val b = before[this[i]] ?: setOf()
            if (i > 0) {
                (0..i - 1).forEach { j ->
                    if (a.contains(this[j])) return false
                }
            }
            if (i < this.size - 1) {
                (i + 1..this.size - 1).forEach { j ->
                    if (b.contains(this[j])) return false
                }
            }
        }
        return true
    }

    fun List<String>.correctOrder(after: Map<String, Set<String>>, before: Map<String, Set<String>>): List<String> {
        val s = this.toSet()
        val newAfter =
            after.filterKeys { s.contains(it) }.mapValues { (_, v) -> v.filter { s.contains(it) } }.toMutableMap()
        val newBefore =
            before.filterKeys { s.contains(it) }.mapValues { (_, v) -> v.filter { s.contains(it) } }.toMutableMap()

        if (newAfter.size > newBefore.size) {
            if (newAfter.size < s.size) s.first { !newAfter.keys.contains(it) }.let { newAfter[it] = listOf() }
            val r = newAfter.map { (k, v) -> k to v.size }.sortedByDescending { it.second }.map { it.first }
            return r
        } else {
            if (newBefore.size < s.size) s.first { !newBefore.keys.contains(it) }.let { newBefore[it] = listOf() }
            val r = newBefore.map { (k, v) -> k to v.size }.sortedBy { it.second }.map { it.first }
            return r
        }
    }

    fun part1(input: List<String>): Int {
        val a = input.takeWhile { it != "" }
        val after = mutableMapOf<String, MutableSet<String>>()
        val before = mutableMapOf<String, MutableSet<String>>()
        a.forEach {
            val r = it.split("|")
            after[r[0]] = (after[r[0]] ?: mutableSetOf<String>()).apply { this.add(r[1]) }
            before[r[1]] = (before[r[1]] ?: mutableSetOf<String>()).apply { this.add(r[0]) }
        }
        val b = input.takeLastWhile { it != "" }.map { it.split(",") }.filter { it.inOrder(after, before) }
        return b.sumOf { it[it.size / 2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val a = input.takeWhile { it != "" }
        val after = mutableMapOf<String, MutableSet<String>>()
        val before = mutableMapOf<String, MutableSet<String>>()
        a.forEach {
            val r = it.split("|")
            after[r[0]] = (after[r[0]] ?: mutableSetOf<String>()).apply { this.add(r[1]) }
            before[r[1]] = (before[r[1]] ?: mutableSetOf<String>()).apply { this.add(r[0]) }
        }
        val b = input.takeLastWhile { it != "" }.map { it.split(",") }.filterNot { it.inOrder(after, before) }
            .map { it.correctOrder(after, before) }

        return b.sumOf { it[it.size / 2].toInt() }
    }

    val input = readInput("sample")
    part1(input).println()
    part2(input).println()
}
