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

package io.dahlgren.advent;

import java.util.HashSet;
import java.util.Set;

public class Day9 {

    private static final int PREAMBLE_COUNT = 25;

    private Day9() {

    }

    public static long executePart1(final long[] numbers){
        for(int x = PREAMBLE_COUNT; x < numbers.length; x++){
            final Set<Long> sums = new HashSet<>();

            for(int y = x - PREAMBLE_COUNT; y < x - 1; y++){
                for(int z = y+1; z < x; z++){
                    final long sum = numbers[y] + numbers[z];
                    sums.add(sum);
                }
            }

            if (!sums.contains(numbers[x])) {
                return numbers[x];
            }
        }

        return -1;
    }

    public static long executePart2(final long[] numbers, final long sum){
        final Set<Long> result = new HashSet<>();
        for(int x = 0; x < numbers.length - 1; x++){
            long current = numbers[x];
            result.add(current);
            for(int y = x+1; y < numbers.length; y++){
                current +=  numbers[y];

                if(current == sum){
                    break;
                } else if(current < sum){
                    result.add(numbers[y]);
                } else {
                    result.clear();
                    break;
                }
            }

            if(current == sum){
                break;
            }
        }

        final long min = result.stream()
                .mapToLong(x -> x)
                .min()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find min"));

        final long max = result.stream()
                .mapToLong(x -> x)
                .max()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find max"));

        return min + max;
    }

}
