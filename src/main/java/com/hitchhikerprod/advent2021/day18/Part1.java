package com.hitchhikerprod.advent2021.day18;

import java.util.ArrayList;
import java.util.List;

public class Part1 {
    private final List<Snumber> snumbers;

    public Part1(DataProvider provider) {
        this.snumbers = provider.getSnumbers();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day18-explode.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        List<Snumber> worklist = new ArrayList<>(snumbers);
        Snumber total = worklist.remove(0);
        while (!worklist.isEmpty()) {
            total = add(total, worklist.remove(0));
        }
        return total.magnitude();
    }

    private Snumber add(Snumber left, Snumber right) {
        Spair newSnumber = new Spair(left, right);
        while(true) {
            if (!checkForExplosion(newSnumber) && !checkForSplit(newSnumber)) { break; }
        }
        return newSnumber;
    }

    private boolean checkForExplosion(Snumber num) {
        return explodeHelper(List.of(num));
    }

    private List<Snumber> listBuilder(List<Snumber> list, Snumber node) {
        final List<Snumber> l = new ArrayList<>(list);
        l.add(node);
        return List.copyOf(l);
    }

    private boolean explodeHelper(List<Snumber> path) {
        final Snumber last = path.get(path.size() - 1);
        if (!last.isPair()) return false;
        final Spair lastPair = (Spair)last;

        if (path.size() > 4) {
            assert(!lastPair.left().isPair());
            addToLeft(path, (Sscalar)lastPair.left());
            assert(!lastPair.right().isPair());
            addToRight(path, (Sscalar)lastPair.right());
            // replace this node with Sscalar(0)
            return true;
        }

        if (explodeHelper(listBuilder(path, lastPair.left()))) {
            return true;
        }
        if (explodeHelper(listBuilder(path, lastPair.right()))) {
            return true;
        }
        return false;
    }

    private void addToLeft(List<Snumber> path, Sscalar value) {
        int index = path.size() - 2;
        while (index >= 0) {
            index--;
            if (index == -1) { return; }
            final Spair sparent = (Spair)path.get(index);
            final Snumber schild = path.get(index+1);
            if (schild.equals(sparent.right())) { break; }
        }
        Spair sparent = (Spair)path.get(index);
        Snumber node = sparent.left();
        if (node.isPair()) {

        }


        while (true) {
            if (node.isPair()) {
                sparent = (Spair)node;
                node = sparent.right();
            } else {

            }

        }

        return value;
    }

    private Sscalar addToRight(List<Snumber> path, Sscalar value) {
        return value;
    }

    private boolean checkForSplit(Snumber num) {
        return false;
    }
}
