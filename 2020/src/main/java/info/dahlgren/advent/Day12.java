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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day12 {

    private enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private static final int EAST_POSITION = 0;
    private static final int NORTH_POSITION = 1;
    private static final int WEST_POSITION = 2;
    private static final int SOUTH_POSITION = 3;
    private static final char NORTH = 'N'; // Action N means to move north by the given value.
    private static final char SOUTH = 'S'; // Action S means to move south by the given value.
    private static final char EAST = 'E'; // Action E means to move east by the given value.
    private static final char WEST = 'W'; // Action W means to move west by the given value.
    private static final char LEFT = 'L'; //  Action L means to turn left the given number of degrees.
    private static final char RIGHT = 'R'; // Action R means to turn right the given number of degrees.
    private static final char FORWARD = 'F'; // Action F means to move forward by the given value in the direction the ship is currently facing.

    private Day12() {

    }

    public static int executePart1(final String[] instructions){
        int east = 0;
        int north = 0;
        Direction direction = Direction.EAST;
        for(final String instruction : instructions){
            final char action = instruction.charAt(0);
            final int value = Integer.parseInt(instruction.substring(1));
            switch (action) {
                case NORTH:
                    north += value;
                    break;
                case SOUTH:
                    north -= value;
                    break;
                case EAST:
                    east += value;
                    break;
                case WEST:
                    east -= value;
                    break;
                case LEFT:
                    direction = turn(direction, value * -1);
                    break;
                case RIGHT:
                    direction = turn(direction, value);
                    break;
                case FORWARD:
                    switch (direction) {
                        case EAST -> east += value;
                        case WEST -> east -= value;
                        case SOUTH -> north -= value;
                        case NORTH -> north += value;
                    }
                    break;
                default:
                    throw new IllegalStateException("Invalid state: " + instruction);
            }
        }

        return Math.abs(east) + Math.abs(north);
    }

    public static int executePart2(final String[] instructions) {
        int east = 0;
        int north = 0;
        int[] waypoint = new int[] {10, 1, 0, 0};
        for(final String instruction : instructions){
            final char action = instruction.charAt(0);
            final int value = Integer.parseInt(instruction.substring(1));
            switch (action) {
                case NORTH, SOUTH, EAST, WEST -> waypoint = updateWaypoint(waypoint, action, value);
                case LEFT -> waypoint = rotate(waypoint, value * -1);
                case RIGHT -> waypoint = rotate(waypoint, value);
                case FORWARD -> {
                    east += waypoint[EAST_POSITION] * value;
                    east -= waypoint[WEST_POSITION] * value;
                    north += waypoint[NORTH_POSITION] * value;
                    north -= waypoint[SOUTH_POSITION] * value;
                }
                default -> throw new IllegalStateException("Invalid state: " + instruction);
            }
        }

        return Math.abs(east) + Math.abs(north);
    }

    private static int[] updateWaypoint(final int[] waypoint, final char action, final int value){
        int[] newWayPoint = waypoint.clone();
        switch (action) {
            case NORTH -> newWayPoint[NORTH_POSITION] += value;
            case SOUTH -> newWayPoint[SOUTH_POSITION] += value;
            case EAST -> newWayPoint[EAST_POSITION] += value;
            case WEST -> newWayPoint[WEST_POSITION] += value;
        }

        return newWayPoint;
    }

    private static int[] rotate(final int[] waypoint, final int value){
        int rotations = (value / 90) * -1;
        List<Integer> rotationList = Arrays.stream(waypoint).boxed().collect(toList());
        Collections.rotate(rotationList, rotations);
        return rotationList.stream().mapToInt(x -> x).toArray();
    }

    private static Direction turn(final Direction direction, final int degrees){
        if(degrees == 0){
            return direction;
        }

        int startDegree = -1;
        switch (direction) {
            case EAST -> startDegree = 0;
            case WEST -> startDegree = 180;
            case SOUTH -> startDegree = 90;
            case NORTH -> startDegree = 270;
        };

        int newDegree = (startDegree + degrees) % 360;
        return switch (newDegree) {
            case 0 -> Direction.EAST;
            case 90,-270 -> Direction.SOUTH;
            case 180,-180 -> Direction.WEST;
            case 270,-90 -> Direction.NORTH;
            default -> throw new IllegalStateException("Unexpected value: " + newDegree);
        };
    }
}
