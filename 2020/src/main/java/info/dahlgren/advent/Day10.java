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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Day10 {

    private static final int MAX = 3;

    private Day10() {

    }

    public static int executePart1(final int[] numbers){
        Arrays.sort(numbers);

        int oneDiff = 0;
        int threeDiff = 1;

        int previousNumber = 0;
        for (int currentNumber : numbers) {
            if (currentNumber - previousNumber == 1) {
                oneDiff++;
            } else if (currentNumber - previousNumber == 3) {
                threeDiff++;
            }
            previousNumber = currentNumber;
        }

        return oneDiff * threeDiff;
    }

    public static long executePart2(final int[] numbers) {
        final List<Integer> list = Arrays.stream(numbers)
                .boxed()
                .sorted()
                .collect(toList());
        list.add(0,0);

        return traverse(list, 0, new HashMap<>());
    }

    private static long traverse(final List<Integer> numbers, final int startIndex, final Map<Integer, Long> results){
        if(startIndex == numbers.size() - 1){
            return 1L;
        } else if(results.containsKey(startIndex)){
            return results.get(startIndex);
        }

        long result = 0L;
        int index = startIndex + 1;
        while(index < numbers.size() && numbers.get(index) - numbers.get(startIndex) <= MAX){
            result += traverse(numbers, index, results);
            index++;
        }

        results.put(startIndex, result);
        return result;
    }

}
