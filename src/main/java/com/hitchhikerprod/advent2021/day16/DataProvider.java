package com.hitchhikerprod.advent2021.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.Objects;

public class DataProvider {
    private final BitSet bits;

    public DataProvider(String inputFile) {
        final InputStream inputData = this.getClass().getResourceAsStream(inputFile);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputData)));
        this.bits = parseBits(reader);
    }

    private DataProvider(BitSet bits) {
        this.bits = bits;
    }

    public static DataProvider from(String inputData) {
        return new DataProvider(parseBits(inputData));
    }

    public static void main(String[] argv) {
        final DataProvider provider = DataProvider.from("D2FE28");
        for (int i = 0; i < provider.getBits().length(); i++) {
            System.out.print(provider.getBits().get(i) ? "1" : "0");
        }
        System.out.println();
    }

    public BitSet getBits() {
        return bits;
    }

    public BitSet parseBits(BufferedReader reader) {
        final String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parseBits(line);
    }

    public static BitSet parseBits(String line) {
        BitSet bits = new BitSet();
        int ptr = 0;

        for (char ch : line.toCharArray()) {
            System.out.print(ch);
            bits.set(ptr, ptr+3, false);
            switch (ch) {
                case '0' -> {}
                case '1' -> bits.set(ptr+3);
                case '2' -> bits.set(ptr+2);
                case '3' -> { bits.set(ptr+3); bits.set(ptr+2); }
                case '4' -> bits.set(ptr+1);
                case '5' -> { bits.set(ptr+3); bits.set(ptr+1); }
                case '6' -> { bits.set(ptr+2); bits.set(ptr+1); }
                case '7' -> { bits.set(ptr+3); bits.set(ptr+2); bits.set(ptr+1); }
                case '8' -> bits.set(ptr);
                case '9' -> { bits.set(ptr+3); bits.set(ptr); }
                case 'A' -> { bits.set(ptr+2); bits.set(ptr); }
                case 'B' -> { bits.set(ptr+3); bits.set(ptr+2); bits.set(ptr); }
                case 'C' -> { bits.set(ptr+1); bits.set(ptr); }
                case 'D' -> { bits.set(ptr+3); bits.set(ptr+1); bits.set(ptr); }
                case 'E' -> { bits.set(ptr+2); bits.set(ptr+1); bits.set(ptr); }
                case 'F' -> { bits.set(ptr+3); bits.set(ptr+2); bits.set(ptr+1); bits.set(ptr); }
                default -> System.err.println("Unrecognized hex character " + ch);
            }
            ptr += 4;
        }
        System.out.println();
        return bits;
    }
}
