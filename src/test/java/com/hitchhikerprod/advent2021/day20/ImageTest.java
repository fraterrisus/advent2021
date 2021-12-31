package com.hitchhikerprod.advent2021.day20;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImageTest {
    @Test
    public void testBoundsCheck() {
        Image uut = new Image(List.of(
                "..#.#.",
                "......"
        ));

        assertTrue(uut.at(2,0));
        assertFalse(uut.at(3,0));
        assertFalse(uut.at(-1,1));
        assertFalse(uut.at(2,-1));
        assertFalse(uut.at(10,1));
        assertFalse(uut.at(4,5));
    }

    @Test
    public void testPixelCounter() {
        Image uut = new Image(List.of(
                "..#..",
                ".#.#.",
                "..#.."
        ));

        assertEquals(4, uut.countOnPixels());
    }

    @Test
    public void testDimensions() {
        Image uut = new Image(List.of(
                ".....",
                ".#.#",
                "..#.."
        ));

        final Image.Dimensions dim = uut.getDimensions();
        assertEquals(5, dim.x());
        assertEquals(3, dim.y());
    }
}
