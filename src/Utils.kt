import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

typealias Grid = MutableList<String>
typealias RowCol = Pair<Int, Int>

enum class Dir {
    UP, DOWN, LEFT, RIGHT
}

fun Dir.rotate() = when (this) {
    Dir.UP -> Dir.RIGHT
    Dir.DOWN -> Dir.LEFT
    Dir.LEFT -> Dir.UP
    Dir.RIGHT -> Dir.DOWN
}

fun RowCol.next(d: Dir) = when (d) {
    Dir.UP -> first - 1 to second
    Dir.DOWN -> first + 1 to second
    Dir.LEFT -> first to second - 1
    Dir.RIGHT -> first to second + 1
}

fun Grid.rows() = this.size
fun Grid.cols() = this[0].length

fun Grid.getOrNull(rc: RowCol) = get(rc.first, rc.second).getOrNull()
fun Grid.get(rc: RowCol) = get(rc.first, rc.second)
fun Grid.get(r: Int, c: Int) = runCatching { this[r][c] }

fun Grid.int(rc: RowCol) = int(rc.first, rc.second)
fun Grid.int(r: Int, c: Int) = runCatching { this[r][c].digitToInt() }
fun Grid.long(rc: RowCol) = long(rc.first, rc.second)
fun Grid.long(r: Int, c: Int) = runCatching { this[r][c].digitToInt().toLong() }

fun Grid.set(rc: RowCol, v: Char) = set(rc.first, rc.second, v)
fun Grid.set(r: Int, c: Int, v: Char) {
    val newRow = this[r].toMutableList().apply { this[c] = v }.joinToString("")
    this[r] = newRow
}

fun Grid.isInbound(rc: RowCol): Boolean {
    val rowCount = this.size - 1
    val colCount = this[0].length - 1
    return rc.first in 0..rowCount && rc.second in 0..colCount
}

fun Grid.print() = this.forEach { it.split("").joinToString(" ").println() }

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("data/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Caps a number. Wrap around if exceeds.
 */
fun Int.cap(cap: Int): Int {
    val r = this % cap
    return if (r == 0) cap else r
}

fun Char.hexToBinary() = digitToInt(16).toString(2).padStart(4, '0')

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return (a * b) / gcd(a, b)
}

fun IntRange.intersects(r: IntRange): Boolean = first <= r.last && last >= r.first

fun Pair<Double, Double>.intersects(r: Pair<Double, Double>) = first <= r.second && second >= r.first

fun <T> permute(
    curr: MutableList<T>,
    maxLength: Int,
    result: MutableList<List<T>>,
    ops: Set<T>
) {
    if (curr.size == maxLength) {
        result.add(curr)
        return
    }
    ops.forEach { permute(curr.toMutableList().apply { add(it) }, maxLength, result, ops) }
}