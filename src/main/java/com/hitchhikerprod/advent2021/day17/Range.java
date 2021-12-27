package com.hitchhikerprod.advent2021.day17;

import java.util.Optional;
import java.util.stream.Stream;

record Range(Integer min, Optional<Integer> max) {
    public boolean includes(Integer point) {
        return min <= point && (max.isEmpty() || max.get() >= point);
    }

    public Range overlaps(Range that) {
        final Integer largestMin = Stream.of(this.min(), that.min()).max(Integer::compare).orElseThrow();
        if (this.max().isEmpty() && that.max().isEmpty()) {
            return new Range(largestMin, Optional.empty());
        }

        final Optional<Integer> smallestMax;
        if (this.max().isEmpty()) smallestMax = that.max();
        else if (that.max().isEmpty()) smallestMax = this.max();
        else smallestMax = Optional.of(
                    Stream.of(this.max(), that.max()).map(Optional::get).min(Integer::compare).orElseThrow());

        if (smallestMax.isPresent() && smallestMax.get() < largestMin) {
            return null;
        }
        return new Range(largestMin, smallestMax);
    }
}
