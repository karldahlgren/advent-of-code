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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day16 {

    private static final String NEW_LINE = "";
    private static final String DEPARTURE = "departure";

    private static class Rule {

        private final String name;
        private final Set<Integer> validNumbers;

        private Rule(final String name, final Set<Integer> validNumbers){
            this.name = Objects.requireNonNull(name);
            this.validNumbers = Objects.requireNonNull(validNumbers);
        }

    }

    private static class Ticket {

        private final int[] numbers;

        private Ticket(final int[] numbers){
            this.numbers = Objects.requireNonNull(numbers);
        }
    }

    private Day16() {

    }

    public static int executePart1(final String[] lines){
        final Set<Rule> rules = getRules(lines);
        final List<Ticket> nearbyTickets = getNearbyTickets(lines);
        final Set<Integer> validNumbers = rules.stream()
                .map(rule -> rule.validNumbers)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        int sum = 0;
        for(Ticket nearbyTicket : nearbyTickets){
            for(int number : nearbyTicket.numbers){
                if(!validNumbers.contains(number)){
                    sum += number;
                }
            }
        }

        return sum;
    }

    public static long executePart2(final String[] lines){
        final Set<Rule> rules = getRules(lines);
        final Ticket myTicket = getMyTicket(lines);
        final Set<Integer> validNumbers = rules.stream()
                .map(rule -> rule.validNumbers)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        final List<Ticket> tickets = getNearbyTickets(lines)
                .stream()
                .filter(ticket -> Arrays.stream(ticket.numbers)
                        .allMatch(validNumbers::contains))
                .collect(Collectors.toList());
        tickets.add(myTicket);

        final Map<Integer, List<Rule>> mappedRules = new HashMap<>();
        for(Rule rule : rules) {
            for(int column = 0; column < rules.size(); column++){
                if(isValid(rule, column, tickets)){
                    final List<Rule> validRules = mappedRules.getOrDefault(column, new ArrayList<>());
                    validRules.add(rule);
                    mappedRules.put(column, validRules);
                }
            }
        }

        final Map<Integer, Rule> uniqueRule = new HashMap<>();
        while(uniqueRule.size() != mappedRules.size()) {
            for(Map.Entry<Integer, List<Rule>> entry : mappedRules.entrySet()){
                if(entry.getValue().size() == 1){
                    final Rule rule = entry.getValue().get(0);

                    if(uniqueRule.containsKey(entry.getKey())){
                        throw new IllegalArgumentException("Invalid state: " + uniqueRule);
                    }

                    uniqueRule.put(entry.getKey(), rule);

                    for(Map.Entry<Integer, List<Rule>> other : mappedRules.entrySet()){
                        other.getValue().remove(rule);
                    }
                    break;
                }
            }
        }

        long sum = 1;
        for(int index = 0; index < myTicket.numbers.length; index++){
            final Rule rule = uniqueRule.get(index);

            if(rule.name.startsWith(DEPARTURE)){
                sum *= myTicket.numbers[index];
            }
        }

        return sum;
    }

    private static boolean isValid(final Rule rule, final int column, final List<Ticket> tickets){
        for(Ticket nearbyTicket : tickets){
            if(!rule.validNumbers.contains(nearbyTicket.numbers[column])){
                return false;
            }
        }

        return true;
    }

    private static Ticket getMyTicket(final String[] lines) {
        for(int index = 0; index < lines.length; index++){
            if(lines[index].equals("your ticket:")){
                final int[] numbers = Arrays.stream(lines[index + 1].split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                return new Ticket(numbers);
            }

        }

        throw new IllegalArgumentException("Invalid position");
    }

    private static List<Ticket> getNearbyTickets(final String[] lines){
        final List<Ticket> nearbyTickets = new ArrayList<>();
        boolean reading = false;
        for (String line : lines) {
            if (line.equals("nearby tickets:")) {
                reading = true;
                continue;
            }

            if (reading) {
                final int[] numbers = Arrays.stream(line.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                nearbyTickets.add(new Ticket(numbers));
            }
        }

        return nearbyTickets;
    }

    private static Set<Rule> getRules(final String[] lines){
        final Set<Rule> rules = new HashSet<>();
        for (String line : lines) {
            if (line.equals(NEW_LINE)) {
                break;
            }

            final Set<Integer> validNumbers = new HashSet<>();
            final String[] parts = line.split(": ");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }

            final String name = parts[0];

            final String[] intervalParts = parts[1].split(" or ");

            if (intervalParts.length != 2) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }


            validNumbers.addAll(getInterval(intervalParts[0]));
            validNumbers.addAll(getInterval(intervalParts[1]));
            rules.add(new Rule(name, validNumbers));
        }

        return rules;
    }

    private static Set<Integer> getInterval(final String interval) {
        final String[] parts = interval.split("-");
        if(parts.length != 2){
            throw new IllegalArgumentException("Invalid interval: " + interval);
        }

        final int startIndex = Integer.parseInt(parts[0]);
        final int endIndex = Integer.parseInt(parts[1]);
        final Set<Integer> result = new HashSet<>();
        for(int index = startIndex; index <= endIndex; index++){
            result.add(index);
        }

        return result;
    }

}
