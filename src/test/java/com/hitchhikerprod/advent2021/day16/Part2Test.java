package com.hitchhikerprod.advent2021.day16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Part2Test {
    @Test
    public void testSum() {
        final DataProvider provider = DataProvider.from("C200B40A82");
        final Part2 uut = new Part2(provider);
        assertEquals(3, uut.solve());
    }

    @Test
    public void testProd() {
        final DataProvider provider = DataProvider.from("04005AC33890");
        final Part2 uut = new Part2(provider);
        assertEquals(54, uut.solve());
    }

    @Test
    public void testLessThan() {
        final DataProvider provider = DataProvider.from("D8005AC2A8F0");
        final Part2 uut = new Part2(provider);
        assertEquals(1, uut.solve());
    }

    @Test
    public void testGreaterThan() {
        final DataProvider provider = DataProvider.from("F600BC2D8F");
        final Part2 uut = new Part2(provider);
        assertEquals(0, uut.solve());
    }

    @Test
    public void testMinimum() {
        final DataProvider provider = DataProvider.from("880086C3E88112");
        final Part2 uut = new Part2(provider);
        assertEquals(7, uut.solve());
    }

    @Test
    public void testMaximum() {
        final DataProvider provider = DataProvider.from("CE00C43D881120");
        final Part2 uut = new Part2(provider);
        assertEquals(9, uut.solve());
    }

    @Test
    public void testEqual() {
        final DataProvider provider = DataProvider.from("9C005AC2F8F0");
        final Part2 uut = new Part2(provider);
        assertEquals(0, uut.solve());
    }

    @Test
    public void testNestedOperations() {
        final DataProvider provider = DataProvider.from("9C0141080250320F1802104A08");
        final Part2 uut = new Part2(provider);
        assertEquals(1, uut.solve());
    }
}
