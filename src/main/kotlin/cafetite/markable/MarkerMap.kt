package cafetite.markable

class MarkerMap<K, V> : MutableMap<K, V>, MutableIterable<MutableMap.MutableEntry<K,V>> {
    companion object {
        fun <K, V> of(vararg pairs: Pair<K, V>) = MarkerMap(pairs.toMap().toMutableMap())
    }

    private val internalMap: MutableMap<K, V>
    var dirty = false

    constructor(m: Map<K, V>) {
        internalMap = m.toMutableMap()
    }

    constructor(m: Collection<Pair<K, V>>) {
        internalMap = m.toMap().toMutableMap()
    }

    constructor() {
        internalMap = mutableMapOf()
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> get() = MarkerSetWrapper(internalMap.entries, ::mark)
    override val keys: MutableSet<K> get() = MarkerSetWrapper(internalMap.keys, ::mark)
    override val size: Int get() = internalMap.size
    override val values: MutableCollection<V> get() = MarkerList(internalMap.values, ::mark)

    override fun clear() {
        dirty = true
        internalMap.clear()
    }

    override fun remove(key: K): V? {
        dirty = true
        return internalMap.remove(key)
    }

    override fun putAll(from: Map<out K, V>) {
        dirty = true
        return internalMap.putAll(from)
    }

    override fun put(key: K, value: V): V? {
        dirty = true
        return internalMap.put(key, value)
    }

    override fun isEmpty(): Boolean = internalMap.isEmpty()
    override fun get(key: K): V? = internalMap[key]
    override fun containsValue(value: V): Boolean = internalMap.containsValue(value)
    override fun containsKey(key: K): Boolean = internalMap.containsKey(key)

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> =
        entries.iterator()

    @Suppress("NOTHING_TO_INLINE")
    private inline fun mark() {
        dirty = true
    }

    private class MarkerSetWrapper<T>(private val values: MutableSet<T>, private val mark: () -> Unit) : MutableSet<T> {
        override fun add(element: T): Boolean {
            mark()
            return values.add(element)
        }

        override fun addAll(elements: Collection<T>): Boolean {
            mark()
            return values.addAll(elements)
        }

        override val size: Int
            get() = values.size

        override fun clear() {
            mark()
            return values.clear()
        }

        override fun retainAll(elements: Collection<T>): Boolean {
            mark()
            return values.retainAll(elements.toSet())
        }

        override fun removeAll(elements: Collection<T>): Boolean {
            mark()
            return values.removeAll(elements.toSet())
        }

        override fun remove(element: T): Boolean {
            mark()
            return values.remove(element)
        }

        override fun isEmpty(): Boolean = values.isEmpty()
        override fun containsAll(elements: Collection<T>): Boolean = values.containsAll(elements)
        override fun contains(element: T): Boolean = values.contains(element)
        override fun iterator(): MutableIterator<T> = MarkerIterator(values.iterator(), mark)
    }

    private class MarkerList<T>(private val values: MutableCollection<T>, val mark: () -> Unit) : MutableCollection<T> {
        override val size: Int
            get() = values.size

        override fun clear() {
            mark()
            values.clear()
        }

        override fun addAll(elements: Collection<T>): Boolean {
            mark()
            return values.addAll(elements)
        }

        override fun add(element: T): Boolean {
            mark()
            return values.add(element)
        }

        override fun retainAll(elements: Collection<T>): Boolean {
            mark()
            return values.retainAll(elements.toSet())
        }

        override fun removeAll(elements: Collection<T>): Boolean {
            mark()
            return values.removeAll(elements.toSet())
        }

        override fun remove(element: T): Boolean {
            mark()
            return values.remove(element)
        }

        override fun isEmpty(): Boolean = values.isEmpty()
        override fun containsAll(elements: Collection<T>): Boolean = values.containsAll(elements)
        override fun contains(element: T): Boolean = values.contains(element)
        override fun iterator(): MutableIterator<T> = MarkerIterator(values.iterator(), mark)
    }
}
