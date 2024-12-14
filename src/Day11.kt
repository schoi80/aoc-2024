fun main() {

//    fun blink(v: String): List<String> {
//        if (v.toLong() == 0L)
//            return listOf("1")
//
//        if (v.length % 2 == 0) {
//            val a = v.take(v.length / 2).toLong().toString()
//            var b = v.takeLast(v.length / 2).toLong().toString()
//            return listOf(a, b)
//        }
//
//        return listOf((v.toLong() * 2024L).toString())
//    }

//    fun part1(input: MutableList<String>): Int {
//        var line = input[0].split(" ")
//        var count = 0
//        while (count < 25) {
//            line = line.map { blink(it) }.flatten()
//            count++
//        }
//        return line.size
//    }

    val cache = mutableMapOf<Pair<String, Int>, Long>()

    fun blink(v: String, blinkCount: Int): Long {
        val key = v to blinkCount

        if (cache.containsKey(key))
            return cache[key]!!

        if (blinkCount == 0) {
            cache[key] = 1
        } else if (v.toLong() == 0L) {
            cache[key] = blink("1", blinkCount - 1)
        } else if (v.length % 2 == 0) {
            val a = v.take(v.length / 2).toLong().toString()
            var b = v.takeLast(v.length / 2).toLong().toString()
            cache[key] = blink(a, blinkCount - 1) + blink(b, blinkCount - 1)
        } else {
            cache[key] = blink((v.toLong() * 2024L).toString(), blinkCount - 1)
        }

        return cache[key]!!
    }

    fun part1(input: MutableList<String>): Long {
        var line = input[0].split(" ")
        return line.sumOf { blink(it, 25) }
    }

    fun part2(input: MutableList<String>): Long {
        var line = input[0].split(" ")
        return line.sumOf { blink(it, 75) }
    }

    val input = readInput("Day11").toMutableList()
    part1(input).println()
    part2(input).println()
}