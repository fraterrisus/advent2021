package com.hitchhikerprod.advent2021.day19;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataProviderTest {
    @Test
    public void testParse() {
        final List<List<Point>> scanners = DataProvider.parseScanners(List.of(
                "--- scanner 0 ---",
                "404,-588,-901",
                "528,-643,409",
                "",
                "--- scanner 1 ---",
                "727,592,562",
                "-293,-554,779"
        ));

        assertEquals(2, scanners.size());
        assertEquals(2, scanners.get(0).size());
        assertEquals(new Point(404, -588, -901), scanners.get(0).get(0));
        assertEquals(2, scanners.get(1).size());
    }
}
