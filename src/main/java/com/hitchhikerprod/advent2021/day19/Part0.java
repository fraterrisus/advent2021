package com.hitchhikerprod.advent2021.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part0 {
    private record OrientationMapping(int index, Point delta) {}

    private final List<Map<Integer, List<Point>>> revolutions;
    private final List<List<Map<Integer, List<OrientationMapping>>>> transformations;

    public Part0(DataProvider provider) {
        this.revolutions = new ArrayList<>();
        this.transformations = new ArrayList<>();
    }

    public static void main(String[] argv) {
        final String inputFile = "/inputs/day19.txt";
        final DataProvider provider = DataProvider.from(inputFile);
        System.out.println(new Part0(provider).solve());
    }

    public long solve() {
        findValidTransformations();
        displayTransformations();

        searchForOrientations();

        return -1L;
    }

    private Map<Integer, List<Point>> generateTransformations(List<Point> in) {
        return IntStream.range(0, Point.TRANSFORMATIONS.size())
                .boxed()
                .collect(Collectors.toMap(
                        idx -> idx,
                        idx -> {
                            final var t = Point.TRANSFORMATIONS.get(idx);
                            return in.stream().map(p -> p.transform(t)).toList();
                        }));
    }

    private void findValidTransformations() {
        for (int i0 = 0; i0 < revolutions.size(); i0++) {
            final List<Map<Integer, List<OrientationMapping>>> fromList = new ArrayList<>();
            transformations.add(i0, fromList);
            for (int j0 = 0; j0 < revolutions.size(); j0++) {
                final Map<Integer, List<OrientationMapping>> mapping = new HashMap<>();
                transformations.get(i0).add(j0, mapping);
                if (i0 == j0) { continue; }
                System.out.println(i0 + "x" + j0);
                final var ti = revolutions.get(i0);
                final var tj = revolutions.get(j0);
                for (var li : ti.entrySet()) {
                    for (var lj : tj.entrySet()) {
                        final OverlapDetector detector = new OverlapDetector(li.getValue(), lj.getValue());
                        if (detector.detect()) {
                            final var dj = detector.getDb().translate(detector.getDa().invert());
                            mapping.computeIfAbsent(li.getKey(), k -> new ArrayList<>());
                            mapping.get(li.getKey()).add(new OrientationMapping(lj.getKey(), dj));
                        }
                    }
                }
            }
        }
    }

    private void displayTransformations() {
        System.out.println();
        for (var a : transformations) {
            System.out.print("[ ");
            for (var b : a) {
                System.out.print(b.isEmpty() ? ". " : b.size() + " ");
            }
            System.out.println("]");
        }
    }

    private static final int UNASSIGNED = -1;

    private void searchForOrientations() {
        final List<Integer> orientations = new ArrayList<>(transformations.size());
        for (int i = 0; i < transformations.size(); i++) { orientations.add(UNASSIGNED); }
        for (int i = 0; i < Point.TRANSFORMATIONS.size(); i++) {
            orientations.set(0, i);
            if (orientationsHelper(List.copyOf(orientations), 0)) { return; }
        }
    }

    private boolean orientationsHelper(List<Integer> assignments, int depth) {
        if (assignments.stream().allMatch(a -> a != UNASSIGNED)) {
            System.out.print("{" + depth + "} ");
            System.out.println("Found assignment: " + assignments);
            return true;
        }

        final List<Integer> myAssignments = new ArrayList<>(assignments);
        System.out.println(myAssignments);
        for (int nodeId = 0; nodeId < assignments.size(); nodeId++) {
            final var myOrientation = myAssignments.get(nodeId);
            if (myOrientation != UNASSIGNED) {
                var neighbors = transformations.get(nodeId);
                for (int neighborId = 0; neighborId < neighbors.size(); neighborId++) {
                    if (myAssignments.get(neighborId) != UNASSIGNED) { continue; }
                    final List<OrientationMapping> potentialMappings = neighbors.get(neighborId).get(myOrientation);
                    if (potentialMappings == null) {
                        System.out.print("{" + depth + "} ");
                        System.out.println("No mappings for " + nodeId + "[" + myAssignments.get(nodeId) + "] -> " + neighborId);
                    } else {
                        for (var m : potentialMappings) {
                            System.out.print("{" + depth + "} ");
                            System.out.println("Trying " + nodeId + "[" + myAssignments.get(nodeId) + "] -> " + neighborId + "[" + m.index() + "]");

                            myAssignments.set(neighborId, m.index());
                            if (orientationsHelper(List.copyOf(myAssignments), depth+1)) {
                                return true;
                            }
                            myAssignments.set(neighborId, UNASSIGNED);

                            System.out.print("{" + depth + "} ");
                            System.out.println("Failed " + nodeId + "[" + myAssignments.get(nodeId) + "] -> " + neighborId + "[" + m.index() + "]");
                        }
                    }
                }
            }
        }

        return false;
    }
}
