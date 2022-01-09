package com.hitchhikerprod.advent2021.day22;

import java.util.stream.Stream;

public record Range(int min, int max) {
    public int cardinality() {
        return 1 + max - min;
    }

    public boolean includes(int point) {
        return min <= point && max >= point;
    }

    public Range overlaps(Range that) {
        final int largestMin = Stream.of(this.min(), that.min()).max(Integer::compare).orElseThrow();
        final int smallestMax = Stream.of(this.max(), that.max()).min(Integer::compare).orElseThrow();

        if (smallestMax < largestMin) {
            return null;
        }
        return new Range(largestMin, smallestMax);
    }

    public Range below(Range that) {
        if (this.min() >= that.min()) { return null; }
        return new Range(this.min(), that.min() - 1);
    }

    public Range above(Range that) {
        if (this.max() <= that.max()) { return null; }
        return new Range(that.max() + 1, this.max());
    }
}
