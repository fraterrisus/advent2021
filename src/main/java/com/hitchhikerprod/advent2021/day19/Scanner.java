package com.hitchhikerprod.advent2021.day19;

import java.util.List;

record Scanner(int id, List<Point> beacons, int orientation, Point position) {
    public Scanner(int id, List<Point> beacons) {
        this(id, beacons, UNASSIGNED, null);
    }

    private static final int UNASSIGNED = -1;

    public boolean hasKnownLocation() {
        return (orientation != UNASSIGNED) && (position != null);
    }

    @Override
    public String toString() {
        return "Scanner[id="+id+",orientation="+orientation+",position="+position+"]";
    }
}
