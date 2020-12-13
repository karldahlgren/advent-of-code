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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    private Day13() {

    }

    private static class Bus {

        private final int index;
        private final int time;

        public Bus(final int index, final int time){
            this.index = index;
            this.time = time;
        }
    }

    public static int executePart1(final int earliest, final String input){
        final Bus[] buses = getBuses(input);
        int time = earliest;
        while(true) {
            for(Bus bus : buses){
                if(time % bus.time == 0){
                    return bus.time * (time - earliest);
                }
            }
            time++;
        }
    }

    public static long executePart2(final String input){
        final List<Bus> buses = Arrays.stream(getBuses(input))
                .sorted(Comparator.comparingInt(bus -> bus.index + bus.time))
                .collect(Collectors.toList());
        final Bus lessFrequentBus = buses.get(buses.size() - 1);
        final Bus secondLessFrequentBus = buses.get(buses.size() - 2);
        long time = 100000000000000L;
        while ((time + lessFrequentBus.index) % (lessFrequentBus.time) != 0 ||
                (time + secondLessFrequentBus.index) % (secondLessFrequentBus.time) != 0) {
            time++;
        }

        final long interval = (long) lessFrequentBus.time * secondLessFrequentBus.time;

        buses.remove(secondLessFrequentBus);
        buses.remove(lessFrequentBus);

        while (true) {
            if(isAllMatch(buses, time)){
                return time;
            } else {
                time += interval;
            }
        }
    }

    private static boolean isAllMatch(final List<Bus> buses, final long time){
        return buses.stream()
                .allMatch(bus -> (time + bus.index) % bus.time == 0);
    }

    private static Bus[] getBuses(final String buses){
        final List<Bus> result = new ArrayList<>();
        final String[] parts = buses.split(",");
        for(int index = 0; index < parts.length; index++){
            try {
                result.add(new Bus(index, Integer.parseInt(parts[index])));
            } catch (NumberFormatException ignored){ }
        }

        return result.toArray(new Bus[0]);
    }

}
