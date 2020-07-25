### Interview Questions: Mergesort (ungraded)
TOTAL POINTS 3
##### Question 1
#### Merging with smaller auxiliary array. <br />
Suppose that the subarray a[0] to a[n−1] is sorted and the subarray a[n] to a[2∗n−1] is sorted. How can you merge the two subarrays so that a[0] to a[2∗n−1] is sorted using an auxiliary array of length n (instead of 2n)? <br /><br />

##### Question 2
#### Counting inversions. <br />
An inversion in an array a[] is a pair of entries a[i] and a[j] such that i < j but a[i] > a[j]. Given an array, design a linearithmic algorithm to count the number of inversions. <br /><br />

##### Question 3
##### Shuffling a linked list. <br />
Given a singly-linked list containing n items, rearrange the items uniformly at random. Your algorithm should consume a logarithmic (or constant) amount of extra memory and run in time proportional to nlogn in the worst case. <br /><br /><br />


### Interview Questions: Quicksort (ungraded)
TOTAL POINTS 3
##### Question 1
#### Nuts and bolts. <br />
A disorganized carpenter has a mixed pile of n nuts and n bolts. The goal is to find the corresponding pairs of nuts and bolts. Each nut fits exactly one bolt and each bolt fits exactly one nut. By fitting a nut and a bolt together, the carpenter can see which one is bigger (but the carpenter cannot compare two nuts or two bolts directly). Design an algorithm for the problem that uses at most proportional to nlogn compares (probabilistically). <br /><br />

##### Question 2
#### Selection in two sorted arrays. <br />
Given two sorted arrays a[] and b[], of lengths n1 and n2 and an integer 0≤ k< n1+n2,
design an algorithm to find a key of rank k. The order of growth of the worst case running time of your algorithm should be logn, where n = n1 + n2 <br />

Version 1 : n1=n2 (equal length arrays) and k=n/2 (median). <br />
Version 2 : k=n/2 (median). <br />
Version 3 : no restrictions. <br /><br />
 
##### Question 3
#### Decimal dominants. <br />
Given an array with n keys, design an algorithm to find all values that occur more than n/10 times. The expected running time of your algorithm should be linear.
