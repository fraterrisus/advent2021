package com.hitchhikerprod.advent2021.day12;

import java.util.List;
import java.util.Objects;

public class Node {
    private final String name;
    private List<Node> neighbors;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public List<Node> getNeighbors() {
        return this.neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Node && this.name.equals(((Node)obj).name));
    }

    @Override
    public String toString() {
        final String n = (neighbors == null) ? "0" : String.valueOf(neighbors.size());
        return "Node[name=\"" + this.name + "\",neighbors=" + n + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
