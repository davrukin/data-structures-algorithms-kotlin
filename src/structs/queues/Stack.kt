package structs.queues

class Stack<T> {

    private val values = mutableListOf<T>()

    val isEmpty: Boolean = values.isEmpty()

    val count: Int = values.size

    fun push(element: T) {
        values.add(element)
    }

    fun pop(): T? {
        return values.removeLastOrNull()
    }

    fun peek(): T? {
        return values.lastOrNull()
    }
}