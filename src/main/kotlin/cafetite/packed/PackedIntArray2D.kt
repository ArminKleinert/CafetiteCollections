package cafetite.packed

import java.util.*

class PackedIntArray2D private constructor(private val packed: IntArray, private val numSubArrays: Int) :
    AbstractList<MutableList<Int>>(), RandomAccess {
    override val size: Int
        get() = numSubArrays

    val subArraySize: Int
        get() = packed.size / size

    val packedArraySize: Int
        get() = packed.size / size

    constructor(numSubArrays: Int, subArraySize: Int, initFn: (Int, Int) -> Int)
            : this(IntArray(numSubArrays * subArraySize), numSubArrays) {
        for (i in 0..numSubArrays) {
            for (j in 0..subArraySize) {
                val index = i * subArraySize + j
                packed[index] = initFn(i, j)
            }
        }
    }

    constructor(numSubArrays: Int, subArraySize: Int, fill: Int = 0)
            : this(IntArray(numSubArrays * subArraySize) { fill }, numSubArrays)

    constructor(numSubArrays: Int, subArraySize: Int)
            : this(IntArray(numSubArrays * subArraySize), numSubArrays)

    override fun isEmpty(): Boolean = size == 0

    fun flatIterator(startIndex: Int = 0): IntIterator = object : IntIterator() {
        private var cursor = startIndex
        override fun hasNext(): Boolean = cursor < packed.size

        override fun nextInt(): Int {
            val temp = packed[cursor]
            cursor++
            return temp
        }
    }

    override fun get(index: Int): IntArraySegment =
        IntArraySegment(packed, size * subArraySize, size * subArraySize + subArraySize)

    class IntArraySegment(
        private val original: IntArray,
        private val fromIndex: Int,
        private val toIndexExclusive: Int
    ) :
        AbstractMutableList<Int>() {
        override val size: Int
            get() = toIndexExclusive - fromIndex

        override fun get(index: Int): Int = original[index + fromIndex]

        override fun set(index: Int, element: Int): Int {
            val old = original[index + fromIndex]
            original[index + fromIndex] = element
            return old
        }

        override fun add(index: Int, element: Int): Unit = throw UnsupportedOperationException()
        override fun removeAt(index: Int): Int = throw UnsupportedOperationException()

        override fun listIterator(index: Int): MutableListIterator<Int> =
            object : IntIterator(), MutableListIterator<Int> {
                private var cursor = 0
                override fun hasNext(): Boolean = cursor < size
                override fun hasPrevious(): Boolean = cursor > 0
                override fun nextIndex(): Int = cursor
                override fun previousIndex(): Int = cursor - 1

                override fun nextInt(): Int {
                    val temp = get(cursor)
                    cursor++
                    return temp
                }

                fun setInt(element: Int) {
                    set(cursor, element)
                }

                override fun previous(): Int {
                    cursor--
                    return get(cursor)
                }

                override fun set(element: Int) {
                    set(cursor, element)
                }

                override fun add(element: Int) = throw UnsupportedOperationException()
                override fun remove() = throw UnsupportedOperationException()
            }
    }
}