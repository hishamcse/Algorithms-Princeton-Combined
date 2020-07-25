package com.company.SymbolTables;

public class WebTracking {

//    Symbol Table API. User will be the key and <website, number of visited times> symbol table will be the value.
//    So, it is a symbol table consists of another symbol table.This is the idea.Here is not the exact solution to the problem.It may vary

    private final int totalwebsite;
    private final int totaluser;
    private final int [][]visitedtimes;

    public WebTracking(int totalwebsite,int totaluser){
        this.totalwebsite=totalwebsite;
        this.totaluser=totaluser;
        visitedtimes=new int[totaluser][totalwebsite];
    }

    public void counter(int user,int website){
        visitedtimes[user][website]++;
    }

    public int total(int user,int website){
        return visitedtimes[user][website];
    }
}
