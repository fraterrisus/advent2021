package com.hitchhikerprod.advent2021.day04;

public class Part1 {
    // Find the board that wins FIRST and calculate its score.
    public int solve() {
        var data = new DataProvider();

        for (int drawn : data.getDraw()) {
            // For each number in the draw, have each board mark that number.
            // For each board that successfully marked something, check if it is winning.
            final var winningBoards = data.getBoards().stream()
                    .filter(board -> board.markNumber(drawn))
                    .filter(BingoBoard::isWinning)
                    .toList();

            // If any board has won, score the first one.
            if (winningBoards.size() > 0) {
                final var winningBoard = winningBoards.get(0);
                return drawn * winningBoard.unmarkedSum();
            }
        }

        return -1;
    }

    public static void main(String[] argv) {
        System.out.println(new Part1().solve());
    }
}
