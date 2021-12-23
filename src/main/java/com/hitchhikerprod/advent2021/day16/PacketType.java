package com.hitchhikerprod.advent2021.day16;

import java.util.Arrays;
import java.util.Optional;

enum PacketType {
    SUM(0),
    PRODUCT(1),
    MINIMUM(2),
    MAXIMUM(3),
    LITERAL(4),
    GREATER(5),
    LESSER(6),
    EQUAL(7);

    private final int typeId;

    PacketType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public static Optional<PacketType> from(int typeId) {
        return Arrays.stream(PacketType.values()).filter(e -> e.getTypeId() == typeId).findFirst();
    }
}
