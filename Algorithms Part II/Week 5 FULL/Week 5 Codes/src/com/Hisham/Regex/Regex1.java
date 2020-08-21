package com.Hisham.Regex;

public class Regex1 {  // regex 1,regex 2,regex 3 are for practice purpose only.from my another repository(Java Programming Masterclass)

    public static void main(String[] args) {

        String mystring = "I am a java programmer,am I?";
        System.out.println(mystring);
        String youstring = mystring.replaceAll("I", "You");
        String finalString = youstring.replaceAll("am", "are");
        System.out.println(finalString);

        String newstring = "AbCzzzziiERSFWW";
        System.out.println(newstring.replaceAll(".", "Y"));  //all character will be replaced

        System.out.println(newstring.replaceAll("^AbCzzz", "YYY")); //when only check the start,use '^'.otherwise if we want all,then normal

        String string = "AbCzzzziiErsAbCzzz";
        System.out.println(string.replaceAll("^AbCzzz", "YYY"));  //only the first occurence will be modified

        System.out.println(string.matches("^hello"));
        System.out.println(string.matches("^AbCzzz"));   //both are false,because matches check the entire string.so,for true,the whole string should be same
        System.out.println(string.matches("AbCzzzziiErsAbCzzz"));  //this is true
        System.out.println(string.matches("^AbCzzzziiErsAbCzzz"));  //this is also true

        System.out.println(newstring.replaceAll("ERSFWW$", "The end"));  //$ sign denotes the end

        System.out.println(newstring.replaceAll("[iE]", "new"));  //that means for every i and E,the letter will be replaced by new
        System.out.println(newstring.replaceAll("[iE][R]", "new")); //that means replacing those letters i and E which are immediate previous to R

        System.out.println("harry".replaceAll("[Hh]arry", "Harry"));  //whether the first letter is uppercase or lowercase,will return always the uppercase

        String new2string = "AbCzzziiERSFWWAbczzzzii761235514ErsAbCzzz";
        System.out.println(new2string.replaceAll("[^Es]", "W"));  //all the letters without E and s will be replaced by W

        System.out.println(new2string.replaceAll("[abcdef345678]", "A"));
        System.out.println(new2string.replaceAll("[a-f3-8]", "A"));  //these two lines will print the same output.but only the lowercase will be replaced
        System.out.println(new2string.replaceAll("[a-fA-F3-8]", "A"));  //this time all lower and uppercase letters using these will be replaced
        System.out.println(new2string.replaceAll("(?i)[a-f3-8]", "A"));  //no case sensitivity as ?

        System.out.println(new2string.replaceAll("[0-9]", "K"));
        System.out.println(new2string.replaceAll("\\d", "K"));  //these two statements do same.all digits are replaced
        System.out.println(new2string.replaceAll("\\D", "K"));  //all non digits are replaced by K

        String another = "I have a blank and\ta tab and also a newline\n";
        System.out.println(another.replaceAll("\\s", ""));  //removing all whitespace characters,create a output
        System.out.println(another.replaceAll("\\S", "t"));  //removing all non whitespaces and replace them with t
        System.out.println(another.replaceAll("\t", "a"));  //all tab character will be replaced by a
        System.out.println(another.replaceAll("\\w", "j"));  //all the characters except whitespace will be replaced
        System.out.println(another.replaceAll("\\W", "j"));  //opposite of "\\w"

        System.out.println(another.replaceAll("\\b", "X"));  //each word differentiated by whitespace will be surrounded by X

        String thirdstring = "AbCzzziiERSFWWAbczzzzii761235514ErsAbCzzz";
        System.out.println(thirdstring.replaceAll("^AbCzzz", "new"));
        System.out.println(thirdstring.replaceAll("^AbCz{3}", "new")); //using quantifier
        System.out.println(thirdstring.replaceAll("^AbCz+", "new"));  //any number of z,it is applicable.the number of z must be at least 1
        System.out.println(thirdstring.replaceAll("^AbCz*", "new"));  //the number of z is 0 or more than 0
        System.out.println(thirdstring.replaceAll("^AbCz{2,5}", "new"));  //the number of z must be greater or equal 2 and less than or equal 5
        System.out.println(thirdstring.replaceAll("z+i*7", "new"));  //any number of z(greater than 0),any number of i(greater or equal 0),and the last of the string is 7

    }
}
