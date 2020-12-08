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

public class Day8 {

    private static final String ACCUMULATOR = "acc";
    private static final String JUMP = "jmp";
    private static final String NO_OPERATION = "nop";

    private Day8() {

    }

    public static int executePart1(final String[] instructions){
        return getAccumulator(instructions, true);
    }

    public static int executePart2(final String[] instructions){
        for(int index = 0; index < instructions.length; index++){
            final String instruction = instructions[index];
            final String[] instructionParts = instruction.split(" ");

            if(instructionParts.length != 2){
                throw new IllegalArgumentException("Unable to parse the following instruction: " + instruction);
            }

            final String operation = instructionParts[0];
            final String argument = instructionParts[1];

            // Update operation
            if(operation.equals(JUMP)){
                instructions[index] = NO_OPERATION + " " + argument;
            } else if(operation.equals(NO_OPERATION)){
                instructions[index] = JUMP + " " + argument;
            }

            // Test the updated instructions
            if(operation.equals(JUMP) || operation.equals(NO_OPERATION)){
                final int result = getAccumulator(instructions, false);
                if(result != -1){
                    return result;
                }
            }

            // Reset operation
            if(operation.equals(JUMP)){
                instructions[index] = JUMP + " " + argument;
            } else if(operation.equals(NO_OPERATION)){
                instructions[index] = NO_OPERATION + " " + argument;
            }
        }

        return 0;
    }

    public static int getAccumulator(final String[] instructions, boolean returnAccumulatorOnInfiniteLoop){
        int accumulator = 0;
        int index = 0;
        final Set<Integer> executedRow = new HashSet<>();
        while (index < instructions.length){
            if(executedRow.contains(index)){
                if(returnAccumulatorOnInfiniteLoop){
                    return accumulator;
                }
                return -1;
            }
            executedRow.add(index);

            final String instruction = instructions[index];
            final String[] instructionParts = instruction.split(" ");

            if(instructionParts.length != 2){
                throw new IllegalArgumentException("Unable to parse the following instruction: " + instruction);
            }

            final String operation = instructionParts[0];
            final int modifier = getModifier(instructionParts[1]);

            switch (operation) {
                case ACCUMULATOR -> {
                    accumulator += modifier;
                    index++;
                }
                case JUMP -> index += modifier;
                case NO_OPERATION -> index++;
                default -> throw new IllegalArgumentException("Unable to map operation: " + operation);
            }
        }

        return accumulator;
    }

    public static int getModifier(final String value){
        if(value.length() < 2){
            throw new IllegalArgumentException("Invalid length for modifier: " + value);
        }

        return switch (value.charAt(0)) {
            case '+' -> Integer.parseInt(value.substring(1));
            case '-' -> Integer.parseInt(value.substring(1)) * -1;
            default -> throw new IllegalArgumentException("Unable to parse modifier: " + value);
        };

    }

}
