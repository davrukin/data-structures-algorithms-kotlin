package structs.queues

class Dequeue<T> {

    private val values = mutableListOf<T>()

    val isEmpty: Boolean = values.isEmpty()

    val count: Int = values.size

    fun enqueue(element: T) {
        values.add(element)
    }

    fun enqueueFront(element: T) {
        values.add(0, element)
    }

    fun dequeue(): T? {
        return values.removeFirstOrNull()
    }

    fun dequeueBack(): T? {
        return values.removeLastOrNull()
    }

    fun peekFront(): T? {
        return values.firstOrNull()
    }

    fun peekBack(): T? {
        return values.lastOrNull()
    }
}