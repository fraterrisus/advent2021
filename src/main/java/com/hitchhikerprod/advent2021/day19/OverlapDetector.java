package com.hitchhikerprod.advent2021.day19;

import java.util.List;

public class OverlapDetector {
    private final List<Point> a;
    private final List<Point> b;

    private Point da;
    private Point db;

    public OverlapDetector(List<Point> a, List<Point> b) {
        this.a = a;
        this.b = b;
    }

    public boolean detect() {
        for (Point pa : a) {
            final Point da = pa.invert();
            final List<Point> ax = a.stream().map(p -> p.translate(da)).toList();
            for (Point pb : b) {
                final Point db = pb.invert();
                final List<Point> bx = b.stream().map(p -> p.translate(db)).toList();

                final var overlaps = ax.stream().filter(bx::contains).toList();
                if (overlaps.size() >= 12) {
                    this.da = da;
                    this.db = db;
                    System.out.println("dA: " + da + ", dB: " + db);
                    System.out.println("Matches: ");
                    overlaps.forEach(p -> System.out.println("  " + p.translate(pa)));
                    return true;
                }
            }
        }
        return false;
    }

    public Point getDa() {
        return da;
    }

    public Point getDb() {
        return db;
    }
}
