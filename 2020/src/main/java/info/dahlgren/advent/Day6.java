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
import java.util.stream.IntStream;

public class Day6 {

    private static final String EMPTY_LINE = "";

    private Day6() {

    }

    public static int executePart1(final String[] forms){
        int answerCount = 0;
        final HashSet<Character> answers = new HashSet<>();
        for(final String form : forms){
            if(form.equals(EMPTY_LINE)){
                answerCount += answers.size();
                answers.clear();
                continue;
            }

            IntStream.range(0, form.length())
                    .mapToObj(form::charAt)
                    .forEach(answers::add);
        }
        return answerCount + answers.size();
    }

    public static int executePart2(final String[] forms){
        int participants = 0;
        int answerCount = 0;
        final Map<Character, Integer> answers = new HashMap<>();
        for(final String form : forms){
            if(form.equals(EMPTY_LINE)){
                answerCount += calculateAnswers(answers, participants);
                participants = 0;
                answers.clear();
                continue;
            }

            participants++;
            IntStream.range(0, form.length())
                    .mapToObj(form::charAt)
                    .forEach(character -> answers.put(character, answers.getOrDefault(character, 0) + 1));
        }
        return answerCount + calculateAnswers(answers, participants);
    }

    private static int calculateAnswers(final Map<Character, Integer> answers, final int participants){
        return (int) answers.values()
                .stream()
                .filter(value -> value == participants)
                .count();
    }

}
