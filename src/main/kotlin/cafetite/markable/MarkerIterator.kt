package cafetite.markable

internal class MarkerIterator<T>(val iterator: MutableIterator<T>, val mark: () -> Unit) : MutableIterator<T> {
    override fun hasNext(): Boolean = iterator.hasNext()

    override fun next(): T {
        return iterator.next()
    }

    override fun remove() {
        mark()
        return iterator.remove()
    }
}
