package com.Hisham.BalancedSearchTree;

public class MergeOfRandomizedBST<Key extends Comparable<Key>,Value> {

    //time complexity of this method is Log(n) + Log(n+1) … Log(m+n-1) The value of this expression
    //will be between mLogn and mLog(m+n-1). As an optimization, we can pick the smaller tree as first tree.

    public RandomizedBST merge(RandomizedBST a,RandomizedBST b){
        if(a.size()<=b.size()){
            for(int i=0;i<a.size();i++){
                Key key= (Key) a.select(i);
                Value value= (Value) a.get(key);
                b.put(key,value);
            }
            return b;
        }
        else{
            for(int i=0;i<b.size();i++){
                Key key= (Key) b.select(i);
                Value value= (Value) b.get(key);
                a.put(key,value);
            }
            return a;
        }
    }

}
