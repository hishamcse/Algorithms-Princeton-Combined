package com.company.ElementarySort;

public class FullSortingUsingCustomDataType implements Comparable<FullSortingUsingCustomDataType>{
      private final int day,month,year;

      public FullSortingUsingCustomDataType(int d,int m,int y){
          day=d;
          month=m;
          year=y;
      }

    @Override
    public int compareTo(FullSortingUsingCustomDataType o) {
        if(this.year<o.year){return -1;}
        if(this.year>o.year){return 1;}
        if(this.month<o.month){return -1;}
        if(this.month>o.month){return 1;}
        if(this.day<o.day){return -1;}
        if(this.day>o.day){return 1;}

        return 0;
    }

    public static boolean less(Comparable a,Comparable b){
          return a.compareTo(b)<0;
    }

    public static void swap(Comparable []a,int i,int j){
          Comparable swapval=a[i];
          a[i]=a[j];
          a[j]=swapval;
    }

    public static boolean isSorted(Comparable []a){
          for(int i=1;i<a.length;i++){
              if(less(a[i],a[i-1])){
                  return false;
              }
          }
          return true;
    }
}
