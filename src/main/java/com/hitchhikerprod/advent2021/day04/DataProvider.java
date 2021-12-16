package com.hitchhikerprod.advent2021.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DataProvider {

    private static final String INPUT_FILE = "/inputs/day04-1.txt";
    // private static final String INPUT_FILE = "/inputs/day04-sample.txt";

    private final List<Integer> draw;
    private final List<BingoBoard> boards;

    public DataProvider() {
        try {
            final InputStream inputData = this.getClass().getResourceAsStream(INPUT_FILE);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));

            final var drawStrings = reader.readLine().split(",");
            this.draw = Stream.of(drawStrings).map(Integer::parseInt).toList();
            this.boards = parseBoards(reader);
        } catch (IOException e) {
            System.err.println("IOException caught while reading input data");
            throw new RuntimeException(e);
        }
    }

    public List<BingoBoard> getBoards() {
        return boards;
    }

    public List<Integer> getDraw() {
        return draw;
    }

    private List<BingoBoard> parseBoards(BufferedReader reader) throws IOException {
        List<BingoBoard> tempBoards = new ArrayList<>();
        while(true) {
            BingoBoard newBoard = new BingoBoard();

            var blankLine = reader.readLine();
            if (blankLine == null) { // EOF
                break;
            }

            for (int y = 0; y < 5; y++) {
                int x = 0;
                for (String valueString : reader.readLine().split("\s+")) {
                    try {
                        int newValue = Integer.parseInt(valueString);
                        newBoard.setSquare(x, y, newValue);
                        x++;
                    } catch (NumberFormatException ignored) { }
                }
            }

            tempBoards.add(newBoard);
        }
        // In retrospect this should probably be a Set for efficiency, but it's not super important.
        return List.copyOf(tempBoards);
    }
}
