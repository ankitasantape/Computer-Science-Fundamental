/*Fraction to Decimal Conversion
Convert a fraction (dividend/divisor) to a string.
If the decimal part is recurring, show the repeating digits in parentheses.
        Example: 10 / 3 = 3.(3)
Example Results
1/2 → "0.5"

2/1 → "2"

2/3 → "0.(6)"

4/333 → "0.(012)"

-50/8 → "-6.25"

7/-12 → "-0.58(3)"
        */

package com.acme.piyush;

import java.util.*;

public class FractionToDecimal {
    public static String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";

        StringBuilder result = new StringBuilder();

        // Handle sign
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append("-");
        }

        // Convert to long to avoid overflow
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);

        // Integer part
        result.append(num / den);
        num %= den;

        if (num == 0) return result.toString();

        // Decimal part
        result.append(".");
        Map<Long, Integer> map = new HashMap<>();

        while (num != 0) {
            if (map.containsKey(num)) {
                int start = map.get(num);
                result.insert(start, "(");
                result.append(")");
                break;
            }

            map.put(num, result.length());
            num *= 10;
            result.append(num / den);
            num %= den;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(fractionToDecimal(10, 3));   // Output: "3.(3)"
        System.out.println(fractionToDecimal(1, 6));    // Output: "0.1(6)"
        System.out.println(fractionToDecimal(-7, 12));  // Output: "-0.58(3)"
        System.out.println(fractionToDecimal(0, 5));    // Output: "0"
        System.out.println(fractionToDecimal(1, 333));  // Output: "0.(003)"
    }
}

