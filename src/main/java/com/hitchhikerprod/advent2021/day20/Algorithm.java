package com.hitchhikerprod.advent2021.day20;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    private final String algorithm;

    public Algorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public boolean at(int index) {
        return (algorithm.charAt(index) == Image.ON);
    }

    public Image enhance(Image in) {
        List<String> newPixels = new ArrayList<>();
        Image.Dimensions dim = in.getDimensions();
        for (int y = -2; y < dim.y() + 2; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = -2; x < dim.x() + 2; x++) {
                final int index = buildIndex(in, x, y);
                final boolean newChar = at(index);
                sb.append(newChar ? Image.ON : Image.OFF);
            }
            newPixels.add(sb.toString());
        }

        boolean newInfinity = newPixels.get(0).charAt(0) == Image.ON;
        int checkChar = newInfinity ? Image.ON : Image.OFF;

        boolean prune = true;
        while (prune) {
            prune = false;
            if (newPixels.get(0).chars().allMatch(x -> x == checkChar)) {
                newPixels.remove(0);
                prune = true;
            }
            if (newPixels.get(newPixels.size() - 1).chars().allMatch(x -> x == checkChar)) {
                newPixels.remove(newPixels.size() - 1);
                prune = true;
            }
            if (newPixels.stream().map(str -> str.charAt(0)).allMatch(x -> x == checkChar)) {
                newPixels = new ArrayList<>(newPixels.stream().map(str -> str.substring(1)).toList());
                prune = true;
            }
            if (newPixels.stream().map(str -> str.charAt(str.length() - 1)).allMatch(x -> x == checkChar)) {
                newPixels = new ArrayList<>(newPixels.stream().map(str -> str.substring(0, str.length() - 1)).toList());
                prune = true;
            }
        }

        return new Image(newPixels, newInfinity);
    }

    public int buildIndex(Image in, int x, int y) {
        StringBuilder numberString = new StringBuilder();
        for (int j = y-1; j <= y+1; j++) {
            for (int i = x-1; i <= x+1; i++) {
                numberString.append(in.at(i,j) ? '1' : '0');
            }
        }
        return Integer.parseInt(numberString.toString(), 2);
    }
}
