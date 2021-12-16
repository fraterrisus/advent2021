package com.hitchhikerprod.advent2021.day05;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Line(Point p0, Point p1) {
    public List<Point> getPointsForCardinalLines() {
        List<Point> points = new ArrayList<>();
        if (p0.x() == p1.x()) {
            var rows = List.of(p0.y(), p1.y());
            for (int i = Collections.min(rows); i <= Collections.max(rows); i++) {
                points.add(new Point(p0.x(), i));
            }
        } else if (p0.y() == p1.y()) {
            var cols = List.of(p0.x(), p1.x());
            for (int i = Collections.min(cols); i <= Collections.max(cols); i++) {
                points.add(new Point(i, p0.y()));
            }
        } else {
            System.err.println("Line is neither horizontal nor vertical: " + this);
        }
        return List.copyOf(points);
    }

    public List<Point> getAllPoints() {
        List<Point> points = new ArrayList<>();

        int dx = Integer.compare(p1.x(), p0.x());
        int dy = Integer.compare(p1.y(), p0.y());

        for (
                Point current = p0;
                ! current.equals(p1);
                current = new Point(current.x() + dx, current.y() + dy)
        ) {
            points.add(current);
        }
        points.add(p1);

        return List.copyOf(points);
    }
}
