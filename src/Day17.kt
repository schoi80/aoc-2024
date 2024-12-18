import java.lang.Math.floor
import java.lang.Math.pow

data class Register(
    var a: Int,
    var b: Int,
    var c: Int,
) {

}

fun main() {


    fun wackyProgram(s1: Long, s2: Long, s3: Long, s4: String): String {
        var a = s1
        var b = s2
        var c = s3
        val p = s4.split(",").map { it.toInt() }

//        val r = Register(a, b, c)
        var curr = 0

        fun combo(op: Int): Long {
            return when (op) {
                0 -> 0
                1 -> 1
                2 -> 2
                3 -> 3
                4 -> a
                5 -> b
                6 -> c
                else -> error("invalid")
            }
        }

        fun adv(op: Int): String {
            a = floor(a / pow(2.0, combo(op).toDouble())).toLong()
            return ""
        }

        fun bxl(lt: Int): String {
            b = b xor lt.toLong()
            return ""
        }

        fun bst(op: Int): String {
            b = combo(op) % 8
            return ""
        }

        fun jnz(lt: Int): String {
            if (a != 0L) {
                curr = lt
            }
            return ""
        }

        fun bxc(_op: Int): String {
            b = b xor c
            return ""
        }

        fun out(op: Int): String {
            return (combo(op) % 8).toString()
        }

        fun bdv(op: Int): String {
            b = floor(a / pow(2.0, combo(op).toDouble())).toLong()
            return ""
        }

        fun cdv(op: Int): String {
            c = floor(a / pow(2.0, combo(op).toDouble())).toLong()
            return ""
        }

        val output = mutableListOf<String>()
        while (curr < p.size) {
            val o = p[curr++]
            val op = p[curr++]
            when (o) {
                0 -> adv(op)
                1 -> bxl(op)
                2 -> bst(op)
                3 -> jnz(op)
                4 -> bxc(op)
                5 -> out(op)
                6 -> bdv(op)
                7 -> cdv(op)
                else -> error("invalid")
            }.let { output.add(it) }
        }

        return output.filter { it != "" }.joinToString(",")
    }


    fun part1(input: MutableList<String>): String {
        var a = input[0].split(": ")[1].toLong()
        var b = input[1].split(": ")[1].toLong()
        var c = input[2].split(": ")[1].toLong()
        val p = input[4].split(": ")[1]
        return wackyProgram(a, b, c, p)
    }


    fun wackyProgram2(s1: Int, s2: Int, s3: Int, s4: String): String {
        var a = s1
        var b = s2
        var c = s3
        val p = s4.split(",").map { it.toInt() }

//        val r = Register(a, b, c)
        var curr = 0

        fun combo(op: Int): Int {
            return when (op) {
                0 -> 0
                1 -> 1
                2 -> 2
                3 -> 3
                4 -> a
                5 -> b
                6 -> c
                else -> error("invalid")
            }
        }

        fun adv(op: Int): String {
            a = floor(a / pow(2.0, combo(op).toDouble())).toInt()
            return ""
        }

        fun bxl(lt: Int): String {
            b = b xor lt
            return ""
        }

        fun bst(op: Int): String {
            b = combo(op) % 8
            return ""
        }

        fun jnz(lt: Int): String {
            if (a != 0) {
                curr = lt
            }
            return ""
        }

        fun bxc(_op: Int): String {
            b = b xor c
            return ""
        }

        fun out(op: Int): String {
            return (combo(op) % 8).toString()
        }

        fun bdv(op: Int): String {
            b = floor(a / pow(2.0, combo(op).toDouble())).toInt()
            return ""
        }

        fun cdv(op: Int): String {
            c = floor(a / pow(2.0, combo(op).toDouble())).toInt()
            return ""
        }

        val output = mutableListOf<String>()
        while (curr < p.size) {
            val o = p[curr++]
            val op = p[curr++]
            when (o) {
                0 -> adv(op)
                1 -> bxl(op)
                2 -> bst(op)
                3 -> jnz(op)
                4 -> bxc(op)
                5 -> out(op)
                6 -> bdv(op)
                7 -> cdv(op)
                else -> error("invalid")
            }.let {
                if (it != "") {
                    output.add(it)
                    if (!s4.startsWith(output.joinToString(",")))
                        error("blip")
                }
            }
        }

        return output.filter { it != "" }.joinToString(",")
    }

    fun part2(input: MutableList<String>): String {
//        var a = input[0].split(": ")[1].toInt()
        var b = input[1].split(": ")[1].toLong()
        var c = input[2].split(": ")[1].toLong()
        val p = input[4].split(": ")[1]


//        var a = pow(8.0,15.0).toLong()
        var a = 202366611619840L
        val start = a
        var output = wackyProgram(a, b, c, p)
        var digitMap = output.split(",").mapIndexed { index, s -> index to s }.toMap()
        val inc = 1L//(2 * pow(8.0,7.0)).toLong()
        while (true) {
            output = wackyProgram(a, b, c, p)
            println("$a: $output")
            if (output == p)
                break
//            val tempMap = output.split(",").mapIndexed { index, s -> index to s }.toMap()
////            (3..15).forEach {
//            if (digitMap[6] != tempMap[6]) {
//                println("digit 4 changed after ${a - start} iterations")
//            }
//            digitMap = tempMap
////            }
            a += inc
        }
        return a.toString()
    }

    var input = readInput("Day17").toMutableList()
    part1(input).println()
    part2(input).println()

//    input = readInput("Day17").toMutableList()
//    part1(input).println()
//    part2(input).println()
}
