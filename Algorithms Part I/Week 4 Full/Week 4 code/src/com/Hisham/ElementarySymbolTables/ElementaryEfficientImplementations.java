package com.Hisham.ElementarySymbolTables;

public class ElementaryEfficientImplementations {

    private static String[] keys=new String[100]; //suppose the size 100
    private static Integer[] vals=new Integer[100];

    public static Integer get(String key){ //can be any comparable
        int i=rank(key);
        if(i<keys.length && keys[i].compareTo(key)==0){
            return vals[i];
        }else {
            return null;
        }
    }

    private static int rank(String key){
        int lo=0;
        int hi=keys.length-1;
        while (lo<=hi){
            int mid=lo+(hi-lo)/2;
            if(keys[mid].compareTo(key)==0){
                return mid;
            }else if(keys[mid].compareTo(key)>0){
                lo=mid-1;
            }else{
                hi=mid+1;
            }
        }
        return lo;
    }
}
