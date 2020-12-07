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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 {

    private static final String SHINY_GOLD = "shiny gold";

    private Day7() {

    }

    private static final class Rule {

        private final String colour;
        private final Map<String, Integer> contains;

        public Rule(final String colour, final Map<String, Integer> contains){
            this.colour = Objects.requireNonNull(colour);
            this.contains = Objects.requireNonNull(contains);
        }

    }

    public static long executePart1(final String[] inputs){
        final Map<String, Rule> rules = getRules(inputs);
        return rules.values()
                .stream()
                .map(rule -> canHoldShinyGold(rule, rules))
                .filter(result -> result)
                .count();
    }

    public static long executePart2(final String[] inputs){
        final Map<String, Rule> rules = getRules(inputs);
        return countBags(rules.get(SHINY_GOLD), rules);
    }

    private static boolean canHoldShinyGold(final Rule rule, final Map<String, Rule> rules) {
        if(rule.contains.containsKey(SHINY_GOLD)){
            return true;
        }

        return rule.contains.keySet()
                .stream()
                .map(rules::get)
                .map(childRule -> canHoldShinyGold(childRule, rules))
                .filter(result -> result)
                .findFirst()
                .orElse(Boolean.FALSE);
    }

    private static long countBags(final Rule rule, final Map<String, Rule> rules){
        return rule.contains.entrySet()
                .stream()
                .map(entry -> entry.getValue() + (entry.getValue() * countBags(rules.get(entry.getKey()), rules)))
                .mapToLong(result -> result)
                .sum();
    }

    private static Map<String, Rule> getRules(final String[] inputs){
        return Arrays.stream(inputs)
                .map(Day7::parse)
                .collect(Collectors.toMap(rule -> rule.colour, Function.identity()));
    }

    private static Rule parse(final String input) {
        final String[] parts = input.split(" bags contain ");

        if(parts.length != 2) {
            throw new IllegalStateException("Invalid rule: " + input);
        }

        final String[] rawContains = parts[1].split(", ");

        final Map<String, Integer> contains = new HashMap<>();
        for(final String rawContain : rawContains) {
            final String[] containParts = rawContain.split(" ");

            if(containParts.length == 3){
                continue;
            } else if(containParts.length != 4) {
                throw new IllegalStateException("Invalid contain: " + rawContain);
            }

            contains.put(containParts[1] + " " + containParts[2], Integer.parseInt(containParts[0]));
        }

        final String colour = parts[0];
        return new Rule(colour, contains);
    }

}
