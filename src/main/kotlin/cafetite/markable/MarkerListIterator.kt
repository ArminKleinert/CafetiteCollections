package cafetite.markable

internal class MarkerListIterator<T>(val iterator: MutableListIterator<T>, val mark: () -> Unit) : MutableListIterator<T> {
    override fun add(element: T) {
        mark()
        return iterator.add(element)
    }

    override fun next(): T {
        mark()
        return iterator.next()
    }

    override fun previous(): T {
        mark()
        return iterator.previous()}

    override fun remove() {
        mark()
        return iterator.remove()
    }

    override fun set(element: T) {
        mark()
        return iterator.set(element)
    }

    override fun hasNext(): Boolean = iterator.hasNext()
    override fun hasPrevious(): Boolean =iterator.hasPrevious()
    override fun nextIndex(): Int = iterator.nextIndex()
    override fun previousIndex(): Int = iterator.previousIndex()
}
