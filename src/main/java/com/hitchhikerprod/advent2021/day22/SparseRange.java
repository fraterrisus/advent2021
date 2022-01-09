package com.hitchhikerprod.advent2021.day22;

import java.util.Set;
import java.util.TreeSet;

public class SparseRange {
    private final long xMin;
    private final long xMax;
    private final long yMin;
    private final long yMax;
    private final long zMin;
    private final long zMax;

    private final TreeSet<InflectionPoint> inflections;

    private record InflectionPoint(long index, boolean lit) implements Comparable<InflectionPoint> {
        @Override
        public int compareTo(InflectionPoint that) {
            return Long.compare(this.index(), that.index());
        }

        public static InflectionPoint on(long index) { return new InflectionPoint(index, true); }
        public static InflectionPoint off(long index) { return new InflectionPoint(index, false); }
    }

    public SparseRange(long xMin, long xMax, long yMin, long yMax, long zMin, long zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;

        this.inflections = new TreeSet<>();
    }

    public long cardinality() {
        long count = 0;
        InflectionPoint last = new InflectionPoint(0, false);
        for (var point : inflections) {
            if (last.lit()) {
                count += (point.index() - last.index());
            }
            last = point;
        }
        if (last.lit()) {
            count += maxIndex() + 1 - last.index();
        }
        return count;
    }

    public boolean getPoint(long x, long y, long z) {
        final long index = index(x, y, z);
        final InflectionPoint point = new InflectionPoint(index, true);
        final InflectionPoint lower = inflections.floor(point);
        if (lower == null) { return false; }
        return lower.lit();
    }

    public void setRange(long x0, long x1, long y, long z) {
        final long index0 = index(x0, y, z);
        final long index1 = index(x1, y, z);
        final InflectionPoint p0 = InflectionPoint.on(index0);
        final InflectionPoint p1 = InflectionPoint.off(index1 + 1);

        final InflectionPoint lower = inflections.lower(p0); // <
        final InflectionPoint upper = inflections.higher(p1); // >

        final Set<InflectionPoint> insidePoints = inflections.subSet(p0, true, p1, true);
        inflections.removeAll(Set.copyOf(insidePoints));

        if (lower == null || !lower.lit()) {
            // Points to our left are OFF; add an ON point here.
            inflections.add(p0);
        }

        if (upper == null || upper.lit()) {
            inflections.add(p1);
        }
    }

    public void clearRange(long x0, long x1, long y, long z) {
        final long index0 = index(x0, y, z);
        final long index1 = index(x1, y, z);
        final InflectionPoint p0 = InflectionPoint.off(index0);
        final InflectionPoint p1 = InflectionPoint.on(index1 + 1);

        final InflectionPoint lower = inflections.lower(p0); // <
        final InflectionPoint upper = inflections.higher(p1); // >

        final Set<InflectionPoint> insidePoints = inflections.subSet(p0, true, p1, true);
        inflections.removeAll(Set.copyOf(insidePoints));

        if (lower != null && lower.lit()) {
            inflections.add(p0);
        }

        if (upper != null && !upper.lit()) {
            inflections.add(p1);
        }
    }

    // Setting (5,ON) if the LOWER,HIGHER points are...
    // (5,ON), ...          -> do nothing
    // (5,OFF), (6,ON)      -> remove (5,OFF), remove (6,ON)
    // (5,OFF), (7+,ON)     -> remove (5,OFF), add (6,OFF)
    // (5,OFF), null        -> remove (5,OFF), add (6,OFF)
    // (4-,ON), ...         -> do nothing
    // (4-,OFF), (6,ON)     -> add (5,ON), remove (6,ON)
    // (4-,OFF), (7+,ON)    -> add (5,ON), add (6,OFF)
    // (4-,OFF), null       -> add (5,ON), add (6,OFF)
    // null, (6,ON)         -> add (5,ON), remove (6,ON)
    // null, (7+,ON)        -> add (5,ON), add (6,OFF)
    // null, null           -> add (5,ON), add (6,OFF)
    public void setPoint(long x, long y, long z) {
        final long index = index(x,y,z);
        final InflectionPoint point = new InflectionPoint(index, true);


        final InflectionPoint lower = inflections.floor(point); // <=
        final InflectionPoint upper = inflections.higher(point); // >

        if (lower == null) {
            // point i-1 is OFF; add ON here
            inflections.add(point);
        } else {
            if (lower.lit()) {
                // point i is already ON, so do nothing; don't even look at the upper point
                return;
            } else {
                if (lower.index() == index) {
                    // (i,OFF) existed; remove it
                    inflections.remove(lower);
                } else {
                    // point i-1 is OFF; add (i,ON)
                    inflections.add(point);
                }
            }
        }

        // if we get this far, lower.lit() == false which implies upper.lit() == true
        if (upper == null || upper.index() > index + 1) {
            // point i+1 was OFF until we added (i,ON), so also add (i+1,OFF)
            if (index < maxIndex()) {
                inflections.add(new InflectionPoint(index + 1, false));
            }
        } else {
            // (i+1,ON) existed; we don't need that anymore because now (i,ON)
            inflections.remove(upper);
        }
    }

    public void clearPoint(long x, long y, long z) {
        final long index = index(x,y,z);
        final InflectionPoint point = new InflectionPoint(index, false);

        final InflectionPoint lower = inflections.floor(point); // <=
        final InflectionPoint upper = inflections.higher(point); // >

        if (lower == null || !lower.lit()) {
            // point i is already OFF, so do nothing; don't even look at UPPER
            return;
        } else {
            if (lower.index() == index) {
                // (i, ON) existed; remove it
                inflections.remove(lower);
            } else {
                // point i-1 is ON; add (i,OFF)
                inflections.add(point);
            }
        }

        // if we get this far, lower.lit() == true which implies upper.lit() == false
        if (upper == null || upper.index() > index + 1) {
            // point i+1 was ON until we added (i,OFF), so also add (i+1,ON)
            if (index < maxIndex()) {
                inflections.add(new InflectionPoint(index + 1, true));
            }
        } else {
            // (i+1,OFF) already existed; we don't need that anymore because noe (i,OFF)
            inflections.remove(upper);
        }
    }

    private long index(long x, long y, long z) {
        final long yDim = yMax - yMin + 1;
        final long xDim = xMax - xMin + 1;

        return ((z-zMin) * xDim * yDim) +
                ((y-yMin) * xDim) +
                (x-xMin);
    }

    private long maxIndex() {
        return index(xMax, yMax, zMax);
    }
}
