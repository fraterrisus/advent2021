package com.hitchhikerprod.advent2021.day20;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlgorithmTest {
    @Test
    public void testEnhance() {
        Image image = new Image(List.of(
                "#..#.",
                "#....",
                "##..#",
                "..#..",
                "..###"
        ));

        final Algorithm uut = new Algorithm("..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
                "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
                ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
                ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
                ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
                "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
                "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#");

        List<Long> counts = new ArrayList<>();
        counts.add(image.countOnPixels());
        for (int i = 0; i < 50; i += 2) {
            image = uut.enhance(image);
            counts.add(-1L);
            image = uut.enhance(image);
            counts.add(image.countOnPixels());
        }

        //assertEquals(24, counts[1])
        assertEquals(new Long(35L), counts.get(2));
        assertEquals(new Long(3351L), counts.get(50));
    }
}
