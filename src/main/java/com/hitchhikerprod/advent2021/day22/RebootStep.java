package com.hitchhikerprod.advent2021.day22;

public record RebootStep(Operation op, Range x, Range y, Range z) {
    public enum Operation { ON, OFF; }
}
