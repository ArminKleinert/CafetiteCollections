package cafetite.markable

class MarkerList<T> : AbstractMutableList<T> {
    companion object {
        fun <T> of(vararg xs: T) = MarkerList(xs.toMutableList())
    }
    private val internalMap: MutableList<T>
    var dirty = false

    constructor(m: List<T>) {
        internalMap = m.toMutableList()
    }

    constructor(m: Collection<T>) {
        internalMap = m.toMutableList()
    }

    constructor() {
        internalMap = mutableListOf()
    }

    override val size: Int
        get() = internalMap.size

    override fun add(index: Int, element: T) {
        internalMap.add(index, element)
        dirty = true
    }

    override fun get(index: Int): T =
        internalMap[index]

    override fun removeAt(index: Int): T {
        val temp = internalMap.removeAt(index)
        dirty = true
        return temp
    }

    override fun set(index: Int, element: T): T {
        val temp = internalMap.set(index, element)
        dirty = true
        return temp
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return internalMap == other
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + internalMap.hashCode()
        return result
    }

    override fun toString(): String =
        internalMap.toString()
}