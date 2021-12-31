package com.hitchhikerprod.advent2021.day19;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part1 {
    private static final boolean PART1 = false;

    private final DataProvider provider;

    public Part1(DataProvider provider) {
        this.provider = provider;
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day19.txt";
        final DataProvider provider = DataProvider.from(inputFile);
        System.out.println(new Part1(provider).solve());
    }

    public long solve() {
        Set<Scanner> scanners = null;

        // Pick ID0 as the root of the search space. We *should* be able to pick the default orientation and have
        // everything else be relative to that, so this is probably unnecessary, but iterate over all possible
        // orientations of the root note just in case. (And short-circuit if one matches.)
        boolean matched = false;
        int orientation = 0;
        while (!matched) {
            if (orientation >= Point.TRANSFORMATIONS.size()) {
                System.out.println("No match found for any orientation. Giving up.");
                return -1L;
            }

            // Package the list of lists of beacons into a set of Scanner objects
            scanners = IntStream.range(0, provider.getReadouts().size())
                    .mapToObj(id -> new Scanner(id, provider.getReadouts().get(id)))
                    .collect(Collectors.toSet());

            // Give the unoriented ID0 Scanner an orientation
            final Scanner first = scanners.stream()
                    .filter(s -> s.id() == 0)
                    .findFirst().orElseThrow();
            scanners.remove(first);
            scanners.add(new Scanner(first.id(), first.beacons(), orientation, new Point(0,0,0)));

            // Attempt to match the rest of the scanners. This *should* work the first time.
            matched = locateScanners(scanners);

            orientation++;
        }

        scanners.stream()
                .sorted(Comparator.comparing(Scanner::id))
                .forEach(System.out::println);

        if (PART1) {
            // For each scanner, translate its beacons from relative coordinates to absolute (relative to ID0).
            // File the points into a Set to remove duplicates.
            final Set<Point> beacons = scanners.stream()
                    .flatMap(scanner -> scanner.absoluteBeacons().stream()
                            .map(p -> p.translate(scanner.position())))
                    .collect(Collectors.toSet());

            return beacons.size();
        } else {
            // Calculate the Manhattan distance (dx+dy+dz) between each pair of scanners, and return the maximum.
            // (Cut the diagonal on the search space.)
            long farthestDistance = 0;

            for (Scanner a : scanners) {
                for (Scanner b : scanners) {
                    if (a.id() >= b.id()) { continue; }
                    final long distance = Math.abs(a.position().x() - b.position().x()) +
                            Math.abs(a.position().y() - b.position().y()) +
                            Math.abs(a.position().z() - b.position().z());
                    if (distance > farthestDistance) {
                        farthestDistance = distance;
                    }
                }
            }

            return farthestDistance;
        }
    }

    private boolean locateScanners(Set<Scanner> scanners) {
        while (true) {
            final Map<Boolean, Set<Scanner>> partition = scanners.stream()
                    .collect(Collectors.groupingBy(Scanner::hasKnownLocation, Collectors.toSet()));

            if (partition.get(false) == null) {
                System.out.println("All scanners have been located.");
                return true;
            }

            // Compare each scanner with a known location...
            boolean acted = false;
            repartition: for (Scanner fixed : partition.get(true)) {
                final List<Point> a = fixed.absoluteBeacons();

                // ... to each scanner without a known location.
                for (Scanner floating : partition.get(false)) {
                    // System.out.println("Comparing " + fixed + " -> " + floating);

                    // Iterate over every possible X/Y/Z transformation
                    for (int tbIndex = 0; tbIndex < Point.TRANSFORMATIONS.size(); tbIndex++) {
                        final List<Point> b = floating.absoluteBeacons(tbIndex);

                        // Attempt to detect overlap between the known scanner and the unknown scanner
                        final OverlapDetector detector = new OverlapDetector(a, b);

                        if (detector.detect()) {
                            // If we're successful, locate the unknown scanner using the delta computed by the detector.
                            final Point delta = detector.getDelta();
                            final Point newPos = fixed.position().translate(delta);
                            System.out.println("Assigning scanner " + floating.id() + " position " + newPos);

                            // Rewrite the newly-located scanner with the correct orientation and position.
                            scanners.remove(floating);
                            scanners.add(new Scanner(floating.id(), floating.beacons(), tbIndex, newPos));
                            acted = true;
                            break repartition;
                        }
                    }
                }
            }
            if (!acted) {
                System.out.println("Failed to find a match.");
                return false;
            }
        }
    }
}
