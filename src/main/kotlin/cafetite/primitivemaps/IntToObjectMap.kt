package cafetite.primitivemaps

class IntToObjectMap <V> : Map<Int, V> {

    private val intKeys: IntArray
    private val objValues: Array<Any?>

    override val entries: Set<Map.Entry<Int, V>>
        get() {
            val res = mutableSetOf<Entry<Int, V>>()
            for (i in 0..intKeys.size) {
                @Suppress("UNCHECKED_CAST")
                res.add(Entry(intKeys[i], objValues[i] as V))
            }
            return res
        }

    override val keys: Set<Int>
        get() = intKeys.toSet()
    override val size: Int
        get()=intKeys.size
    override val values: Collection<V>
        @Suppress("UNCHECKED_CAST")
        get() = objValues.toList() as Collection<V>

    constructor(pairs : Collection<Pair<Int, V>>) {
        val mEntries = pairs.sortedBy { (k, _) -> k }
        intKeys = IntArray(pairs.size)
        objValues = Array(pairs.size) { null }

        if (mEntries.isEmpty()) {
            val (ff, fs) = mEntries.first()
            var previous: Int = ff
            intKeys[0] = ff
            objValues[0] = fs
            for ((index, e) in mEntries.withIndex()) {
                if (previous == e.first)
                    continue
                previous = e.first
                intKeys[index] = e.first
                objValues[index] = e.second
            }
        }
    }

    constructor(map: Map<Int, V>) {
        val mEntries = map.entries.sortedBy { (k, _) -> k }
        intKeys = IntArray(map.size)
        objValues = Array(map.size) { null }
        for ((index,e) in mEntries.withIndex()) {
            intKeys[index] = e.key
            objValues[index] = e.value
        }}

    override fun isEmpty(): Boolean = intKeys.isEmpty()
    override fun containsValue(value: V): Boolean = values.contains(value)
    override fun containsKey(key: Int): Boolean = intKeys.binarySearch(key) >= 0

    override fun get(key: Int): V? {
        val i = intKeys.binarySearch(key)
        if (i < 0) return null
        @Suppress("UNCHECKED_CAST")
        return objValues[i] as V
    }

    internal data class Entry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V> {
        override fun toString(): String = "($key, $value)"
    }
}