package cafetite.markable

class MarkerSet<T> : MutableSet<T> {
    companion object {
        fun <T> of(vararg xs: T) = MarkerSet(xs.toMutableSet())
    }

    private val internalMap: MutableSet<T>
    var dirty = false

    constructor(m: Set<T>) {
        internalMap = m.toMutableSet()
    }

    constructor(m: Collection<T>) {
        internalMap = m.toMutableSet()
    }

    constructor() {
        internalMap = mutableSetOf()
    }

    override fun add(element: T): Boolean {
        val modified = internalMap.add(element)
        if (modified) dirty = true
        return modified
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val modified = internalMap.addAll(elements)
        if (modified) dirty = true
        return modified
    }

    override val size: Int
        get() = internalMap.size

    override fun clear() {
        if (isEmpty()) return
        dirty = true
        internalMap.clear()
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val modified = internalMap.retainAll(elements.toSet())
        if (modified) dirty = true
        return modified
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val modified = internalMap.removeAll(elements.toSet())
        if (modified) dirty = true
        return modified
    }

    override fun remove(element: T): Boolean {
        val modified = internalMap.remove(element)
        if (modified) dirty = true
        return modified
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun mark() {
        dirty = true
    }

    override fun isEmpty(): Boolean = internalMap.isEmpty()
    override fun containsAll(elements: Collection<T>): Boolean = internalMap.containsAll(elements)
    override fun contains(element: T): Boolean = internalMap.contains(element)
    override fun iterator(): MutableIterator<T> = MarkerIterator(internalMap.iterator(), ::mark)

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        return internalMap == other
    }

    override fun hashCode(): Int {
        var result = internalMap.hashCode()
        result = 31 * result + dirty.hashCode()
        return result
    }

    override fun toString(): String =
        internalMap.toString()
}