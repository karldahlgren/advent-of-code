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

import java.util.ArrayList;
import java.util.List;

public class Day5 {

    private static final char FRONT = 'F'; // lower half
    private static final char BACK = 'B'; // upper half
    private static final char LEFT = 'L'; // left
    private static final char RIGHT = 'R'; // right

    private Day5() {

    }

    public static int executePart1(final String[] boardingPasses){
        int highestId = Integer.MIN_VALUE;
        for(final String boardingPass : boardingPasses){
            final int row = getSeat(boardingPass, 0, 0, 127);
            final int column = getSeat(boardingPass, 7, 0, 7);
            final int id = (row * 8) + column;
            if(id > highestId){
                highestId = id;
            }
        }

        return highestId;
    }

    public static int executePart2(final String[] boardingPasses){
        final List<Integer> ids = new ArrayList<>();

        int highestId = Integer.MIN_VALUE;
        int lowestId = Integer.MAX_VALUE;
        for(final String boardingPass : boardingPasses){
            final int row = getSeat(boardingPass, 0, 0, 127);
            final int column = getSeat(boardingPass, 7, 0, 7);
            final int id = (row * 8) + column;
            ids.add(id);

            if(id > highestId){
                highestId = id;
            }
            if(id < lowestId){
                lowestId = id;
            }
        }

        for(int id = lowestId; id < highestId; id++) {
            if(!ids.contains(id)) {
                return id;
            }
        }

        throw new IllegalArgumentException("Unable to find the seat");
    }

    public static int getSeat(final String seat, final int index, final int startRange, final int endRange){
        if(startRange == endRange){
            return startRange;
        }

        final int effect = (int) Math.ceil((double)(endRange - startRange) / 2) ;
        if(seat.charAt(index) == FRONT || seat.charAt(index) == LEFT){
            return getSeat(seat, index + 1, startRange, endRange - effect);
        } else if(seat.charAt(index) == BACK || seat.charAt(index) == RIGHT){
            return getSeat(seat, index + 1, startRange + effect, endRange);
        }

        throw new IllegalArgumentException("Invalid seat: " + seat);
    }

}
