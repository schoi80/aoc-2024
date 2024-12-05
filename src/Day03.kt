fun main() {

    fun String.mul(): Int {
        val v = this.replace("mul(", "").replace(")", "").split(",").map { it.toInt() }
        return v[0] * v[1]
    }

    fun part1(input: List<String>): Int {
        val regex = Regex("""mul\(\d+,\d+\)""")
        return input.sumOf { line -> regex.findAll(line).toList().sumOf { it.value.mul() } }
    }

    fun part2(input: List<String>): Int {
        val regex = Regex("""(mul\(\d+,\d+\)|don't\(\)|do\(\))""")
        val r = input.map { line -> regex.findAll(line).toList().map { it.value } }.flatten()
        val f = mutableListOf<String>()
        var enabled = true
        r.forEach {
            if (it == "don't()")
                enabled = false
            else if (it == "do()")
                enabled = true
            else if (enabled) f.add(it)
        }
        return f.sumOf { it.mul() }
    }

    val input = readInput("sample")
    part1(input).println()
    part2(input).println()
}
