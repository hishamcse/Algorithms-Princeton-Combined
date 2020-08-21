package com.Hisham.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex2 {
    public static void main(String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1>this is first heading</h1>");
        stringBuilder.append("<h2>this is second header</h2>");
        stringBuilder.append("<p> this is a paragraph </p>");
        stringBuilder.append("<h2>this is final header</h2>");
        stringBuilder.append("<p> this is a last paragraph </p>");

        String h2pattern = "<h2>";
//        String h2pattern=".*<h2>.*";  //now true,an implement starting with anything and finishes on anything
//        Pattern pattern= Pattern.compile(h2pattern);
        Pattern pattern = Pattern.compile(h2pattern, Pattern.CASE_INSENSITIVE);  //case insensitive
        Matcher matcher = pattern.matcher(stringBuilder);
        System.out.println(matcher.matches());  //if we use it,then matcher has too be reset.because any operation after this will not work

        matcher.reset();
        int count = 0;
        while (matcher.find()) {  //all because * using at h2.so,we have to change that
            count++;
            System.out.println("Occurrence " + count + ": " + matcher.start() + " to " + matcher.end());
        }

//        String h2groupPattern="(<h2>)";
//        String h2groupPattern="(<h2>.*</h2>)";   //* is a greedy quantifier.it will check the whole stringBuilder to see whether there are more h2 than the first and it will grab the whole
        String h2groupPattern = "(<h2>.*?</h2>)";   //now *? is lazy quantifier which will only print out just the occurrences of it.not the whole inside the first h2 and last /h2
        Pattern groupPattern = Pattern.compile(h2groupPattern);
        Matcher groupMatcher = groupPattern.matcher(stringBuilder);
        System.out.println(groupMatcher.matches());
        groupMatcher.reset();

        while (groupMatcher.find()) {
            System.out.println("Occurrence: " + groupMatcher.group(1));   //group 0 meaning the whole string.1 meaning the (<h2>)
        }

//        String h2textGroups="(<h2>)(.+?)(</h2>)";  //3 groups::(1)(2)(3)  ,0 means the whole stringBuilder
//        Pattern textGroupPattern=Pattern.compile(h2textGroups);
//        Matcher textGroupMatcher=textGroupPattern.matcher(stringBuilder);
//
//        while(textGroupMatcher.find()){
//            System.out.println("Occurrence: "+textGroupMatcher.group(2));  //as we want the contents not the tags
//        }

        StringBuilder sb = new StringBuilder();
        sb.append("kheJur kha google@gmail.com mara kha");
        String h2textGroups = "(\\S)([e]*?@[g])(\\S)";  //3 groups::(1)(2)(3)  ,0 means the whole stringBuilder
        Pattern textGroupPattern = Pattern.compile(h2textGroups);
        Matcher textGroupMatcher = textGroupPattern.matcher(sb);

        while (textGroupMatcher.find()) {
            System.out.println("Occurrence: " + textGroupMatcher.group(2));  //as we want the contents not the tags
        }

    }
}
