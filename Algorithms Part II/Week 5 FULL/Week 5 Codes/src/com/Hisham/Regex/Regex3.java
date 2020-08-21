package com.Hisham.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex3 {
    public static void main(String[] args) {

        System.out.println("harry".replaceAll("[H|h]arry", "Larry"));
        System.out.println("Harry".replaceAll("[H|h]arry", "Larry"));

        String tvtest = "tstvtkt";
//        String s="t[^v]";   //but it will always want a character after t other than v.so,the last occurence of t is not printed(output:0 to 2,4 to 6)
        String s = "t(?!v)";   //here,the last t will be included(output:0 to 1, 4 to 5,6 to 7) ,only the t char is matching
//        String s="t[v]";  //all t followed by v(v included)
//        String s="t[?=v]";  //all t followed by v(v excluded)
        Pattern pattern = Pattern.compile(s);
        Matcher matcher = pattern.matcher(tvtest);
        int count = 0;

        while (matcher.find()) {
            count++;
            System.out.println("Occurrence : " + matcher.start() + " to " + matcher.end());
        }

        String s1 = "1234567890";      //no match
        String s2 = "(123) 456-789";   //match
        String s3 = "(123)456-789";   //no match
        String s4 = "(123)456 789";    //no match

        System.out.println(s1.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{3})$"));
        System.out.println(s2.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{3})$"));
        System.out.println(s3.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{3})$"));
        System.out.println(s4.matches("^([\\(]{1}[0-9]{3}[\\)]{1}[ ]{1}[0-9]{3}[\\-]{1}[0-9]{3})$"));

        System.out.println("========================");

        String v1 = "4444444444444"; //match
        String v2 = "5444444444444"; //no match
        String v3 = "444444"; //no match
        String v4 = "4444444444444444"; //match

        System.out.println(v1.matches("^4[0-9]{12}([0-9]{3})?$"));
        System.out.println(v2.matches("^4[0-9]{12}([0-9]{3})?$"));
        System.out.println(v3.matches("^4[0-9]{12}([0-9]{3})?$"));
        System.out.println(v4.matches("^4[0-9]{12}([0-9]{3})?$"));

    }
}
