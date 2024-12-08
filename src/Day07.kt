enum class Ops {
    ADD, MULTIPLY, CONCAT
}

fun main() {

    fun isValid(v: Long, nums: List<Long>, ops: List<List<Ops>>): Boolean {
        ops.forEach { o ->
            val r = o.foldIndexed(nums[0]) { i, r, t ->
                when (t) {
                    Ops.ADD -> r + nums[i + 1]
                    Ops.MULTIPLY -> r * nums[i + 1]
                    Ops.CONCAT -> "$r${nums[i + 1]}".toLong()
                }
            }
            if (r == v) return true
        }
        return false
    }

    fun part1(input: MutableList<String>): Long {
        return input.sumOf {
            val (v, nums) = it.split(": ").let { it[0].toLong() to it[1].split(" ").map { it.toLong() } }
            val ops = mutableListOf<List<Ops>>()
            permute(mutableListOf(), nums.size - 1, ops, setOf(Ops.ADD, Ops.MULTIPLY))
            if (isValid(v, nums, ops)) v else 0
        }
    }

    fun part2(input: MutableList<String>): Long {
        return input.sumOf {
            val (v, nums) = it.split(": ").let { it[0].toLong() to it[1].split(" ").map { it.toLong() } }
            val ops = mutableListOf<List<Ops>>()
            permute(mutableListOf(), nums.size - 1, ops, setOf(Ops.ADD, Ops.MULTIPLY, Ops.CONCAT))
            if (isValid(v, nums, ops)) v else 0
        }
    }

    val input = readInput("sample").toMutableList()
    part1(input).println()
    part2(input).println()
}