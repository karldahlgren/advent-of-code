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
import java.util.Optional;
import java.util.Set;

public final class Day1 {

    private static final int GOAL = 2020;

    private Day1() {

    }

    public static Optional<Integer> executePart1(final int[] numbers) {
        // Store a map of all numbers extracted with 2020 and the respective index for each number.
        // We want to store the index since we can have two numbers with the same value and we want
        // to verify that we do not multiple the same index.
        final Map<Integer, Set<Integer>> indices = new HashMap<>();

        for(int index = 0; index < numbers.length; index++) {
            // Get current value
            int value = numbers[index];

            // Check whether the value which we would like to multiple with already exists
            // in our index map. If so, find it and verify that it does not have the same
            // index as the current index
            if(indices.containsKey(value)) {
                final Integer secondIndex = findMatchedIndex(indices.get(value), index);
                if(secondIndex != null){
                    // The second index is not the same as the current index
                    // Multiple the value at the index and the value at the second index
                    return Optional.of(numbers[index] * numbers[secondIndex]);
                }
            }

            // Calculate the value which we would like to multiple with the current value
            int extractedValue = GOAL - value;
            if(!indices.containsKey(extractedValue)){
                indices.put(extractedValue, new HashSet<>());
            }

            // Store the current value's index
            indices.get(extractedValue).add(index);
        }

        return Optional.empty();
    }


    public static Optional<Integer> executePart2(final int[] numbers) {
        final Map<Integer, Set<Integer>> indices = new HashMap<>();

        for(int index = 0; index < numbers.length; index++) {
            int value = numbers[index];

            if(!indices.containsKey(value)){
                indices.put(value, new HashSet<>());
            }

            indices.get(value).add(index);
        }

        for(int i = 0; i < numbers.length - 1; i++) {
            for(int j = i + 1; j < numbers.length; j++) {
                int value = GOAL - (numbers[i] + numbers[j]);

                if(indices.containsKey(value)) {
                    final Integer lookupIndex = findMatchedIndex(indices.get(value), i, j);
                    if(lookupIndex != null){
                        return Optional.of(numbers[i] * numbers[j] * numbers[lookupIndex]);
                    }
                }
            }
        }

        return Optional.empty();
    }


    private static Integer findMatchedIndex(final Set<Integer> matchedIndices, final int ignoredIndex) {
        return matchedIndices
                .stream()
                .filter(index -> index != ignoredIndex)
                .findFirst()
                .orElse(null);
    }

    private static Integer findMatchedIndex(final Set<Integer> matchedIndices,
                                            final int ignoredIndex1, final int ignoredIndex2) {
        return matchedIndices
                .stream()
                .filter(index -> index != ignoredIndex1 && index != ignoredIndex2)
                .findFirst()
                .orElse(null);
    }

}
