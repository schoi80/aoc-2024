fun main() {

    fun part1(input: MutableList<String>): Long {
        val line = input[0]
        val blockSet = (0 until line.length step 2).map {
            line[it].digitToInt()
        }.toMutableList()

        val spaceSet = (1 until line.length step 2).map {
            line[it].digitToInt()
        }.toMutableList()

//        blockSet.println()
//        spaceSet.println()

        val spaceList = mutableListOf<Int>()
        ((blockSet.size - 1) downTo 0).forEach { blockId ->
            var blockCount = blockSet[blockId]
            while (blockCount != 0) {
                spaceList.add(blockId)
                blockCount--
            }
        }

        val totalCount = blockSet.sum()

        val finalList = mutableListOf<Int>()
        var curr = 0
        blockSet.indices.forEach { blockId ->
            repeat(blockSet[blockId]) {
                finalList.add(blockId)
            }
            if (spaceSet.size > blockId && finalList.size < totalCount) {
                repeat(spaceSet[blockId]) {
                    finalList.add(spaceList[curr])
                    curr++
                }
            }
        }

        return finalList.take(totalCount).mapIndexed { index, value -> (index * value).toLong() }.sum()
    }

    fun part2(input: MutableList<String>): Long {
        val line = input[0]
        val totalCount = line.map { it.digitToInt() }.sum()

        val blockSet = (0 until line.length step 2).map {
            line[it].digitToInt()
        }.toMutableList()

        var spaceSet = (1 until line.length step 2).map {
            line[it].digitToInt()
        }.toMutableList()

//        blockSet.println()
//        spaceSet.println()

        val blockIdMove = mutableMapOf<Int, Int>()
        val tempSpaceSet = spaceSet.toMutableList()

        ((blockSet.size - 1) downTo 0).forEach { blockId ->
            var blockCount = blockSet[blockId]
            for (i in 0 until tempSpaceSet.size) {
                val v = tempSpaceSet[i]
                if (v >= blockCount && i < blockId) {
                    tempSpaceSet[i] = v - blockCount
                    blockIdMove[blockId] = i
//                    println("Moving $blockId to space $i only if it is lower")
                    break
                }
            }
        }

        val movableBlocks = blockIdMove.map { (k, v) -> k to v }
            .sortedWith(compareBy<Pair<Int, Int>> { it.second }.thenByDescending { it.first })
            .groupBy { it.second }
            .mapValues { (_, v) -> v.map { it.first } }
        val movableBlockIds = movableBlocks.map { it.value }.flatten().toSet()

        val finalList = mutableListOf<Int>()
        var curr = 0
        blockSet.indices.forEach { blockId ->
            if (movableBlockIds.contains(blockId)) {
                repeat(blockSet[blockId]) { finalList.add(0) }
            } else {
                repeat(blockSet[blockId]) { finalList.add(blockId) }
            }
            if (blockId < spaceSet.size) {
                var remainingSpace = spaceSet[blockId]
                movableBlocks[blockId]?.forEach { movableBlockId ->
                    remainingSpace -= blockSet[movableBlockId]
                    repeat(blockSet[movableBlockId]) {
                        finalList.add(movableBlockId)
                        curr++
                    }
                }
                repeat(remainingSpace) {
                    finalList.add(0)
                    curr++
                }
            }
        }

        return finalList.take(totalCount).mapIndexed { index, value -> (index * value).toLong() }.sum()
    }

    val sample = readInput("sample").toMutableList()
    check(part1(sample) == 1928L)
    check(part2(sample) == 2858L)

    val input = readInput("Day09").toMutableList()
    part1(input).println()
    part2(input).println()
}