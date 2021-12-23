package com.hitchhikerprod.advent2021.day16;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class PacketParser {
    private final BitSet bits;
    private final boolean part2;

    public PacketParser(BitSet bits) {
        this.bits = bits;
        this.part2 = false;
    }

    public PacketParser(BitSet bits, boolean part2) {
        this.bits = bits;
        this.part2 = part2;
    }

    public PacketData parse() {
        return parsePacket(0);
    }

    private PacketData parsePacket(final int index) {
        System.out.println("Parsing packet @" + index);
        final int typeId = readFromBitSet(index + 3, 3);

        if (typeId == 4) {
            return parseLiteralPacket(index);
        } else {
            return parseOperatorPacket(typeId, index);
        }
    }

    private PacketData parseOperatorPacket(final int typeId, final int index) {
        Optional<PacketType> packetType = PacketType.from(typeId);
        String identifier = (packetType.isPresent()) ? packetType.get().toString() : "operator";
        System.out.println("Parsing " + identifier + " packet @" + index);

        // read header
        final int version = readFromBitSet(index, 3);
        final boolean lengthTypeId = bits.get(index + 6);
        int newIndex = index + 7;

        int i = 0;

        final BiFunction<Integer, Integer, Boolean> endCondition;
        if (lengthTypeId) {
            final int numSubPackets = readFromBitSet(newIndex, 11);
            newIndex += 11;
            endCondition = (num, idx) -> num < numSubPackets;
            System.out.println("Parsing " + numSubPackets + " sub-packets");
        } else {
            final int endIndex = readFromBitSet(newIndex, 15) + newIndex + 15;
            newIndex += 15;
            endCondition = (num, idx) -> idx < endIndex;
            System.out.println("Parsing sub-packets until @" + endIndex);
        }

        final List<PacketData> packets = new ArrayList<>();
        while (endCondition.apply(i, newIndex)) {
            final PacketData data = parsePacket(newIndex);
            newIndex = data.endIndex();
            i++;
            packets.add(data);
        }

        if (part2) {
            final long value;
            switch (typeId) {
                case 0 -> value = packets.stream().mapToLong(PacketData::value).sum();
                case 1 -> value = packets.stream().mapToLong(PacketData::value).reduce((a,b) -> a * b).orElse(-1L);
                case 2 -> value = packets.stream().mapToLong(PacketData::value).min().orElse(-1L);
                case 3 -> value = packets.stream().mapToLong(PacketData::value).max().orElse(-1L);
                case 5 -> value = (packets.get(0).value() > packets.get(1).value()) ? 1L : 0L;
                case 6 -> value = (packets.get(0).value() < packets.get(1).value()) ? 1L : 0L;
                case 7 -> value = (packets.get(0).value() == packets.get(1).value()) ? 1L : 0L;
                default -> {
                    System.err.println("Unrecognized packet type " + typeId);
                    value = -1L;
                }
            }
            return new PacketData(version, value, newIndex);
        } else {
            final int versionSum = version + packets.stream()
                    .mapToInt(PacketData::version).sum();
            return new PacketData(versionSum, 0L, newIndex);
        }
    }

    private PacketData parseLiteralPacket(final int index) {
        System.out.print("Parsing literal packet @" + index);

        // read header
        final int version = readFromBitSet(index, 3);
        int newIndex = index + 6;

        long out = 0;
        boolean keepReading = true;
        while (keepReading) {
            int b = readFromBitSet(newIndex + 1, 4);
            out = out << 4;
            out = out | b;
            keepReading = bits.get(newIndex);
            newIndex += 5;
        }

        System.out.println(" = " + out);
        return new PacketData(version, out, newIndex);
    }

    private int readFromBitSet(final int from, final int length) {
        if (length > 31) {
            throw new IllegalArgumentException("Can only read one int (31b) at a time");
        }
        int maxBits = IntStream.of(31, length).min().orElse(-1);
        int b = 0;
        for (int i = 0; i < maxBits; i++) {
            b = b << 1;
            if (bits.get(from + i)) {
                b = b | 0x1;
            }
        }
        return b;
    }
}
