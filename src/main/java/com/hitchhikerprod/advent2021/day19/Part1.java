package com.hitchhikerprod.advent2021.day19;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Part1 {
    private final Set<Scanner> scanners;

    public Part1(DataProvider provider) {
        scanners = new HashSet<>();
        int id = 0;
        boolean first = true;
        for (var beacons : provider.getReadouts()) {
            if (first) {
                scanners.add(new Scanner(id, beacons, 0, new Point(0, 0, 0)));
                first = false;
            } else {
                scanners.add(new Scanner(id, beacons));
            }
            id++;
        }
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day19-sample.txt";
        final DataProvider provider = DataProvider.from(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    private record ScannerPair(Scanner a, Scanner b) {}

    public long solve() {
        while (true) {
            final Map<Boolean, Set<Scanner>> partition = scanners.stream()
                    .collect(Collectors.groupingBy(Scanner::hasKnownLocation, Collectors.toSet()));

            if (partition.get(false) == null) {
                System.out.println("All scanners have been located.");
                break;
            }

            ScannerPair pair = null;
            repartition: for (Scanner fixed : partition.get(true)) {
                final List<Point> a = fixed.beacons();

                for (Scanner floating : partition.get(false)) {
                    System.out.println("Comparing " + fixed + " -> " + floating);
                    for (int tbIndex = 0; tbIndex < Point.TRANSFORMATIONS.size(); tbIndex++) {
                        var tb = Point.TRANSFORMATIONS.get(tbIndex);
                        final List<Point> b = floating.beacons().stream()
                                .map(p -> p.transform(tb))
                                .toList();
                        final OverlapDetector detector = new OverlapDetector(a, b);
                        if (detector.detect()) {
                            final Point delta = detector.getDelta();
                            final Point newPos = fixed.position().translate(delta);
                            System.out.println("Assigning scanner " + floating.id() + " position " + newPos);
                            pair = new ScannerPair(floating,
                                    new Scanner(floating.id(), b, tbIndex, newPos));
                            break repartition;
                        }
                    }
                }
            }
            if (pair == null) {
                System.out.println("No matching scanners found.");
                break;
            } else {
                scanners.remove(pair.a());
                scanners.add(pair.b());
            }
        }

        scanners.forEach(System.out::println);

        return -1L;
    }


}
