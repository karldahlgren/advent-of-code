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

import java.util.Arrays;
import java.util.Objects;

public final class Day2 {

    private static final class PasswordPolicy {

        private final String password;
        private final char character;
        private final int position1;
        private final int position2;

        public PasswordPolicy(final String password, final char character,
                              final int position1, final int position2) {
            this.password = Objects.requireNonNull(password);
            this.character = character;
            this.position1 = position1;
            this.position2 = position2;
        }

    }

    private Day2() {

    }

    public static long executePar1(final String[] lines) {
        return Arrays.stream(lines)
                .map(Day2::validatePart1)
                .filter(value -> value)
                .count();
    }

    public static long executePar2(final String[] lines) {
        return Arrays.stream(lines)
                .map(Day2::validatePart2)
                .filter(value -> value)
                .count();
    }

    private static boolean validatePart1(final String line){
        final PasswordPolicy policy = getPasswordPolicy(line);
        int count = 0;
        for(int index = 0; index < policy.password.length(); index++){
            if(policy.password.charAt(index) == policy.character){
                count++;
            }
        }

        return count >= policy.position1 && count <= policy.position2;
    }

    private static boolean validatePart2(final String line){
        final PasswordPolicy policy = getPasswordPolicy(line);
        if(policy.position1 > policy.password.length() || policy.position2 > policy.password.length()){
            return false;
        }

        if(policy.password.charAt(policy.position1 - 1) == policy.character &&
                policy.password.charAt(policy.position2 - 1) == policy.character){
            return false;
        } else if(policy.password.charAt(policy.position1 - 1) == policy.character){
            return true;
        } else if(policy.password.charAt(policy.position2 - 1) == policy.character){
            return true;
        }

        return false;
    }

    private static PasswordPolicy getPasswordPolicy(final String line){
        final String[] part = line.split(": ");
        if(part.length != 2){
            throw new IllegalArgumentException("Invalid parts: " + line);
        }

        final String[] policyParts = part[0].split(" ");

        if(policyParts.length != 2){
            throw new IllegalArgumentException("Invalid policy parts: " + line);
        }

        final String[] rangePart = policyParts[0].split("-");

        if(rangePart.length != 2){
            throw new IllegalArgumentException("Invalid range parts: " + line);
        }
        if(policyParts[1].length() > 1){
            throw new IllegalArgumentException("Invalid second policy part: " + line);
        }

        final String password = part[1];
        final char character = policyParts[1].charAt(0);
        final int position1 = Integer.parseInt(rangePart[0]);
        final int position2 = Integer.parseInt(rangePart[1]);

        return new PasswordPolicy(password, character, position1, position2);
    }

}
