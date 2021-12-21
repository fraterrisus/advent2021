package com.hitchhikerprod.advent2021.day13;

import java.util.HashSet;
import java.util.Set;

public class Folder {
    public Set<Point> foldPaper(Set<Point> in, Fold fold) {
        Set<Point> out = new HashSet<>();

        if (fold.dimension() == Dimension.X) {
            for (Point p: in) {
                if (p.x() > fold.value()) {
                    final int newX = fold.value() - (p.x() - fold.value());
                    out.add(new Point(newX, p.y()));
                } else {
                    out.add(p);
                }
            }
        } else {
            for (Point p : in) {
                if (p.y() > fold.value()) {
                    final int newY = fold.value() - (p.y() - fold.value());
                    out.add(new Point(p.x(), newY));
                } else {
                    out.add(p);
                }
            }
        }

        return Set.copyOf(out);
    }
}
