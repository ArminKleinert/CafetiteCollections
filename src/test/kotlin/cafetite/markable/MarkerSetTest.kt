package cafetite.markable

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MarkerSetTest {

    @Test
    fun dirty() {
        Assertions.assertEquals(false, MarkerSet(mutableSetOf<Int>()).dirty)
        Assertions.assertEquals(false, MarkerSet(setOf<Int>()).dirty)
        Assertions.assertEquals(false, MarkerSet(listOf<Int>()).dirty)
        Assertions.assertEquals(false, MarkerSet<Int>().dirty)
        Assertions.assertEquals(false, MarkerSet.of<Int>().dirty)
        Assertions.assertEquals(false, MarkerSet(mutableSetOf(1, 2, 3)).dirty)
        Assertions.assertEquals(false, MarkerSet(setOf(1, 2, 3)).dirty)
        Assertions.assertEquals(false, MarkerSet(listOf(1, 2, 3)).dirty)
        Assertions.assertEquals(false, MarkerSet.of(1, 2, 3).dirty)

        val ms = MarkerSet.of(1, 2, 3)
        Assertions.assertEquals(false, ms.dirty)
        ms.dirty = true
        Assertions.assertEquals(true, ms.dirty)
        ms.dirty = false
        Assertions.assertEquals(false, ms.dirty)
    }

    @Test
    fun add() {
        val ms = MarkerSet.of<Int>()
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.add(5))
        Assertions.assertEquals(true, ms.dirty)

        ms.dirty = false
        Assertions.assertEquals(false, ms.add(5)) // Already included
        Assertions.assertEquals(false, ms.dirty) // No changes made
    }

    @Test
    fun addAll() {
        val ms = MarkerSet.of<Int>()
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.addAll(listOf(5)))
        Assertions.assertEquals(true, ms.dirty)

        ms.dirty = false
        Assertions.assertEquals(false, ms.addAll(listOf(5))) // Already included
        Assertions.assertEquals(false, ms.dirty) // No changes made
    }

    @Test
    fun getSize() {
        Assertions.assertEquals(0, MarkerSet(mutableSetOf<Int>()).size)
        Assertions.assertEquals(0, MarkerSet(setOf<Int>()).size)
        Assertions.assertEquals(0, MarkerSet(listOf<Int>()).size)
        Assertions.assertEquals(0, MarkerSet<Int>().size)
        Assertions.assertEquals(0, MarkerSet.of<Int>().size)

        Assertions.assertEquals(3, MarkerSet(mutableSetOf(1, 2, 3)).size)
        Assertions.assertEquals(3, MarkerSet(setOf(1, 2, 3)).size)
        Assertions.assertEquals(3, MarkerSet(listOf(1, 2, 3)).size)

        // Test: `size` does not mark the collection as dirty.
        val ms = MarkerSet.of<Int>()
        Assertions.assertEquals(false, ms.dirty)
        ms.size
        Assertions.assertEquals(false, ms.dirty)
        ms.dirty = true
        ms.size
        Assertions.assertEquals(true, ms.dirty)

    }

    @Test
    fun clear() {
        val ms = MarkerSet.of(1, 2, 3)
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.size)

        ms.clear()
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(0, ms.size)

        ms.dirty = false
        ms.clear()
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(0, ms.size)
    }

    @Test
    fun retainAll() {
        val ms = MarkerSet.of(1, 2, 3)
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.size)

        ms.retainAll(setOf(1, 2, 3))
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.size)

        ms.retainAll(setOf(4))
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(0, ms.size)
    }

    @Test
    fun removeAll() {
        val ms = MarkerSet.of(1, 2, 3)
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.size)

        Assertions.assertEquals(false, ms.removeAll(setOf(4)))
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.size)


        Assertions.assertEquals(true, ms.removeAll(setOf(1, 2, 3)))
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(0, ms.size)
    }

    @Test
    fun remove() {
        val ms = MarkerSet.of(1, 2, 3)
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.size)

        Assertions.assertEquals(false, ms.remove(4))
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(3, ms.size)

        Assertions.assertEquals(true, ms.remove(1))
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(2, ms.size)
    }

    @Test
    fun isEmpty() {
        var ms = MarkerSet.of(1, 2, 3)
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(false, ms.isEmpty())
        Assertions.assertEquals(false, ms.dirty) // `isEmpty` does not mark as dirty

        ms = MarkerSet.of()
        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.isEmpty())
        Assertions.assertEquals(false, ms.dirty) // `isEmpty` does not mark as dirty
    }

    @Test
    fun containsAll() {
        val ms = MarkerSet.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.containsAll(setOf()))
        Assertions.assertEquals(false, ms.dirty)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.containsAll(setOf(1, 2)))
        Assertions.assertEquals(false, ms.dirty)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(false, ms.containsAll(setOf(1, 2, 3, 4)))
        Assertions.assertEquals(false, ms.dirty)
    }

    @Test
    fun contains() {
        val ms = MarkerSet.of(1, 2, 3)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(true, ms.contains(3))
        Assertions.assertEquals(false, ms.dirty)

        Assertions.assertEquals(false, ms.dirty)
        Assertions.assertEquals(false, ms.contains(4))
        Assertions.assertEquals(false, ms.dirty)
    }

    @Test
    fun iterator() {
        val ms = MarkerSet.of(1, 2, 3)
        val iter = ms.iterator()

        Assertions.assertInstanceOf(MutableIterator::class.java, iter)
        Assertions.assertEquals(true, iter.hasNext())
        Assertions.assertEquals(false, ms.dirty)

        Assertions.assertEquals(1, iter.next())
        Assertions.assertEquals(false, ms.dirty)

        iter.remove()
        Assertions.assertEquals(true, ms.dirty)
        Assertions.assertEquals(setOf(2, 3), ms)
    }

    @Test
    fun testEquals() {
        val ms = MarkerSet.of<Int>()
        Assertions.assertEquals(setOf<Int>(), ms)
        Assertions.assertEquals(ms, setOf<Int>())

        val ms1 = MarkerSet.of(1,2,3)
        Assertions.assertEquals(setOf(1,2,3), ms1)
        Assertions.assertEquals(ms1, setOf(1,2,3))
    }
}