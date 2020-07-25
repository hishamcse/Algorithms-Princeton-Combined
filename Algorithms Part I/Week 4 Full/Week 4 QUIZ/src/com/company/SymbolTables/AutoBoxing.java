package com.company.SymbolTables;

public class AutoBoxing {

    public static void main(String[] args) {
        System.out.println(0.00==-0.00);
        System.out.println(Double.valueOf(0.00).equals(Double.valueOf(-0.00)));
        System.out.println(Double.NaN==Double.NaN);
        System.out.println(Double.valueOf(Double.NaN).equals(Double.valueOf(Double.NaN)));
    }
}
