package com.company.QuickSort;

public class NutsAndBolts {

    //condition:no duplicate in one array
    public static int partition(Comparable[] a,int lo,int hi,Comparable find){
        int i=lo;
        int j=hi+1;
        int p=lo;

        while(true){
            while(less(a[++i],find)){
                if(i==hi){
                    break;
                }
            }
            if(a[i].compareTo(find)==0){
                swap(a,p,i);
            }
            while (less(find,a[--j])){
                if(j==lo){
                    break;
                }
            }
            if(a[j].compareTo(find)==0){
                swap(a,p,j);
            }
            if(i>=j){
                break;
            }
            swap(a,i,j);
        }
        swap(a,p,j);
        return j;
    }

    //another way
//    private static int partition(Comparable[] arr, int low, int high, Comparable pivot)
//    {
//        int i = low;
//
//        for (int j = low; j < high; j++)
//        {
//            if (less(arr[j],pivot)){
//                swap(arr,i,j);
//                i++;
//            } else if(arr[j] == pivot){
//                swap(arr,j,high);
//                j--;
//            }
//        }
//        swap(arr,i,high);
//
//        // Return the partition index of an array based on the pivot
//        // element of other array.
//        return i;
//    }

    public static void sort(Comparable[] nuts,Comparable[] bolts,int lo, int hi){
        if(hi<=lo){
            return;
        }
        int j=partition(nuts,lo,hi,bolts[hi]);
        partition(bolts,lo,hi,nuts[j]);

        sort(nuts,bolts,lo,j-1);
        sort(nuts,bolts,j+1,hi);
    }

    public static void sort(Comparable[] nuts,Comparable[] bolts){
        int lo=0;
        int hi=nuts.length-1;
        sort(nuts,bolts,lo,hi);
    }

    private static boolean less(Comparable a,Comparable b){
        return a.compareTo(b)<0;
    }

    private static void swap(Comparable[] a,int i,int j){
        Comparable temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }

    public static void main(String[] args) {
//        Comparable[] nuts = {'@', '#', '$', '%', '^', '&'};
//        Comparable[] bolts = {'$', '%', '&', '^', '@', '#'};

//        Comparable[] nuts={1,2,5,3,8,9,11,12,13,14,15,16,17,18,19};
//        Comparable[] bolts={2,3,1,9,8,5,13,11,12,15,19,18,17,16,14};

        Comparable[] nuts=new Comparable[1000];
        Comparable[] bolts=new Comparable[1000];

        for(int i=0;i<1000;i++){
            nuts[i]=i;
            bolts[i]=1000-i-1;
        }

        sort(nuts,bolts);

        for(Comparable i:nuts){
            System.out.print(i+" ");
        }
        System.out.println();

        for(Comparable i:bolts){
            System.out.print(i+" ");
        }
    }
}
