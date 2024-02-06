package structs.trees

import kotlin.math.max

class BinarySearchTree<T : Comparable<T>> {
    var value: T

    constructor(value: T) {
        this.value = value
    }

    constructor(values: Array<T>) {
        if (values.isNotEmpty()) {
            this.value = values[0]
            values.drop(1).forEach { insert(it) }
        } else {
            throw IllegalArgumentException()
        }
    }

    var parent: BinarySearchTree<T>? = null
        private set

    var left: BinarySearchTree<T>? = null
        private set

    var right: BinarySearchTree<T>? = null
        private set

    val isRoot: Boolean = parent == null
    val isLeaf: Boolean = left == null && right == null
    val isLeftChild: Boolean = parent?.left == this
    val isRightChild: Boolean = parent?.right == this
    val hasLeftChild: Boolean = left != null
    val hasRightChild: Boolean = right != null
    val hasAnyChild: Boolean = hasLeftChild || hasRightChild
    val hasBothChildren: Boolean = hasLeftChild && hasRightChild

    val count: Int = (left?.count ?: 0) + (right?.count ?: 0) + 1

    fun insert(value: T) {
        if (value < this.value) {
            left?.let {
                it.insert(value)
            } ?: {
                left = BinarySearchTree(value)
                left?.parent = this
            }
        } else {
            right?.let {
                it.insert(value)
            } ?: {
                right = BinarySearchTree(value)
                right?.parent = this
            }
        }
    }

    fun searchRecursive(value: T): BinarySearchTree<T>? {
        return if (value < this.value) {
            left?.searchRecursive(value)
        } else if (value > this.value) {
            right?.searchRecursive(value)
        } else {
            this // found it!
        }
    }

    fun searchIterative(value: T): BinarySearchTree<T>? { // TODO: need to test
        var node: BinarySearchTree<T>? = this
        while (node != null) {
            node = if (value < node.value) {
                node.left
            } else if (value > node.value) {
                node.right
            } else {
                return node
            }
        }
        return null
    }

    fun traversePreOrder(block: (T) -> Unit) {
        block.invoke(value)
        left?.traversePreOrder(block)
        right?.traversePreOrder(block)
    }

    fun traverseInOrder(block: (T) -> Unit) {
        left?.traverseInOrder(block)
        block.invoke(value)
        right?.traverseInOrder(block)
    }

    fun traversePostOrder(block: (T) -> Unit) {
        left?.traversePostOrder(block)
        right?.traversePostOrder(block)
        block.invoke(value)
    }

    fun <R> mapToList(mapper: (T) -> R): List<R> {
        val list = mutableListOf<R>()
        left?.map {
            list.add(mapper.invoke(it))
        }
        list.add(mapper.invoke(value))
        right?.map {
            list.add(mapper.invoke(it))
        }
        return list
    }

    fun <R : Comparable<R>> map(mapper: (T) -> R): BinarySearchTree<R> {
        val tree = BinarySearchTree(mapper.invoke(value))
        left?.let {
            tree.insert(mapper.invoke(it.value))
        }
        tree.insert(mapper.invoke(value))
        right?.let {
            tree.insert(mapper.invoke(it.value))
        }
        return tree
    }

    // TODO: filter

    private fun reconnectParentTo(node: BinarySearchTree<T>?) {
        parent?.let {
            if (isLeftChild) {
                it.left = node
            } else {
                it.right = node
            }
        }

        node?.parent = parent
    }

    fun minimum(): BinarySearchTree<T> {
        var node = this
        while (node.left != null) {
            node = node.left!!
        }
        return node
    }

    fun maximum(): BinarySearchTree<T> {
        var node = this
        while (node.right != null) {
            node = node.right!!
        }
        return node
    }

    fun remove(): BinarySearchTree<T>? {
        // replacement for current node can be either biggest one on the left or
        // smallest one on the right, whichever is not null
        val replacement = right?.minimum() ?: left?.maximum()
        replacement?.remove()

        // place the replacement on current node's position
        replacement?.right = right
        replacement?.left = left
        right?.parent = replacement
        left?.parent = replacement
        reconnectParentTo(replacement)

        // the current node is no longer part of the tree, so clean it up
        parent = null
        left = null
        right = null

        return replacement
    }

    val height: Int = if (isLeaf) {
        0
    } else {
        max(left?.height ?: 0, right?.height ?: 0) + 1
    }

    val depth: Int
        get() {
            var node = this
            var edges = 0

            while (node.parent != null) {
                node = node.parent!!
                edges++
            }

            return edges
        }

    fun predecessor(): BinarySearchTree<T>? {
        return if (left != null) {
            left?.maximum()
        } else {
            var node = this
            while (node.parent != null) {
                if (node.parent!!.value < value) {
                    return node.parent
                }
                node = node.parent!!
            }
            return null
        }
    }

    fun successor(): BinarySearchTree<T>? {
        return if (right != null) {
            right?.minimum()
        } else {
            var node = this
            while (node.parent != null) {
                if (node.parent!!.value > value) {
                    return node.parent
                }
                node = node.parent!!
            }
            return null
        }
    }

    fun isValid(minValue: T, maxValue: T): Boolean {
        if (value < minValue || value > maxValue) return false
        val leftBST = left?.isValid(minValue, value) ?: true
        val rightBST = right?.isValid(value, maxValue) ?: true
        return leftBST && rightBST
    }

    override fun toString(): String {
        return buildString {
            left?.let {
                append("$left <- ")
            }
            append(value)
            right?.let {
                append(" -> $right")
            }
        }
    }
}