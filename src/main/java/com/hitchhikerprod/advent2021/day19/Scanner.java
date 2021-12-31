package com.hitchhikerprod.advent2021.day19;

import java.util.List;

record Scanner(int id, List<Point> beacons, int orientation, Point position) {
    private static final int UNASSIGNED = -1;

    public Scanner(int id, List<Point> beacons) {
        this(id, beacons, UNASSIGNED, null);
    }

    public boolean hasKnownLocation() {
        return (position != null);
    }

    public List<Point> absoluteBeacons(int transformationId) {
        var transform = Point.TRANSFORMATIONS.get(transformationId);
        return beacons.stream().map(p -> p.transform(transform)).toList();
    }

    public List<Point> absoluteBeacons() {
        assert(orientation != UNASSIGNED);
        return absoluteBeacons(orientation);
    }

    @Override
    public String toString() {
        return "Scanner[id="+id+",orientation="+orientation+",position="+position+"]";
    }
}
