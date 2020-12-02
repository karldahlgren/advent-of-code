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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day1Test {

    private static final int[] NUMBERS = Arrays.stream(Utility.parseFile("Day1.txt"))
            .mapToInt(Integer::parseInt)
            .toArray();

    @Test
    @DisplayName("December 1: Part 1")
    void testPart1(){
        final Optional<Integer> result = Day1.executePart1(NUMBERS);
        assertTrue(result.isPresent());
        assertEquals(864864, result.get());
    }

    @Test
    @DisplayName("December 1: Part 1 - Corner case 1")
    void testPart1CornerCase1(){
        final int[] numbers = new int[] { 2020 };

        final Optional<Integer> result = Day1.executePart1(numbers);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("December 1: Part 1 - Corner case 2")
    void testPart1CornerCase2(){
        final int[] numbers = new int[] { 1010 };

        final Optional<Integer> result = Day1.executePart1(numbers);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("December 1: Part 1 - Corner case 3")
    void testPart1CornerCase3(){
        final int[] numbers = new int[] { 1010, 1010 };

        final Optional<Integer> result = Day1.executePart1(numbers);
        assertTrue(result.isPresent());
        assertEquals(1020100, result.get());
    }

    @Test
    @DisplayName("December 1: Part 2")
    void testPart2(){
        final Optional<Integer> result = Day1.executePart2(NUMBERS);
        assertTrue(result.isPresent());
        assertEquals(281473080, result.get());
    }

    @Test
    @DisplayName("December 1: Part 2 - Corner case 1")
    void testPart2CornerCase1(){
        final int[] numbers = new int[] { 2020 };

        final Optional<Integer> result = Day1.executePart1(numbers);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("December 1: Part 2 - Corner case 2")
    void testPart2CornerCase2(){
        final int[] numbers = new int[] { 1000,20 };

        final Optional<Integer> result = Day1.executePart1(numbers);
        assertFalse(result.isPresent());
    }

}
