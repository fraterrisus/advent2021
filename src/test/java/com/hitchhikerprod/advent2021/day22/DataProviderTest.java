package com.hitchhikerprod.advent2021.day22;

import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class DataProviderTest {
    @Test
    public void testString() {
        final Stream<String> input = Stream.of("on x=10..11,y=12..13,z=14..15");
        final DataProvider uut = new DataProvider(input);
        final List<RebootStep> steps = uut.getSteps();

        assertEquals(1, steps.size());
        assertEquals(RebootStep.Operation.ON, steps.get(0).op());
        assertEquals(new Range(10,11), steps.get(0).x());
        assertEquals(new Range(12,13), steps.get(0).y());
        assertEquals(new Range(14,15), steps.get(0).z());
    }
}
