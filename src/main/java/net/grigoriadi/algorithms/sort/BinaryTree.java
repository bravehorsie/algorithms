package net.grigoriadi.algorithms.sort;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Structure implementing a binary tree.
 * Each element has left and right, left is for a value smaller than current and right is for greater.
 * The rule of left and right above applies recursively.
 * @param <T> A comparable type
 */
public class BinaryTree<T extends Comparable<T>> {

    private enum LinkType {
        LEFT,RIGHT
    }


    private static class Node<T extends Comparable<T>> {
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;

        private final T value;

        public Node(Node<T> parent, T value) {
            this.parent = parent;
            this.value = value;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
            if (left != null) {
                left.parent = this;
            }
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
            this.right = right;
            if (right != null) {
                right.parent = this;
            }
        }

        public T getValue() {
            return value;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }
    }

    private Node<T> root;

    public void put(T value) {
        if (root == null) {
            root = new Node<>(null, value);
            return;
        }
        put(root, value);
    }

    private void put(Node<T> node, T value) {
        int result = value.compareTo(node.getValue());
        if (result == 0) {
            System.out.println("WARNING: encountered existing value: "+value);
        } else if (result < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new Node<>(node, value));
                return;
            }
            put(node.getLeft(), value);
        } else {
            if (node.getRight() == null) {
                node.setRight(new Node<>(node, value));
                return;
            }
            put(node.getRight(), value);
        }
    }

    private void pushNode(Node<T> node, Node<T> pushed) {
        int result = pushed.getValue().compareTo(node.getValue());
        if (result == 0) {
            throw new IllegalStateException("Pushed node already exists");
        } else if (result < 0) {
            if (node.getLeft() == null) {
                node.setLeft(pushed);
                return;
            }
            pushNode(node.getLeft(), pushed);
        } else {
            if (node.getRight() == null) {
                node.setRight(pushed);
                return;
            }
            pushNode(node.getRight(), pushed);
        }
    }

    public Collection<T> getOrdered() {
        ArrayDeque<T> queue = new ArrayDeque<>();
        visit(root, queue);
        return queue;
    }

    /**
     * Visit the nodes and put the value on the way back, so it is in the reverse order.
     * @param node current node
     * @param queue ordered elements
     */
    public void visit(Node<T> node, Deque<T> queue) {
        if (node.getLeft() != null) {
            visit(node.getLeft(), queue);
        }
        queue.addLast(node.getValue());
        if (node.getRight() != null) {
            visit(node.getRight(), queue);
        }
    }

    /**
     * Get a value that is equal to or less, closest to provided value.
     * @param value value to search floor for
     * @return a floor of the value
     */
    public T floor(T value) {
        return floor(root, value).getValue();
    }

    private Node<T> floor(Node<T> current, T value) {
        int cmp = value.compareTo(current.getValue());
        if (cmp == 0) {
            return current;
        }
        if (cmp < 0) {
            if (current.getLeft() == null) {
                return current;
            }
            return floor(current.left, value);
        } else {
            //value is greater
            if (current.getRight() == null) {
                return current;
            }
            Node<T> rightResult = floor(current.getRight(), value);
            if (rightResult.getValue().compareTo(value) > 0) {
                return current;
            }
            return rightResult;
        }
    }

    public void delete(T value) {
        if (!this.contains(value)) {
            return;
        }
        delete(root, value);
    }

    /**
     * This is a custom algorithm implementation. A user manual try.
     * There is more efficient and shorter implementation, with heavier use of recursion,
     * pointed out in the algorithm classics.
     */
    private void delete(Node<T> current, T value) {
        int cmp = value.compareTo(current.getValue());
        if (cmp == 0) {
            if (current.getLeft() == null && current.getRight() == null) {
                if (current.getParent() != null) {
                    replaceInParent(current, null);
                } else {
                    root = null;
                }
            } else if (current.getLeft() == null) {
                replaceInParent(current, current.getRight());
            } else if (current.getRight() == null) {
                replaceInParent(current, current.getLeft());
            } else {
                //we are looking for the node with a null left link on the righ side of current node,
                // which is basically the smallest node in tree of the right node
                Node<T> minNode = selectMin(current.getRight());

                //if minNode is referenced with left link and minNode is not a direct ancestor of the removed node
                boolean minNodeIsDirectAncestor = minNode.getParent() == current;

                //switch links:
                //backup the right of the min node
                Node<T> minNodeRightOld = minNode.getRight();

                if (!minNodeIsDirectAncestor) {
                    //set the node in the right link of removed node
                    //to be right link of minNode
                    minNode.setRight(current.getRight());
                    // remove the minNode from the hierarchy (since it is min we are sure link is left)
                    minNode.getParent().setLeft(null);
                }

                if (current.getLeft() != null) {
                    minNode.setLeft(current.getLeft());
                }

                replaceInParent(current, minNode);
                if (minNodeRightOld != null && !minNodeIsDirectAncestor) {
                    //push the (old) minNode rightLink to the proper position (likely the leftmost link in subtree)
                    pushNode(minNode.getRight(), minNodeRightOld);
                }
            }
        } else if (cmp < 0) {
            delete(current.getLeft(), value);
        } else {
            //cpm > 0
            delete(current.getRight(), value);
        }
    }

    private void replaceInParent(Node<T> current, Node<T> newNode) {
        LinkType parentLinkType = getParentLinkType(current);
        //if its null than its root
        if (parentLinkType != null) {
            if (LinkType.LEFT == parentLinkType) {
                current.getParent().setLeft(newNode);
            } else if (LinkType.RIGHT == parentLinkType) {
                current.getParent().setRight(newNode);
            }
        } else {
            root = newNode;
            root.setParent(null);
        }
    }

    /**
     * Selects either min node, or the right node of the min node.
     * Intended to be used as a replacement node  in delete operation.
     *
     * @param current subject
     * @return found node
     */
    private Node<T> selectMin(Node<T> current) {
        if (current.getLeft() == null) {
            return current;
        }
        return selectMin(current.getLeft());
    }

    public void printBFS() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        Deque<Node<T>> deck = new ArrayDeque<>();
        deck.add(root);
        printBFS(deck, 0);
    }

    public void printBFS(Deque<Node<T>> deck, int level) {
        Node<T> next;
        var nextQueue = new ArrayDeque<Node<T>>();
        StringBuffer sb = new StringBuffer();
        deck.forEach(node -> {
            sb.append(node.getValue());
            if (node.getParent() != null) {
                sb.append("(")
                .append(node.getParent().getValue())
                .append(")");
            }
            sb.append(", ");
        });

        printMessage(level, sb.toString());
        while (( next = deck.pollFirst()) != null) {
            if (next.getLeft() != null) {
                nextQueue.addLast(next.getLeft());
            }
            if (next.getRight() != null) {
                nextQueue.addLast(next.getRight());
            }
        }
        if (!nextQueue.isEmpty()) {
            printBFS(nextQueue, level+1);
        }
    }

    private void printMessage(int level, String msg) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(msg);
    }

    public boolean contains(T element) {
        if (root == null) {
            return false;
        }
        return search(root, element) != null;
    }

    private Node<T> search(Node<T> node, T element) {
        int cmp = element.compareTo(node.getValue());
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            if (node.getLeft() == null) {
                return null;
            }
            return search(node.getLeft(), element);
        } else {
            if (node.getRight() == null) {
                return null;
            }
            return search(node.getRight(), element);
        }
    }

    private LinkType getParentLinkType(Node<T> node) {
        if (node.getParent() == null) {
            return null;
        }
        if (node.getParent().getLeft() != null && node.getParent().getLeft().getValue().equals(node.getValue())) {
            return LinkType.LEFT;
        }
        if (node.getParent().getRight() != null && node.getParent().getRight().getValue().equals(node.getValue())) {
            return LinkType.RIGHT;
        }
        return null;
    }

    public Integer size() {
        AtomicInteger count = new AtomicInteger();
        visitNode(root, node -> count.incrementAndGet());
        return count.get();
    }

    private void visitNode(Node<T> current, NodeVisitor<T> visitor) {
        if (current == null) {
            return;
        }
        visitor.visitNode(current);
        visitNode(current.getLeft(), visitor);
        visitNode(current.getRight(), visitor);
    }

    private interface NodeVisitor<T extends Comparable<T>> {
        void visitNode(Node<T> node);
    }
}
