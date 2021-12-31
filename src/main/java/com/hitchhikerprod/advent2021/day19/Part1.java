package com.hitchhikerprod.advent2021.day19;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part1 {
    private final DataProvider provider;

    public Part1(DataProvider provider) {
        this.provider = provider;
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day19.txt";
        final DataProvider provider = DataProvider.from(inputFile);
        System.out.println(new Part1(provider).oldSolve());
    }

    private record Foo(int id, int orientation) {}
    private record Bar(Foo f, Point delta) {}

    public long solve() {
        Set<Scanner> scanners = IntStream.range(0, provider.getReadouts().size())
                .mapToObj(id -> new Scanner(id, provider.getReadouts().get(id)))
                .collect(Collectors.toSet());

        Map<Foo, List<Bar>> scannerMap = new HashMap<>();

        for (Scanner a : scanners) {
            System.out.print(a.id() + ":");
            for (Scanner b : scanners) {
                if (b.id() <= a.id()) {
                    continue;
                }
                System.out.print(" " + b.id());
                for (int i = 0; i < Point.TRANSFORMATIONS.size(); i++) {
                    List<Point> at = a.absoluteBeacons(i);
                    for (int j = 0; j < Point.TRANSFORMATIONS.size(); j++) {
                        List<Point> bt = b.absoluteBeacons(j);

                        final OverlapDetector detector = new OverlapDetector(at, bt);
                        if (detector.detect()) {
                            var aReference = new Foo(a.id(), i);
                            var bReference = new Foo(b.id(), j);
                            scannerMap.computeIfAbsent(aReference, k -> new ArrayList<>());
                            scannerMap.get(aReference).add(new Bar(bReference, detector.getDelta()));
                            scannerMap.computeIfAbsent(bReference, k -> new ArrayList<>());
                            scannerMap.get(bReference).add(new Bar(aReference, detector.getDelta().invert()));
                        }
                    }
                }
            }
            System.out.println();
        }

        List<Bar> locations = null;
        for (var f : scannerMap.keySet()) {
            var candidate = new Bar(f, new Point(0,0,0));
            Optional<List<Bar>> solution = recursiveBuild(scannerMap, scanners.size(), List.of(candidate));
            if (solution.isPresent()) {
                locations = solution.get();
                break;
            }
        }

        Set<Point> beacons = new HashSet<>();
        for (var location : locations) {
            final Scanner scanner = scanners.stream()
                    .filter(s -> s.id() == location.f().id())
                    .findFirst().orElseThrow();
            final Scanner newScanner = new Scanner(
                    scanner.id(),
                    scanner.beacons(),
                    location.f().orientation(),
                    location.delta());
            beacons.addAll(newScanner.absoluteBeacons().stream()
                    .map(p -> p.translate(newScanner.position()))
                    .toList());
        }

        return beacons.size();
    }

    private Optional<List<Bar>> recursiveBuild(Map<Foo, List<Bar>> scannerMap, int expectedSize, List<Bar> candidates) {
        List<Integer> candidateIds = candidates.stream().map(b -> b.f().id()).toList();
        if (candidateIds.size() == expectedSize) {
            System.out.println(candidates);
            return Optional.of(candidates);
        }

        final List<Bar> subCandidates = new ArrayList<>();
        for (var key : candidates) {
            for (var value : scannerMap.get(key.f())) {
                if (candidateIds.contains(value.f().id())) { continue; }
                subCandidates.add(new Bar(value.f(), value.delta().translate(key.delta())));
            }
        }

        for (Bar b : subCandidates) {
            final var newCandidates = new ArrayList<>(candidates);
            newCandidates.add(b);

            Optional<List<Bar>> solution = recursiveBuild(scannerMap, expectedSize, newCandidates);
            if (solution.isPresent()) { return solution; }
        }

        return Optional.empty();
    }

    public long oldSolve() {
        Set<Scanner> scanners = null;

        boolean matched = false;
        int orientation = 0;
        while (!matched) {
            if (orientation >= Point.TRANSFORMATIONS.size()) {
                System.out.println("No match found for any orientation. Giving up.");
                return -1L;
            }

            scanners = IntStream.range(0, provider.getReadouts().size())
                    .mapToObj(id -> new Scanner(id, provider.getReadouts().get(id)))
                    .collect(Collectors.toSet());

            final Scanner first = scanners.stream()
                    .filter(s -> s.id() == 0)
                    .findFirst().orElseThrow();
            scanners.remove(first);
            scanners.add(new Scanner(first.id(), first.beacons(), orientation, new Point(0,0,0)));

            matched = locateScanners(scanners);

            orientation++;
        }

        scanners.stream()
                .sorted(Comparator.comparing(Scanner::id))
                .forEach(System.out::println);

        final Set<Point> beacons = scanners.stream()
                .flatMap(scanner -> scanner.absoluteBeacons().stream()
                        .map(p -> p.translate(scanner.position())))
                .collect(Collectors.toSet());

        return beacons.size();
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
