package com.hitchhikerprod.advent2021.day16;

import org.junit.Test;

import static org.junit.Assert.*;

public class Part1Test {
    @Test
    public void testOne() {
        final DataProvider provider = DataProvider.from("8A004A801A8002F478");
        final Part1 uut = new Part1(provider);
        assertEquals(16, uut.solve());
    }

    @Test
    public void testTwo() {
        final DataProvider provider = DataProvider.from("620080001611562C8802118E34");
        final Part1 uut = new Part1(provider);
        assertEquals(12, uut.solve());
    }

    @Test
    public void testThree() {
        final DataProvider provider = DataProvider.from("C0015000016115A2E0802F182340");
        final Part1 uut = new Part1(provider);
        assertEquals(23, uut.solve());
    }

    @Test
    public void testFour() {
        final DataProvider provider = DataProvider.from("A0016C880162017C3686B18A3D4780");
        final Part1 uut = new Part1(provider);
        assertEquals(31, uut.solve());
    }
}
