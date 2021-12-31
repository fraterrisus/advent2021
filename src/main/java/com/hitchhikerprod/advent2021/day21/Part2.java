package com.hitchhikerprod.advent2021.day21;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.LongStream;

public class Part2 {
    private record GameState(Player player1, Player player2) {}

    private final DataProvider provider;
    private Long wins1 = 0L;
    private Long wins2 = 0L;

    public Part2(DataProvider provider) {
        this.provider = provider;
    }

    public static void main(String[] argv) {
        final DataProvider provider = DataProvider.from("/inputs/day21.txt", 21);
        System.out.println(new Part2(provider).solve());
    }

    // Quantum choices:
    // 3: 1+1+1                                     (1)
    // 4: 1+1+2 1+2+1       2+1+1                   (3)
    // 5: 1+1+3 1+2+2 1+3+1 2+1+2 2+2+1 3+1+1       (6)
    // 6: 2+3+1 1+2+3 1+3+2 2+1+3 2+2+2 3+1+2 3+2+1 (7)
    // 7: 2+3+2 3+3+1 1+3+3       2+2+3 3+1+3 3+2+2 (6)
    // 8: 2+3+3 3+3+2                         3+2+3 (3)
    // 9:       3+3+3                               (1)

    private static final Map<Integer, Integer> QUANTUM_DIE_ROLLS = new HashMap<>();
    static {
        QUANTUM_DIE_ROLLS.put(3, 1);
        QUANTUM_DIE_ROLLS.put(4, 3);
        QUANTUM_DIE_ROLLS.put(5, 6);
        QUANTUM_DIE_ROLLS.put(6, 7);
        QUANTUM_DIE_ROLLS.put(7, 6);
        QUANTUM_DIE_ROLLS.put(8, 3);
        QUANTUM_DIE_ROLLS.put(9, 1);
    }

    public long solve() {
        final GameState initialState = new GameState(provider.getPlayer1(), provider.getPlayer2());

        // FIXME: change the hash key to half of the game state, and on each iteration, write a new table that's keyed
        // off the *other* player's state, so you're only reading the half of the game state that's relevant to the
        // current player.

        Map<GameState, Long> stateCounts = new HashMap<>();
        stateCounts.put(initialState, 1L);

        int turn = 0;

        while (true) {
            if (stateCounts.isEmpty()) { break; }

            final Function<GameState, Player> copyPlayerFunction;
            final Consumer<Integer> updateWinsFunction;
            final BiFunction<GameState, Player, GameState> gameStateFunction;
            if (turn == 0) {
                copyPlayerFunction = (s) -> Player.copyOf(s.player1());
                updateWinsFunction = (w) -> wins1 += w;
                gameStateFunction = (s, p) -> new GameState(p, s.player2());
            } else {
                copyPlayerFunction = (s) -> Player.copyOf(s.player2());
                updateWinsFunction = (w) -> wins2 += w;
                gameStateFunction = (s, p) -> new GameState(p, s.player1());
            }

            final Map<GameState, Long> newStateCounts = new HashMap<>();

            for (var e : stateCounts.entrySet()) {
                final GameState state = e.getKey();
                for (var f : QUANTUM_DIE_ROLLS.entrySet()) {
                    Player p1 = copyPlayerFunction.apply(state);
                    p1.advance(f.getKey());
                    p1.score(p1.position());
                    if (p1.hasWon()) {
                        updateWinsFunction.accept(f.getValue());
                    } else {
                        final GameState newState = gameStateFunction.apply(state, p1);
                        newStateCounts.compute(newState, (k, v) -> v == null ? f.getValue() : v + f.getValue());
                    }
                }
            }

            stateCounts = newStateCounts;
            turn = 1 - turn;
        }

        return LongStream.of(wins1, wins2).max().orElse(-1L);
    }

}
