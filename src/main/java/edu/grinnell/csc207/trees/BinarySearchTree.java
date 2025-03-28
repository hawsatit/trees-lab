package edu.grinnell.csc207.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading
    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {

        T value;
        Node<T> left;
        Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() {
    }

    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in the tree
     */
    public int size() {
        return sizeH(root);
    }

    private Node<T> insertH(T value, Node<T> root) {
        if (root == null) {
            return new Node<T>(value);
        } else {
            if (value.compareTo(root.value) < 0) {
                root.left = insertH(value, root.left);
            } else {
                root.right = insertH(value, root.right);
            }
            return root;
        }
    }

    /**
     * @param value the value to add to the tree
     */
    public void insert(T value) {
        root = insertH(value, root);
    }

    ///// Part 1: Traversals
    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> list = new ArrayList<>();
        inorderRec(this.root, list);
        return list;
    }

    private void inorderRec(Node<T> cur, List<T> list) {
        if (cur != null) {
            inorderRec(cur.left, list);
            list.add(cur.value);
            inorderRec(cur.right, list);

        }
    }

    /**
     * @return the elements of this tree collected via a pre-order traversal
     * Check if the data is null (is a leaf) if not the recur on the left and
     * then the right.
     */
    public List<T> toListPreorder() {
        List<T> list = new ArrayList<>();
        preorderRec(this.root, list);
        return list;
    }

    private void preorderRec(Node<T> cur, List<T> list) {
        if (cur != null) {
            list.add(cur.value);
            preorderRec(cur.left, list);
            preorderRec(cur.right, list);

        }
    }

    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> list = new ArrayList<>();
        postorderRec(this.root, list);
        return list;
    }

    private void postorderRec(Node<T> cur, List<T> list) {
        if (cur != null) {
            postorderRec(cur.left, list);
            postorderRec(cur.right, list);
            list.add(cur.value);

        }
    }

    ///// Part 2: Contains
    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        return containsRec(this.root, value, false);
    }

    public boolean containsRec(Node<T> cur, T value, boolean isFound) {
        //if already found do nothing
        if (isFound) {
            return isFound;
        }

        if (cur != null) {
            if (value.compareTo(cur.value) == 0) {
                isFound = true;

            } else {
                isFound = containsRec(cur.left, value, isFound);
                isFound = containsRec(cur.right, value, isFound);
            }
        }
        return isFound;
    }

    ///// Part 3: Pretty Printing
    /**
     * @return a string representation of the tree obtained via an pre-order
     * traversal in the form: "[v0, v1, ..., vn]"
     */
    public String toStringPreorder() {
        String string = "";
        if (this.root != null) {
            string = "[" + this.root.value.toString();
            string += stringRec(root.left) + stringRec(root.right);
            string += "]";
        }
        return string;
    }

    private String stringRec(Node cur) {
        if (cur == null) {
            return "";
        } else {
            return ", " + cur.value.toString() + stringRec(cur.left) + stringRec(cur.right);
        }
    }

    ///// Part 4: Deletion
    /*
     * The three cases of deletion are:
     * 1. (V has both leaves. so just delete v)
     * 2. (Either left or right of v is a leaf so you shift the non leaf)
     * 3. (Neither left or right are leaves so we determine the one with the least complex shift and do as needed)
     */
    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code>
     * found in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        throw new UnsupportedOperationException();
    }

    private boolean searchDelete(Node<T> cur, T value, boolean isDeleted) {
        if (isDeleted) {
            return isDeleted;
        }

        //good to check in curr
        if (cur != null) {
            //if left is good to check
            if (cur.left != null) {
                if (value.compareTo(cur.left.value) == 0) {
                    
                    //first case both leaves
                    if (cur.left.left == null && cur.left.right == null){
                        cur.left = null;
                        
                    //second case one leaf
                    } else if (cur.left.left == null || cur.left.right == null){
                        if (cur.left.left == null){
                            cur.left = cur.left.right;
                        } else {
                            cur.left = cur.left.left;
                        }
                    }
                    isDeleted = true;
                    return isDeleted;
                }

            }
            if (cur.right != null) {
                if (value.compareTo(cur.right.value) == 0) {
                    if (cur.right.left == null && cur.right.right == null){
                        cur.right = null;
                    } else if (cur.right.left == null || cur.right.right == null){
                        if (cur.right.left == null){
                           cur.right = cur.right.right; 
                        } else {
                            cur.right = cur.right.left;
                        }
                    }
                    isDeleted = true;
                    return isDeleted;
                }

            }

            isDeleted = searchDelete(cur.left, value, isDeleted);
            isDeleted = searchDelete(cur.right, value, isDeleted);
        }

        return isDeleted;

    }

    private void performDelete(Node<T> parent,char direction) {
        //Case: Both leaves
        
    }
}
