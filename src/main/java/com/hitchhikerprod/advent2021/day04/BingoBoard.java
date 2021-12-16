package com.hitchhikerprod.advent2021.day04;

public class BingoBoard {
    private final BingoSquare[][] squares;

    public BingoBoard() {
        squares = new BingoSquare[5][5];
    }

    public BingoSquare getSquare(int x, int y) {
        return squares[y][x];
    }

    public void setSquare(int x, int y, int value) {
        squares[y][x] = new BingoSquare(value);
    }

    public boolean markNumber(int value) {
        for (var row : squares) {
            for (var square : row) {
                if (value == square.getValue()) {
                    square.mark();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isWinning() {
        for (int index = 0; index < 5; index++) {
            boolean winning = true;
            final int y0 = index;
            for (int x0 = 0; x0 < 5; x0++) {
                if (getSquare(x0, y0).isUnmarked()) {
                    winning = false;
                    break;
                }
            }
            if (winning) { return true; }

            winning = true;
            final int x1 = index;
            for (int y1 = 0; y1 < 5; y1++) {
                if (getSquare(x1, y1).isUnmarked()) {
                    winning = false;
                    break;
                }
            }
            if (winning) { return true; }
        }
        return false;
    }

    public int unmarkedSum() {
        int sum = 0;
        for (final var row : squares) {
            for (final var square : row) {
                if (square.isUnmarked()) {
                    sum += square.getValue();
                }
            }
        }
        return sum;
    }

    private static class BingoSquare {
        private final int value;
        private boolean marked;

        BingoSquare(int value) {
            this.value = value;
            this.marked = false;
        }

        public int getValue() {
            return value;
        }

        public void mark() {
            this.marked = true;
        }

        public boolean isUnmarked() {
            return !this.marked;
        }
    }
}
