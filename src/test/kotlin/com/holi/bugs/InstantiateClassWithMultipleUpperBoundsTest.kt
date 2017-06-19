package com.holi.bugs

import com.natpryce.hamkrest.assertion.assert;
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class InstantiateClassWithMultipleUpperBoundsTest {
    open class Node;
    class BinaryTree<T>(val left: T, val right: T) where T : Node, T : Cloneable {
        companion object {
            operator fun <L, R> invoke(left: L, right: R): BinaryTree<*> where  L : Node, L : Cloneable, R : Node, R : Cloneable {
                @Suppress("UNCHECKED_CAST")
                return BinaryTree(left, right as L);
            }
        }
    };
    class Branch : Node(), Cloneable;
    class Leaf : Node(), Cloneable;

    @Test
    fun `can't instantiate a class with multi upper bounds directly`() {
        val branch = Branch();
        val leaf = Leaf();

        val tree: BinaryTree<*> = BinaryTree(branch, leaf);

        assert.that(tree.left, equalTo<Node>(branch));
        assert.that(tree.right, equalTo<Node>(leaf));

    }
}
