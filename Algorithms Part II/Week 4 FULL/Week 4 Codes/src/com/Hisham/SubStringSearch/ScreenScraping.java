package com.Hisham.SubStringSearch;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class ScreenScraping {

    public static void main(String[] args) {
        String name = "https://finance.yahoo.com/quote/";
        In in = new In(name + StdIn.readString());
        String text = in.readAll();
        int start = text.indexOf("Last Trade:");
        int from = text.indexOf("<b>", start);
        int to = text.indexOf("</b>", from);
        String price = text.substring(from + 3, to);
        System.out.println(price);
    }
}
