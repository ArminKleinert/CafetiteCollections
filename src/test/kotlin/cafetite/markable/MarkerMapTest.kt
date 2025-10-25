package cafetite.markable

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions

class MarkerMapTest {

    @Test
    fun getDirty() {
        Assertions.assertEquals(false, MarkerMap.of<Int,Int>().dirty)
        Assertions.assertEquals(false, MarkerMap(mutableSetOf<Pair<Int,Int>>()).dirty)
        Assertions.assertEquals(false, MarkerMap.of(1 to 2, 2 to 3).dirty)

        val mm = MarkerMap.of<Int,Int>()
        Assertions.assertEquals(false,mm.dirty)
        mm.dirty = true
        Assertions.assertEquals(true, mm.dirty)
    }

    @Test
    fun setDirty() {
    }

    @Test
    fun getEntries() {
    }

    @Test
    fun getKeys() {
    }

    @Test
    fun getSize() {
    }

    @Test
    fun getValues() {
    }

    @Test
    fun clear() {
    }

    @Test
    fun remove() {
    }

    @Test
    fun putAll() {
    }

    @Test
    fun put() {
    }

    @Test
    fun isEmpty() {
    }

    @Test
    fun get() {
    }

    @Test
    fun containsValue() {
    }

    @Test
    fun containsKey() {
    }

    @Test
    operator fun iterator() {
    }
}