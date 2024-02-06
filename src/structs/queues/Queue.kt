package structs.queues

class Queue<T> {

    private val values = mutableListOf<T>()

    val isEmpty = values.isEmpty()

    val count = values.size

    fun enqueue(element: T) {
        values.add(element)
    }

    fun dequeue(): T? {
        return values.removeFirstOrNull()
    }

    fun front(): T? {
        return values.firstOrNull()
    }
}
