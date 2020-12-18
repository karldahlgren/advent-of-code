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
import java.util.Objects;

public class Day18 {


    private Day18() {

    }

    public static long executePart1(final String[] lines){
        return Arrays.stream(lines)
                .map(line -> calculatePart1(line, 0))
                .map(result -> result.sum)
                .mapToLong(x -> x)
                .sum();
    }

    public static long executePart2(final String[] lines){
        return Arrays.stream(lines)
                .map(line -> calculatePart2(line, 0))
                .map(result -> result.sum)
                .mapToLong(x -> x)
                .sum();
    }

    private static Result calculatePart1(final String line, int index){
        Long numberA = null;
        Character operator = null;
        while (index < line.length() && line.charAt(index) != ')') {
            if(Character.isDigit(line.charAt(index))){
                if(numberA == null){
                    numberA = (long)Character.getNumericValue(line.charAt(index));
                } else {
                    final long numberB = Character.getNumericValue(line.charAt(index));
                    numberA = calculate(numberA, numberB, operator);
                    operator = null;
                }
            } else if(line.charAt(index) == '+' || line.charAt(index) == '*') {
                operator = line.charAt(index);
            } else if(line.charAt(index) == '(') {
                final Result result = calculatePart1(line, index + 1);
                index = result.newOffset;
                if(numberA == null){
                    numberA = result.sum;
                } else {
                    final long numberB = result.sum;
                    numberA = calculate(numberA, numberB, operator);
                    operator = null;
                }
            }
            index++;
        }
        return new Result(numberA, index);
    }

    private static Result calculatePart2(final String line, int startIndex){
        Long numberA = null;
        Character operator = null;
        int index = startIndex;
        while (index < line.length() && line.charAt(index) != ')') {
            if(Character.isDigit(line.charAt(index))){
                if(numberA == null){
                    numberA = (long)Character.getNumericValue(line.charAt(index));
                } else {
                    long numberB = Character.getNumericValue(line.charAt(index));
                    numberA = calculate(numberA, numberB, operator);
                    operator = null;
                }
            } else if(line.charAt(index) == '+') {
                operator = line.charAt(index);
            } else if(line.charAt(index) == '*') {
                final Result result = calculatePart2(line, index + 1);
                operator = line.charAt(index);
                index = result.newOffset;
                numberA = calculate(numberA, result.sum, operator);
                operator = null;
                if(index < line.length() && line.charAt(index) == ')'){
                    return new Result(numberA, index);
                }
            } else if(line.charAt(index) == '(') {
                final Result result = calculatePart2(line, index + 1);
                index = result.newOffset;
                if(numberA == null){
                    numberA = result.sum;
                } else {
                    long numberB = result.sum;
                    numberA = calculate(numberA, numberB, operator);
                    operator = null;
                }
            }
            index++;
        }
        return new Result(numberA, index);
    }

    private static long calculate(final Long numberA, final Long numberB, final Character operator){
        if(operator == null){
            throw new IllegalArgumentException("Missing operator");
        }

        return switch (operator) {
            case '+' -> numberA + numberB;
            case '*' -> numberA * numberB;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

    private static class Result {

        private final long sum;
        private final int newOffset;

        public Result(final Long sum, final Integer newOffset) {
            this.sum = Objects.requireNonNull(sum);
            this.newOffset = Objects.requireNonNull(newOffset);
        }
    }
}
