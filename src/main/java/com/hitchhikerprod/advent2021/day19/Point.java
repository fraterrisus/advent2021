package com.hitchhikerprod.advent2021.day19;

import java.util.List;
import java.util.regex.Pattern;

public record Point(int x, int y, int z) {
    private static final Pattern PARSE_PATTERN = Pattern.compile("([-\\d]+),\\s*([-\\d]+),\\s*([-\\d]+)");

    public static final int IDENTITY_TRANSFORMATION = 0;

    public static final List<int[][]> TRANSFORMATIONS = List.of(
            //              Xout        Yout         Zout
            //            Xi Yi Zi
            new int[][]{ { 1, 0, 0}, { 0, 1, 0}, { 0, 0, 1} },
            new int[][]{ { 0,-1, 0}, { 1, 0, 0}, { 0, 0, 1} },
            new int[][]{ {-1, 0, 0}, { 0,-1, 0}, { 0, 0, 1} },
            new int[][]{ { 0, 1, 0}, {-1, 0, 0}, { 0, 0, 1} },

            new int[][]{ { 1, 0, 0}, { 0,-1, 0}, { 0, 0,-1} },
            new int[][]{ { 0, 1, 0}, { 1, 0, 0}, { 0, 0,-1} },
            new int[][]{ {-1, 0, 0}, { 0, 1, 0}, { 0, 0,-1} },
            new int[][]{ { 0,-1, 0}, {-1, 0, 0}, { 0, 0,-1} },

            new int[][]{ { 1, 0, 0}, { 0, 0,-1}, { 0, 1, 0} },
            new int[][]{ { 0, 0, 1}, { 1, 0, 0}, { 0, 1, 0} },
            new int[][]{ {-1, 0, 0}, { 0, 0, 1}, { 0, 1, 0} },
            new int[][]{ { 0, 0,-1}, {-1, 0, 0}, { 0, 1, 0} },

            new int[][]{ { 1, 0, 0}, { 0, 0, 1}, { 0,-1, 0} },
            new int[][]{ { 0, 0,-1}, { 1, 0, 0}, { 0,-1, 0} },
            new int[][]{ {-1, 0, 0}, { 0, 0,-1}, { 0,-1, 0} },
            new int[][]{ { 0, 0, 1}, {-1, 0, 0}, { 0,-1, 0} },

            new int[][]{ { 0, 0,-1}, { 0, 1, 0}, { 1, 0, 0} },
            new int[][]{ { 0,-1, 0}, { 0, 0,-1}, { 1, 0, 0} },
            new int[][]{ { 0, 0, 1}, { 0,-1, 0}, { 1, 0, 0} },
            new int[][]{ { 0, 1, 0}, { 0, 0, 1}, { 1, 0, 0} },

            new int[][]{ { 0, 0,-1}, { 0,-1, 0}, {-1, 0, 0} },
            new int[][]{ { 0, 1, 0}, { 0, 0,-1}, {-1, 0, 0} },
            new int[][]{ { 0, 0, 1}, { 0, 1, 0}, {-1, 0, 0} },
            new int[][]{ { 0,-1, 0}, { 0, 0, 1}, {-1, 0, 0} }
        );

    public static Point from(String str) {
        var matcher = PARSE_PATTERN.matcher(str);
        if (!matcher.matches()) { throw new IllegalArgumentException(); }
        return new Point(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3))
        );
    }

    public Point invert() {
        return new Point(
                -1 * this.x(),
                -1 * this.y(),
                -1 * this.z()
        );
    }

    /**
     * Converts this, a relative point, to an absolute (=relative to origin) point.
     * @param matrix a 3x3 transformation matrix
     * @return a new Point
     */
    public Point transform(int[][] matrix) {
        return new Point(
                (this.x() * matrix[0][0]) + (this.y() * matrix[0][1]) + (this.z() * matrix[0][2]),
                (this.x() * matrix[1][0]) + (this.y() * matrix[1][1]) + (this.z() * matrix[1][2]),
                (this.x() * matrix[2][0]) + (this.y() * matrix[2][1]) + (this.z() * matrix[2][2])
        );
    }

    public Point translate(Point delta) {
        return new Point(
                this.x() + delta.x(),
                this.y() + delta.y(),
                this.z() + delta.z()
        );
    }
}
