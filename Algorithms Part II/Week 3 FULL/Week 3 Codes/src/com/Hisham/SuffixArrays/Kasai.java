package com.Hisham.SuffixArrays;

import edu.princeton.cs.algs4.SuffixArray;

import java.util.Arrays;

public class Kasai {

    int[] lcp;
    String text;

    public Kasai(String text){
        SuffixArray suffixArray = new SuffixArray(text);  // creating suffix array and sorting it
        int[] suffixArr=new int[text.length()];

        lcp=new int[text.length()];
        this.text=text;

        for(int i=0;i<text.length();i++){
            suffixArr[i]=suffixArray.index(i);
        }

        // printing
        System.out.println("Suffix Array:");
        System.out.println(Arrays.toString(suffixArr));

        lcp=calculateLCP(suffixArr);
    }

    public int[] calculateLCP(int[] suffixArr){
        int n=suffixArr.length;
        int[] lcpArr=new int[n];

        // An auxiliary array to store inverse of suffix array
        // elements. For example if suffixArr[0] is 5, the
        // invSuff[5] would store 0.  This is used to get next
        // suffix string from suffix array.
        int[] inv=new int[n];

        for(int i=0;i<n;i++){
            inv[suffixArr[i]]=i;
        }

        // Initialize length of previous LCP
        int k=0;

        // Process all suffixes one by one
        for (int i=0; i<n; i++)
        {
        /* If the current suffix is at n-1, then we don’t
           have next substring to consider. So lcp is not
           defined for this substring, we put zero. */
            if (inv[i] == n-1)
            {
                k = 0;
                continue;
            }

        /* j contains index of the next substring to
           be considered  to compare with the present
           substring, i.e., next string in suffix array */
            int j = suffixArr[inv[i]+1];

            // Directly start matching from k'th index as
            // at-least k-1 characters will match
            while (i+k<n && j+k<n && text.charAt(i+k)==text.charAt(j+k))
                k++;

            lcpArr[inv[i]] = k; // lcp for the present suffix.

            // Deleting the starting character from the string.
            if (k>0)
                k--;
        }

        return lcpArr;
    }

    public int[] lcp(){
        return lcp;
    }

    public static void main(String[] args) {
        Kasai kasai=new Kasai("banana");
        System.out.println("LCP Array:");
        System.out.println(Arrays.toString(kasai.lcp()));
    }

}
