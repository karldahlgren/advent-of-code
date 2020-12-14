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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {

    private static final String MEM_POSITION_REGEXP= "(?<=\\[)(.*?)(?=\\])";
    private static final String MEM_VALUE_REGEX = "(?<== ).*$";

    private Day14() {

    }

    public static long executePart1(final String[] inputs){
        final Map<Integer, Long> values = new HashMap<>();
        String mask = null;
        for(final String input : inputs){
            if(input.startsWith("mask")){
                mask = getMask(input);
            } else if(input.startsWith("mem")) {
                if(mask == null){
                    throw new IllegalArgumentException("Mask cannot be null");
                }

                final Integer position = getMemPosition(input);
                final Integer value = getMemValue(input);
                final String binary = intToBinary(value);
                final String result = applyMaskPart1(binary, mask);
                values.put(position,  binaryToLong(result));
            }
        }

        return values.values()
                .stream()
                .mapToLong(x -> x)
                .sum();
    }

    public static long executePart2(final String[] inputs){
        final Map<Long, Integer> values = new HashMap<>();
        String mask = null;
        for(final String input : inputs){
            if(input.startsWith("mask")){
                mask = getMask(input);
            } else if(input.startsWith("mem")) {
                if(mask == null){
                    throw new IllegalArgumentException("Mask cannot be null");
                }

                final Integer position = getMemPosition(input);
                final Integer value = getMemValue(input);
                final String binary = intToBinary(position);
                final String template = applyMaskPart2(binary, mask);
                final Set<String> combinations = getCombinations(template);

                for(final String combination : combinations){
                    values.put(binaryToLong(combination), value);
                }
            }
        }

        return values.values()
                .stream()
                .mapToLong(x -> x)
                .sum();
    }


    private static String applyMaskPart1(final String binary, final String mask){
        if(binary.length() != mask.length()){
            throw new IllegalArgumentException("Invalid binary and mask: " + binary);
        }

        char[] output = binary.toCharArray();
        for(int index = 0; index < mask.length(); index++){
            if(mask.charAt(index) != 'X'){
                output[index] = mask.charAt(index);
            }
        }

        return new String(output);
    }

    private static String applyMaskPart2(final String binary, final String mask){
        if(binary.length() != mask.length()){
            throw new IllegalArgumentException("Invalid binary and mask: " + binary);
        }

        char[] output = binary.toCharArray();
        for(int index = 0; index < mask.length(); index++){
            if(mask.charAt(index) != '0'){
                output[index] = mask.charAt(index);
            }
        }

        return new String(output);
    }

    private static Set<String> getCombinations(final String template){
        final Set<String> combinations = new HashSet<>();
        getCombinations(template, template.replaceAll("X", "0"), combinations, 0);
        return combinations;
    }

    private static void getCombinations(final String template, final String input, final Set<String> output, int index){
        output.add(input);
        char[] modified = input.toCharArray();
        for(; index < input.length(); index++){
            if(template.charAt(index) == 'X'){
                modified[index] = '1';
                getCombinations(template, new String(modified), output, index + 1);
                modified[index] = '0';
            }
        }
    }

    private static Long binaryToLong(final String binary){
        return Long.parseLong(binary, 2);
    }

    private static String intToBinary(final int value){
        final String binary = Integer.toBinaryString(value);
        return String.format("%36s", binary).replaceAll(" ", "0");
    }

    private static Integer getMemValue(final String input){
        final Pattern pattern = Pattern.compile(MEM_VALUE_REGEX);
        final Matcher matcher = pattern.matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Unable to find mem position: " + input);
        }
        return Integer.parseInt(matcher.group(0));
    }

    private static Integer getMemPosition(final String input){
        final Pattern pattern = Pattern.compile(MEM_POSITION_REGEXP);
        final Matcher matcher = pattern.matcher(input);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Unable to find mem position: " + input);
        }
        return Integer.parseInt(matcher.group(0));
    }

    private static String getMask(final String input){
        return input.substring(7);
    }

}
