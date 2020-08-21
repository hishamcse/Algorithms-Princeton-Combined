package com.Hisham.Regex;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChallengingRegex {   // quiz 1

    public static void main(String[] args) {

        // All strings except 11 or 111 ->  [^(11)]*[^(111)]*
        // Strings with 1 in every odd-number bit position ->  (1(01)*)0
        // Strings with an equal number of 0s and 1s ->  impossible
        // Strings with at least two 0s and at most one 1 ->  (100+)|(0+10+)|(00+1)
        // Strings that when interpreted as a binary integer are a multiple of 3 ->  (1(01*0)*1|0)*
        // Strings with no two consecutive 1s ->  (0(01|10)*0*)|(1(01)*0*)|(0(10)*(0|1)*)    or    (0*10+)*1?|0*
        // Strings that are palindromes (same forwards and backwards) -> impossible
        // Strings with an equal number of substrings of the form 01 and 10 -> 

        String regex = StdIn.readLine();
        In in = new In(StdIn.readLine());
        String input = in.readAll();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
