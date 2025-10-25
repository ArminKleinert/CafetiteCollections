package cafetite.markable

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MarkerListTest {
    @Test
    fun dirty() {
        Assertions.assertEquals(false, MarkerList(listOf<Int>()).dirty)
        Assertions.assertEquals(false, MarkerList<Int>().dirty)
        Assertions.assertEquals(false, MarkerList.of<Int>().dirty)
        Assertions.assertEquals(false, MarkerList(listOf(1,2,3)).dirty)
        Assertions.assertEquals(false, MarkerList.of(1,2,3).dirty)

        val ml = MarkerList.of<Int>()
        ml.dirty = true
        Assertions.assertEquals(true, ml.dirty)
    }

    @Test
    fun getSize() {
        Assertions.assertEquals(0, MarkerList(listOf<Int>()).size)
        Assertions.assertEquals(0, MarkerList<Int>().size)
        Assertions.assertEquals(0, MarkerList.of<Int>().size)

        Assertions.assertEquals(3, MarkerList(listOf(1, 2, 3)).size)

        // Test: `size` does not mark the collection as dirty.
        val ml = MarkerList.of<Int>()
        Assertions.assertEquals(false, ml.dirty)
        ml.size
        Assertions.assertEquals(false, ml.dirty)
        ml.dirty = true
        ml.size
        Assertions.assertEquals(true, ml.dirty)
    }

    @Test
    fun clear() {
        val ml = MarkerList.of(1, 2, 3)
        Assertions.assertEquals(false, ml.dirty)
        Assertions.assertEquals(3, ml.size)

        ml.clear()
        Assertions.assertEquals(true, ml.dirty)
        Assertions.assertEquals(0, ml.size)

        ml.dirty = false
        ml.clear()
        Assertions.assertEquals(false, ml.dirty)
        Assertions.assertEquals(0, ml.size)
    }

    @Test
    fun addAll() {
        val ml = MarkerList.of<Int>()
        Assertions.assertEquals(false, ml.dirty)
        Assertions.assertEquals(true, ml.addAll(listOf(5)))
        Assertions.assertEquals(true, ml.dirty)
    }

    @Test
    fun add() {
        val ml = MarkerList.of<Int>()
        Assertions.assertEquals(false, ml.dirty)
        Assertions.assertEquals(true, ml.add(5))
        Assertions.assertEquals(true, ml.dirty)
    }

    @Test
    fun containsAll() {
        val ml = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ml.dirty)
        Assertions.assertEquals(true, ml.containsAll(setOf()))
        Assertions.assertEquals(false, ml.dirty)

        Assertions.assertEquals(false, ml.dirty)
        Assertions.assertEquals(true, ml.containsAll(setOf(1, 2)))
        Assertions.assertEquals(false, ml.dirty)

        Assertions.assertEquals(false, ml.dirty)
        Assertions.assertEquals(false, ml.containsAll(setOf(1, 2, 3, 4)))
        Assertions.assertEquals(false, ml.dirty)
    }

    @Test
    fun contains() {
        val ms = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.contains(3))
        Assertions.assertEquals(false, ms.dirty)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(false, ms.contains(4))
        Assertions.assertEquals(false, ms.dirty)
    }

    @Test
    fun get() {
        val ms = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(2, ms[1])
        Assertions.assertEquals(false, ms.dirty)
    }

    @Test
    fun removeAt() {
        val ms = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(2, ms.removeAt(1))
        Assertions.assertEquals(true, ms.dirty)
    }

    @Test
    fun subList() {
        run{
            val ms = MarkerList.of(1, 2, 3)
            Assertions.assertEquals(false, ms.dirty)

            val msSub = ms.subList(0, 3)
            Assertions.assertEquals(listOf(1, 2, 3), msSub)
            Assertions.assertEquals(false, ms.dirty)

            msSub.removeAt(1)
            Assertions.assertEquals(listOf(1, 3), msSub)
            Assertions.assertEquals(true, ms.dirty)
            Assertions.assertEquals(listOf(1, 3), ms)
        }
        run{
            val ms = MarkerList.of(1, 2, 3)
            Assertions.assertEquals(false, ms.dirty)

            val msSub = ms.subList(1, 3)
            Assertions.assertEquals(listOf(2, 3), msSub)
            Assertions.assertEquals(false, ms.dirty)

            msSub.removeAt(1)
            Assertions.assertEquals(listOf(2), msSub)
            Assertions.assertEquals(true, ms.dirty)
            Assertions.assertEquals(listOf(1, 2), ms)
        }
    }

    @Test
    fun retainAll() {
        val ms = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.retainAll(listOf(1, 2)))
        Assertions.assertEquals(true, ms.dirty)

        ms.dirty = false
        Assertions.assertEquals(false, ms.retainAll(listOf(1, 2)))
        Assertions.assertEquals(false, ms.dirty)
    }

    @Test
    fun set() {
        val ms = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(2, ms.set(1, 4))
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(listOf(1, 4, 3), ms)
    }

    @Test
    fun testEquals() {
        val ms = MarkerList.of(1, 2, 3)
        Assertions.assertEquals(ms, ms)
        Assertions.assertEquals(listOf(1, 2, 3), ms)
        Assertions.assertNotEquals(1, ms)
        Assertions.assertEquals(listOf<Int>(), MarkerList.of<Int>())
        Assertions.assertEquals(MarkerList.of(1, 2, 3), ms)
    }

    @Test
    fun isEmpty() {
        val ms = MarkerList.of(1, 2, 3)
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(false, ms.isEmpty())
        Assertions.assertEquals(false, ms.dirty)

        val msE = MarkerList.of<Int>()
        Assertions.assertEquals(false, msE.dirty)
        Assertions.assertEquals(true, msE.isEmpty())
        Assertions.assertEquals(false, msE.dirty)
    }

    @Test
    fun iterator() {
        val ms = MarkerList.of(1, 2, 3)
        val msI = ms.iterator()

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(1, msI.next())
        Assertions.assertEquals(false, ms.dirty)

        msI.remove()
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(listOf(2, 3), ms)
    }

    @Test
    fun listIterator() {
        val ms = MarkerList.of(1, 2, 3)
        val msI = ms.listIterator()

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(1, msI.next())
        Assertions.assertEquals(false, ms.dirty)

        msI.remove()
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(listOf(2, 3), ms)

        ms.dirty = false
        msI.add(4)
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(listOf(4, 2, 3), ms)
    }

    @Test
    fun removeAll() {
        val ms = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.removeAll(listOf(2, 3)))
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(listOf(1), ms)

        ms.dirty = false
        Assertions.assertEquals(false, ms.removeAll(listOf(2, 3)))
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(listOf(1), ms)
    }

    @Test
    fun remove() {
        val ms = MarkerList.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.remove(2))
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(listOf(1,3), ms)

        ms.dirty = false
        Assertions.assertEquals(false, ms.remove(2))
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(listOf(1,3), ms)
    }

    @Test
    fun lastIndexOf() {
        val ms = MarkerList.of(1, 2, 3, 2)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.lastIndexOf(2))
        Assertions.assertEquals(false, ms.dirty)

        Assertions.assertEquals(-1, ms.lastIndexOf(4))
        Assertions.assertEquals(false, ms.dirty)
    }

    @Test
    fun indexOf() {
        val ms = MarkerList.of(1, 2, 3, 2)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(1, ms.indexOf(2))
        Assertions.assertEquals(false, ms.dirty)

        Assertions.assertEquals(-1, ms.indexOf(4))
        Assertions.assertEquals(false, ms.dirty)
    }
}