package com.hitchhikerprod.advent2021.day22;

import java.util.HashSet;
import java.util.Set;

public record Cube(Range x, Range y, Range z) {
    public long cardinality() {
        return (long) x.cardinality() * y.cardinality() * z.cardinality();
    }

    public Cube overlaps(Cube that) {
        final Range dx = this.x().overlaps(that.x());
        if (dx == null) { return null; }

        final Range dy = this.y().overlaps(that.y());
        if (dy == null) { return null; }

        final Range dz = this.z().overlaps(that.z());
        if (dz == null) { return null; }

        return new Cube(dx, dy, dz);
    }

    public Set<Cube> minus(Cube that) {
        final Cube overlap = this.overlaps(that);
        if (overlap == null) { return Set.of(this); }

        final Set<Cube> cubes = new HashSet<>();

        final Range[] xr = new Range[] {
                this.x().below(overlap.x()),
                overlap.x(),
                this.x().above(overlap.x())
        };

        final Range[] yr = new Range[] {
                this.y().below(overlap.y()),
                overlap.y(),
                this.y().above(overlap.y())
        };

        final Range[] zr = new Range[] {
                this.z().below(overlap.z()),
                null,
                this.z().above(overlap.z())
        };

        if (xr[0] != null) cubes.add(new Cube(xr[0], this.y(), this.z()));
        if (yr[0] != null) cubes.add(new Cube(xr[1], yr[0], this.z()));
        if (zr[0] != null) cubes.add(new Cube(xr[1], yr[1], zr[0]));
        if (zr[2] != null) cubes.add(new Cube(xr[1], yr[1], zr[2]));
        if (yr[2] != null) cubes.add(new Cube(xr[1], yr[2], this.z()));
        if (xr[2] != null) cubes.add(new Cube(xr[2], this.y(), this.z()));
        return cubes;
    }
}
