package com.hitchhikerprod.advent2021.day04;

import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    // Find the board that wins LAST and calculate its score.
    public int solve() {
        var data = new DataProvider();

        // Build a list of boards that haven't won yet.
        List<BingoBoard> remainingBoards = List.copyOf(data.getBoards());

        for (int drawn : data.getDraw()) {
            // For each number in the draw, have each board mark that number.
            remainingBoards.forEach(board -> board.markNumber(drawn));

            // Partition the set of boards by winning / not winning.
            var winningMap = remainingBoards.stream()
                    .collect(Collectors.groupingBy(BingoBoard::isWinning, Collectors.toList()));

            // If there are no more not-winning boards, we're done; the board(s) that won on this pass are the last
            // boards to win, so calculate its score and return.
            if (winningMap.get(false) == null) {
                final var lastWinningBoard = winningMap.get(true).get(0);
                return drawn * lastWinningBoard.unmarkedSum();
            }

            // Otherwise, update the list of not-winning boards and iterate.
            remainingBoards = winningMap.get(false);
        }

        return -1;
    }

    public static void main(String[] argv) {
        System.out.println(new Part2().solve());
    }
}
