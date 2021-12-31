package com.hitchhikerprod.advent2021.day20;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataProviderTest {
    @Test
    public void testParser() {
        String testAlgorithm = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
                "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
                ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
                ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
                ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
                "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
                "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#";
        List<String> testImage = List.of(
                "#..#.",
                "#....",
                "##..#",
                "..#..",
                "..###"
        );

        final DataProvider uut = new DataProvider(testAlgorithm, testImage);
    }

    @Test
    public void testFile() {
        final DataProvider uut = DataProvider.from("/inputs/day20.txt");
        assertTrue(uut.getImage().at(0,0));
        assertFalse(uut.getImage().at(0,4));
    }
}
