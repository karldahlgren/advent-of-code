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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day1Test {

    private static final int[] NUMBERS = new int[] {1765,1742,1756,1688,1973,1684,1711,1728,1603,1674,1850,1836,1719,1937,
            1970,1770,1954,1848,1885,1851,1474,1801,1769,1904,1906,1739,1717,1830,1985,1930,1791,1977,1713,
            1787,1773,1672,1750,1931,1807,1762,1835,1736,1979,1923,1782,1797,1822,1903,1729,343,1678,1753,
            1873,1358,1987,1821,1761,1988,1886,1669,857,1894,1404,1909,1789,1752,1882,1969,1892,1701,1956,
            1839,483,1897,1730,1829,1879,1810,1755,1799,1932,1715,1809,418,1896,1691,1749,1922,1631,1780,
            1734,1859,1695,1548,1948,1997,1921,1994,1369,1364,1764,1697,1833,1239,616,1786,1890,677,1867,
            1705,1993,1925,1774,1732,1686,1847,1911,1841,1962,1907,1919,1725,1687,1236,1864,1855,1928,1941,
            1709,1683,1676,1889,1982,1595,1735,1857,1731,1816,2003,1724,741,1655,1308,1959,1955,1768,1795,
            1804,1961,1693,1884,1813,1927,1845,1689,1646,1803,2008,1599,1984,1871,1814,1918,1990,1858,1908,
            1949,1980,1618,1718,1712,1989,1876,1947,1974,1838,1865,1842,1817,680,1986,1812,1895,1991,1644,
            1877,1880,1792,1800,1899,1806,1699,1685,1793,1647,1429,1751,1722,1887,1968 };

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
    @DisplayName("December 1: Part 2 - Corner case 1")
    void testPart2CornerCase2(){
        final int[] numbers = new int[] { 1000,20 };

        final Optional<Integer> result = Day1.executePart1(numbers);
        assertFalse(result.isPresent());
    }

}
