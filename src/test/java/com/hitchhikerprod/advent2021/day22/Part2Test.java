package com.hitchhikerprod.advent2021.day22;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class Part2Test {
    @Test
    public void testSmallExample() {
        final Stream<String> input = Stream.of(
                "on x=10..12,y=10..12,z=10..12",
                "on x=11..13,y=11..13,z=11..13",
                "off x=9..11,y=9..11,z=9..11",
                "on x=10..10,y=10..10,z=10..10"
        );
        final DataProvider provider = new DataProvider(input);
        final Part2 uut = new Part2(provider);
        assertEquals(39, uut.solve());
    }
}
