/*
 * The MIT License
 *
 * Copyright (c) 2020-, Karl A. Dahlgren
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.dahlgren.advent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class Day17 {

    private static final char ACTIVE = '#';
    private static final char INACTIVE = '.';

    private Day17() {

    }

    public static int executePart1(final String[] lines){
        Map<Coordinate, Character> nodes = new HashMap<>();
        for(int y = 0; y < lines.length;  y++){
            for(int x = 0; x < lines[y].length(); x++) {
                nodes.put(new Coordinate(x, y, 0, 0), lines[y].charAt(x));
            }
        }

        for(int cycle = 0; cycle < 6; cycle++) {
            final Map<Coordinate, Character> nextCycle = getNextCycle(nodes, Day17::getNeighborsPart1);
            final Map<Coordinate, Integer> activeNeighborCount = count(nextCycle, Day17::getNeighborsPart1);
            update(nextCycle, activeNeighborCount);
            nodes = nextCycle;
        }

        return (int) nodes.values().stream()
                .filter(value -> value == ACTIVE)
                .count();
    }

    public static int executePart2(final String[] lines){
        Map<Coordinate, Character> nodes = new HashMap<>();
        for(int y = 0; y < lines.length;  y++){
            for(int x = 0; x < lines[y].length(); x++) {
                nodes.put(new Coordinate(x, y, 0, 0), lines[y].charAt(x));
            }
        }

        for(int cycle = 0; cycle < 6; cycle++) {
            final Map<Coordinate, Character> nextCycle = getNextCycle(nodes, Day17::getNeighborsPart2);
            final Map<Coordinate, Integer> activeNeighborCount = count(nextCycle, Day17::getNeighborsPart2);
            update(nextCycle, activeNeighborCount);
            nodes = nextCycle;
        }

        return (int) nodes.values().stream()
                .filter(value -> value == ACTIVE)
                .count();
    }

    private static Map<Coordinate, Character> getNextCycle(final Map<Coordinate, Character> nodes, final Function<Coordinate, Set<Coordinate>> function){
        final Map<Coordinate, Character> nextCycle = new HashMap<>();
        for(Map.Entry<Coordinate, Character> entry : nodes.entrySet()) {
            final Set<Coordinate> neighbors = function.apply(entry.getKey());
            for(Coordinate neighbor : neighbors){
                nextCycle.put(neighbor, nodes.getOrDefault(neighbor, INACTIVE));
            }
        }

        return nextCycle;
    }

    private static Map<Coordinate, Integer> count(final Map<Coordinate, Character> nodes, final Function<Coordinate, Set<Coordinate>> function){
        final Map<Coordinate, Integer> activeNeighborCount = new HashMap<>();
        for(Map.Entry<Coordinate, Character> entry : nodes.entrySet()) {
            final Set<Coordinate> neighbors = function.apply(entry.getKey());
            int count = 0;
            for(Coordinate neighbor : neighbors){
                final char value = nodes.getOrDefault(neighbor, INACTIVE);
                if(value == ACTIVE) {
                    count++;
                }
            }
            activeNeighborCount.put(entry.getKey(), count);
        }

        return activeNeighborCount;
    }

    private static void update(final Map<Coordinate, Character> nodes, final Map<Coordinate, Integer> activeNeighbors){
        nodes.forEach((key, value) -> {
            int count = activeNeighbors.get(key);
            if (value == ACTIVE && (count != 2 && count != 3)) {
                nodes.put(key, INACTIVE);
            } else if (value == INACTIVE && count == 3) {
                nodes.put(key, ACTIVE);
            }
        });
    }

    private static Set<Coordinate> getNeighborsPart1(final Coordinate coordinate){
        final Set<Coordinate> coordinates = new HashSet<>();
        for(int x = coordinate.x -1; x <= coordinate.x + 1; x++){
            for(int y = coordinate.y -1; y <= coordinate.y + 1; y++){
                for(int z = coordinate.z -1; z <= coordinate.z + 1; z++){
                    if(x == coordinate.x && y == coordinate.y && z == coordinate.z) {
                        continue;
                    }

                    coordinates.add(new Coordinate(x,y,z,0));
                }
            }
        }

        return coordinates;
    }

    private static Set<Coordinate> getNeighborsPart2(final Coordinate coordinate){
        final Set<Coordinate> coordinates = new HashSet<>();
        for(int x = coordinate.x -1; x <= coordinate.x + 1; x++){
            for(int y = coordinate.y -1; y <= coordinate.y + 1; y++){
                for(int z = coordinate.z -1; z <= coordinate.z + 1; z++){
                    for(int w = coordinate.w -1; w <= coordinate.w + 1; w++){
                        if(x == coordinate.x && y == coordinate.y && z == coordinate.z && w == coordinate.w) {
                            continue;
                        }

                        coordinates.add(new Coordinate(x,y,z,w));
                    }
                }
            }
        }

        return coordinates;
    }

    private static class Coordinate {

        private final int x;
        private final int y;
        private final int z;
        private final int w;

        public Coordinate(int x, int y, int z, int w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y && z == that.z && w == that.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, w);
        }
    }

}
