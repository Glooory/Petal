package com.glooory.petal.app.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Glooory on 17/2/22.
 */

public class StringUtils {

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    private StringUtils(){
    }

    public static boolean isEmptyString(String str) {
        if (null == str || str.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Convert number eg: 1522 -> 1.5k
     * 785 -> 785
     * 1000 -> 1k
     * 93321 -> 93.3k
     * @param value
     * @return
     */
    public static String appenUnit(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return appenUnit(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + appenUnit(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static String appenUnit(String number) {
        long value = Long.valueOf(number);
        return appenUnit(value);
    }

}
