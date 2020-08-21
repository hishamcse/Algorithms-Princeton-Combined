package com.Hisham.Regex;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Harvester {

    public static void main(String[] args) {
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
