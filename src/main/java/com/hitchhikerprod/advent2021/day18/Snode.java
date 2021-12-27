package com.hitchhikerprod.advent2021.day18;

import java.util.Objects;

public class Snode {

    // --------- fields

    private Snode parent;
    private Integer scalar;
    private Snode left;
    private Snode right;

    // --------- constructors

    public Snode(int scalar) {
        this.parent = null;
        this.scalar = scalar;
        this.left = null;
        this.right = null;
    }

    public Snode(Snode left, Snode right) {
        this.parent = null;
        this.scalar = null;
        this.left = left;
        this.right = right;
        this.left.setParent(this);
        this.right.setParent(this);
    }

    public static Snode copyOf(Snode that) {
        if (that.isPair()) {
            return new Snode(Snode.copyOf(that.getLeft()), Snode.copyOf(that.getRight()));
        } else {
            return new Snode(that.getScalar());
        }
    }

    // --------- public methods

    public int magnitude() {
        return Objects.requireNonNullElseGet(scalar, () -> (3 * left.magnitude()) + (2 * right.magnitude()));
    }

    public boolean isPair() {
        return scalar == null;
    }

    public boolean explode() {
        return explodeHelper(this, 0);
    }

    public boolean split() {
        return splitHelper(this);
    }

    public static Snode add(Snode left, Snode right) {
        Snode newSnode = new Snode(Snode.copyOf(left), Snode.copyOf(right));
        boolean moreToDo = true;
        while (moreToDo) {
            moreToDo = newSnode.explode() || newSnode.split();
        }
        return newSnode;
    }

    // --------- private helpers

    private static boolean explodeHelper(Snode node, int depth) {
        if (!node.isPair()) return false;

        if (depth == 4) {
            explodeLeft(node.getLeft());
            explodeRight(node.getRight());
            replaceNodeWithZero(node);
            return true;
        }

        return explodeHelper(node.getLeft(), depth + 1) || explodeHelper(node.getRight(), depth + 1);
    }

    private static void explodeLeft(Snode node) {
        Snode ptr = node;
        while (true) {
            final Snode parent = ptr.getParent();
            if (parent == null) { return; }
            if (parent.getRight() == ptr) { break; }
            ptr = parent;
        }
        ptr = ptr.getParent().getLeft();
        while (ptr.isPair()) {
            ptr = ptr.getRight();
        }
        ptr.setScalar(ptr.getScalar() + node.getScalar());
    }

    private static void explodeRight(Snode node) {
        Snode ptr = node;
        while (true) {
            final Snode parent = ptr.getParent();
            if (parent == null) { return; }
            if (parent.getLeft() == ptr) { break; }
            ptr = parent;
        }
        ptr = ptr.getParent().getRight();
        while (ptr.isPair()) {
            ptr = ptr.getLeft();
        }
        ptr.setScalar(ptr.getScalar() + node.getScalar());
    }

    private static boolean splitHelper(Snode node) {
        if (node.isPair()) {
            return splitHelper(node.getLeft()) || splitHelper(node.getRight());
        }

        if (node.getScalar() < 10) { return false; }

        replaceNodeWithPair(node);
        return true;
    }

    private static void replaceNodeWithZero(Snode node) {
        final Snode parent = node.getParent();

        final Snode newChild = new Snode(0);

        replaceChild(parent, node, newChild);
    }

    private static void replaceNodeWithPair(Snode node) {
        final Snode parent = node.getParent();

        final Snode newChild;
        final int div = node.getScalar() / 2;
        if (node.getScalar() % 2 == 0) {
            newChild = new Snode(new Snode(div), new Snode(div));
        } else {
            newChild = new Snode(new Snode(div), new Snode(div+1));
        }

        replaceChild(parent, node, newChild);
    }

    private static void replaceChild(Snode parent, Snode oldChild, Snode newChild) {
        newChild.setParent(parent);
        if (oldChild == parent.getLeft()) {
            parent.setLeft(newChild);
        } else {
            parent.setRight(newChild);
        }
    }

    // --------- getters and setters

    public Snode getParent() {
        return parent;
    }

    public void setParent(Snode parent) {
        this.parent = parent;
    }

    public int getScalar() {
        if (scalar == null) {
            throw new IllegalArgumentException();
        }
        return scalar;
    }

    public void setScalar(Integer scalar) {
        this.scalar = scalar;
    }

    public Snode getLeft() {
        return left;
    }

    public void setLeft(Snode left) {
        this.left = left;
    }

    public Snode getRight() {
        return right;
    }

    public void setRight(Snode right) {
        this.right = right;
    }

    // --------- Object overrides

    @Override
    public String toString() {
        if (scalar == null) {
            return "[" + left.toString() + "," + right.toString() + "]";
        } else {
            return scalar.toString();
        }
    }
}
