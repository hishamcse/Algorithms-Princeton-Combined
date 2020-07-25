package com.company.SearchingApplication;

import edu.princeton.cs.algs4.ST;

public class SparseVector {
    private final int d;
    private final ST<Integer,Double> st;

    public SparseVector(int d){
        this.d=d;
        st=new ST<>();
    }

    public void put(int i,double x){
        if(x==0.0){
            st.delete(i);
        }
        st.put(i,x);
    }

    public double get(int i){
        if(!st.contains(i)){
            return 0.0;
        }
        return st.get(i);
    }

    public int nnz() {
        return st.size();
    }

    public int size(){
        return d;
    }

    public int dimension(){
        return d;
    }

    public Iterable<Integer> indices(){
        return st.keys();
    }

    public double dot(SparseVector that) {
        double sum = 0.0;

        // iterate over the vector with the fewest nonzeros
        if (this.st.size() <= that.st.size()) {
            for (int i : this.st.keys())
                if (that.st.contains(i)) sum += this.get(i) * that.get(i);
        } else  {
            for (int i : that.st.keys())
                if (this.st.contains(i)) sum += this.get(i) * that.get(i);
        }
        return sum;
    }

    public double dot(double[] that){
        double sum=0.0;
        for(int i:indices()){
            sum+=that[i]*this.get(i);
        }
        return sum;
    }

    public SparseVector scale(double alpha) {
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys()) c.put(i, alpha * this.get(i));
        return c;
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    public SparseVector plus(SparseVector that) {
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys()) c.put(i, this.get(i));                // c = this
        for (int i : that.st.keys()) c.put(i, that.get(i) + c.get(i));     // c = c + that
        return c;
    }

}
