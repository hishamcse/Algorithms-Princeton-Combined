### Interview Questions: Union–Find (ungraded)
TOTAL POINTS 3
##### Question 1
#### Social network connectivity. <br />
Given a social network containing n members and a log file containing m timestamps at which times pairs of members formed friendships,
design an algorithm to determine the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). 
Assume that the log file is sorted by timestamp and that friendship is an equivalence relation. The running time of your algorithm should be mlogn or better and 
use extra space proportional to n. <br /><br />

##### Question 2
#### Union-find with specific canonical element. <br />
Add a method find() to the union-find data type so that find(i) returns the largest element in the connected component
containing i. The operations, union(), connected(), and find() should all take logarithmic time or better. <br />
For example, if one of the connected components is {1,2,6,9}, then the find() method should return 99 for each of the four elements in the connected components. <br /><br />

##### Question 3
#### Successor with delete. <br />
Given a set of n integers S={0,1,...,n−1} and a sequence of requests of the following form: <br />
   * Remove x from S  <br />
   * Find the successor of x: the smallest y in S such that y≥x.  <br />
   
design a data type so that all operations (except construction) take logarithmic time or better in the worst case. <br /><br /><br />


### Interview Questions: Analysis of Algorithms (ungraded)
TOTAL POINTS 3
##### Question 1
#### 3-SUM in quadratic time. <br />
Design an algorithm for the 3-SUM problem that takes time proportional to n^2 in the worst case. You may assume that you can sort the n integers in time 
proportional to n^2 or better. <br /><br />

##### Question 2
#### Search in a bitonic array. <br />
An array is bitonic if it is comprised of an increasing sequence of integers followed immediately by a decreasing sequence of integers. Write a program that, given a bitonic array of n distinct integer values, determines whether a given integer is in the array. <br />

Standard version:  Use ∼3lgn compares in the worst case. <br />
Signing bonus:  Use ∼2lgn compares in the worst case (and prove that no algorithm can guarantee to perform fewer than ∼2lgn compares in the worst case). <br /><br />

##### Question 3
#### Egg drop. <br />
Suppose that you have an n-story building (with floors 1 through n) and plenty of eggs. An egg breaks if it is dropped from floor T or higher and does not break otherwise. Your goal is to devise a strategy to determine the value of T given the following limitations on the number of eggs and tosses: <br />

Version 0:  1 egg,≤T tosses. <br />
Version 1:  ~1lgn eggs and ∼1lgn tosses. <br />
Version 1:  ~1lgn eggs and ∼1lgn tosses. <br />
Version 2:  ∼lgT eggs and ∼2lgT tosses. <br />
Version 3:  2 eggs and ~2sqrt(n) tosses. <br />
Version 4:  2 eggs and ≤ csqrt(T) tosses for some fixed constant c.
