package com.hitchhikerprod.advent2021.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 {
    private final List<List<Point>> readouts;
    private final List<List<List<Point>>> revolutions;
    private final Point[][] transformations;

    public Part1(DataProvider provider) {
        this.readouts = provider.getReadouts();
        this.revolutions = new ArrayList<>();
        for (var r : readouts) {
            this.revolutions.add(generateTransformations(r));
        }
        this.revolutions.set(0, List.of(this.readouts.get(0)));
        final int dim = readouts.size();
        this.transformations = new Point[dim][dim];
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day19-sample.txt";
        final DataProvider provider = new DataProvider(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        for (int i0 = 0; i0 < revolutions.size(); i0++) {
            scanner: for (int j0 = 0; j0 < revolutions.size(); j0++) {
                if (i0 == j0) { continue; }
                System.out.println(i0 + "x" + j0);
                final List<List<Point>> ti = revolutions.get(i0);
                final List<List<Point>> tj = revolutions.get(j0);
                for (var li : ti) {
                    for (var lj : tj) {
                        final OverlapDetector detector = new OverlapDetector(li, lj);
                        if (detector.detect()) {
                            final var dj = detector.getDb().translate(detector.getDa().invert());
                            // revolutions.set(i0, List.of(li));
                            // revolutions.set(j0, List.of(lj));
                            // var newB = lj.stream().map(p -> p.translate(dj)).toList();
                            // transformations.set(j0, List.of(newB));
                            transformations[i0][j0] = dj;
                            continue scanner;
                        }
                    }
                }
            }
        }

        /*
        for (var beacon1 : readouts) {
            var transforms1 = generateTransformations(beacon1);

            for (var beacon2 : readouts) {
                if (beacon1 == beacon2) { continue; }

                var transforms2 = generateTransformations(beacon2);

                for (var l1 : transforms1) {
                    for (var l2 : transforms2) {
                        if (detector.detect(l1, l2)) {
                            System.out.println("found");
                        }
                    }
                }
            }
        }
         */

        System.out.println(Arrays.deepToString(transformations));

        return -1L;
    }

    private List<List<Point>> generateTransformations(List<Point> in) {
        return Point.TRANSFORMATIONS.stream()
                .map(t -> in.stream().map(p -> p.transform(t)).toList())
                .toList();
    }
}
