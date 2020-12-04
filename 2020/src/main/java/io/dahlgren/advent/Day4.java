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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day4 {

    private static final String COUNTRY_ID = "cid";
    private static final String BIRTH_YEAR = "byr";
    private static final String ISSUE_YEAR = "iyr";
    private static final String EXPIRATION_YEAR = "eyr";
    private static final String HEIGHT = "hgt";
    private static final String HAIR_COLOR = "hcl";
    private static final String EYE_COLOR = "ecl";
    private static final String PASSPORT_ID = "pid";
    private static final String EMPTY_LINE = "";

    private static final Set<Character> ALLOWED_HAIR_COLOR_CHARACTERS = Set.of(
            'a', 'b', 'c', 'd', 'e', 'f',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    private Day4() {

    }

    public static int executePart1(final String[] input){

        int validPasswords = 0;
        final Set<String> passport = new HashSet<>();
        for (String line : input) {

            if (line.equals(EMPTY_LINE)) {
                if(isValidPart1(passport)){
                    validPasswords++;
                }
                passport.clear();
                continue;
            }

            final String[] fields = line.split(" ");
            for (String field : fields) {
                final String[] fieldParts = field.split(":");

                if (fieldParts.length != 2) {
                    throw new IllegalArgumentException("Invalid field; " + line);
                }

                passport.add(fieldParts[0]);
            }
        }

        if(!passport.isEmpty()){
            if(isValidPart1(passport)){
                validPasswords++;
            }
        }

        return validPasswords;
    }

    public static int executePart2(final String[] input){

        int validPasswords = 0;
        final Map<String, String> passport = new HashMap<>();
        for (String line : input) {

            if (line.equals(EMPTY_LINE)) {
                if(isValidPart2(passport)){
                    validPasswords++;
                }
                passport.clear();
                continue;
            }

            final String[] fields = line.split(" ");
            for (String field : fields) {
                final String[] fieldParts = field.split(":");

                if (fieldParts.length != 2) {
                    throw new IllegalArgumentException("Invalid field; " + line);
                }

                passport.put(fieldParts[0], fieldParts[1]);
            }
        }

        if(!passport.isEmpty()){
            if(isValidPart2(passport)){
                validPasswords++;
            }
        }

        return validPasswords;
    }



    private static boolean isValidPart1(final Set<String> passport){
        if (passport.size() == 8) {
            return true;
        } else return passport.size() == 7 && !passport.contains(COUNTRY_ID);
    }

    private static boolean isValidPart2(final Map<String, String> passport){
        return validateRange(passport.get(BIRTH_YEAR),  1920, 2002) &&
                validateRange(passport.get(ISSUE_YEAR),  2010, 2020) &&
                validateRange(passport.get(EXPIRATION_YEAR),  2020, 2030) &&
                validateHeight(passport.get(HEIGHT)) &&
                validateHairColor(passport.get(HAIR_COLOR)) &&
                validateEyeColor(passport.get(EYE_COLOR)) &&
                validatePasswordId(passport.get(PASSPORT_ID));
    }


    private static boolean validateRange(final String data, final int rangeStart, final int rangeEnd){
        if(data == null){
            return false;
        }

        try {
            final int year = Integer.parseInt(data);
            return year >= rangeStart && year <= rangeEnd;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private static boolean validateHeight(final String data){
        if(data == null){
            return false;
        }

        if(data.endsWith("cm")){
            return validateRange(data.replace("cm", ""), 150, 193);
        } else if(data.endsWith("in")){
            return validateRange(data.replace("in", ""), 59, 76);
        }

        return false;
    }

    private static boolean validateHairColor(final String data){
        if(data == null){
            return false;
        }
        if(data.length() != 7){
            return false;
        }
        if(data.charAt(0) != '#'){
            return false;
        }
        for(int index = 1; index < data.length(); index++){
            if(!ALLOWED_HAIR_COLOR_CHARACTERS.contains(data.charAt(index))){
                return false;
            }
        }

        return true;
    }

    private static boolean validateEyeColor(final String data){
        if(data == null){
            return false;
        }

        return switch (data) {
            case "amb", "blu", "brn", "gry", "grn", "hzl", "oth" -> true;
            default -> false;
        };
    }

    private static boolean validatePasswordId(final String data){
        if(data == null){
            return false;
        }

        if(data.length() != 9){
            return false;
        }
        for(char character : data.toCharArray()){
            if(!Character.isDigit(character)){
                return false;
            }
        }

        return true;
    }

}
