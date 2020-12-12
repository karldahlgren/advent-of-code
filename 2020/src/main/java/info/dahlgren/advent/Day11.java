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

public class Day11 {

    private static final class Adjacent {

        private final int row;
        private final int column;

        public Adjacent(int row, int column) {
            this.row = row;
            this.column = column;
        }

    }

    private static final char OCCUPIED_SEAT = '#';
    private static final char EMPTY_SEAT = 'L';
    private static final Adjacent[] ADJACENTS = {
            new Adjacent(-1, 0),
            new Adjacent(-1, -1),
            new Adjacent(-1, 1),
            new Adjacent(0, -1),
            new Adjacent(0, 1),
            new Adjacent(1, -1),
            new Adjacent(1, 0),
            new Adjacent(1, 1)
    };

    private Day11() {

    }

    public static int executePart1(final char[][] layout){
        char[][] previous = copy(layout);
        char[][] current = copy(layout);
        while(true){
            for(int row = 0; row < layout.length; row++){
                for(int column = 0; column < layout[row].length; column++){
                    if(previous[row][column] == EMPTY_SEAT &&
                            checkAdjacentPart1(previous, row, column) == 0){
                        current[row][column] = OCCUPIED_SEAT;
                    } else if(previous[row][column] == OCCUPIED_SEAT &&
                            checkAdjacentPart1(previous, row, column) >= 4){
                        current[row][column] = EMPTY_SEAT;
                    }
                }
            }

            if(isEquals(previous, current)){
                return countOccupied(previous);
            }

            previous = copy(current);
        }
    }

    public static int executePart2(final char[][] layout) {
        char[][] previous = copy(layout);
        char[][] current = copy(layout);
        while(true){
            for(int row = 0; row < layout.length; row++){
                for(int column = 0; column < layout[row].length; column++){
                    if(previous[row][column] == EMPTY_SEAT &&
                            checkAdjacentPart2(previous, row, column) == 0){
                        current[row][column] = OCCUPIED_SEAT;
                    } else if(previous[row][column] == OCCUPIED_SEAT &&
                            checkAdjacentPart2(previous, row, column) >= 5){
                        current[row][column] = EMPTY_SEAT;
                    }
                }
            }

            if(isEquals(previous, current)){
                return countOccupied(previous);
            }

            previous = copy(current);
        }
    }

    private static int countOccupied(final char[][] matrix){
        int occupied = 0;
        for (char[] rows : matrix) {
            for (char seat : rows) {
                if (seat == OCCUPIED_SEAT) {
                    occupied++;
                }
            }
        }
        return occupied;
    }

    private static int checkAdjacentPart1(final char[][] matrix, final int row, final int column){
        int adjacentCount = 0;
        for(final Adjacent adjacent : ADJACENTS){
            int newRow = row + adjacent.row;
            int newColumn = column + adjacent.column;
            if (newRow >= 0 && newRow < matrix.length &&
                    newColumn >= 0 && newColumn < matrix[row].length &&
                    matrix[newRow][newColumn] == OCCUPIED_SEAT){
                adjacentCount++;
            }
        }
        return adjacentCount;
    }

    private static int checkAdjacentPart2(final char[][] matrix, final int row, final int column){
        int adjacentCount = 0;
        for(final Adjacent adjacent : ADJACENTS){
            int newRow = row + adjacent.row;
            int newColumn = column + adjacent.column;
            while(newRow >= 0 && newRow < matrix.length &&
                    newColumn >= 0 && newColumn < matrix[row].length){
                if(matrix[newRow][newColumn] == OCCUPIED_SEAT){
                    adjacentCount++;
                    break;
                } else if(matrix[newRow][newColumn] == EMPTY_SEAT){
                    break;
                }

                newRow+= adjacent.row;
                newColumn+= adjacent.column;
            }
        }
        return adjacentCount;
    }

    private static boolean isEquals(final char[][] matrix1, final char[][] matrix2){
        for(int row = 0; row < matrix1.length; row++){
            for(int column = 0; column < matrix1[row].length; column++){
                if(matrix1[row][column] != matrix2[row][column]){
                    return false;
                }
            }
        }

        return true;
    }

    private static char[][] copy(final char[][] matrix){
        return Arrays.stream(matrix)
                .map(char[]::clone)
                .toArray(char[][]::new);
    }

}
