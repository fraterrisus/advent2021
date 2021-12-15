package com.hitchhikerprod.advent2021.day01;

public class Part2 {
    final Integer[] data;

    public Part2() {
        this.data = new DataProvider().getData().toArray(new Integer[]{});
    }

    public int solve() {
        int count = 0;
        int slide = data[0] + data[1] + data[2];
        for (int index = 3; index < data.length; index++) {
            int current = slide;
            current -= data[index - 3];
            current += data[index];
            if (current > slide) {
                count += 1;
            }
            slide = current;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(new Part2().solve());
    }
}
