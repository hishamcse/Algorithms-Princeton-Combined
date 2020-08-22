### Interview Questions: Maximum Flow (ungraded)
TOTAL POINTS 3
##### Question 1
#### Fattest path <br />
 Given an edge-weighted digraph and two vertices s and t, design an ElogE algorithm to find a fattest path from s to t. The bottleneck capacity of a path is the minimum weight of an edge on the path. A fattest path is a path such that no other path has a higher bottleneck capacity. <br /><br />

##### Question 2
#### Perfect matchings in k-regular bipartite graphs. <br />
Suppose that there are n men and n women at a dance and that each man knows exactly k women and each woman knows exactly k men (and relationships are mutual). Show that it is always possible to arrange a dance so that each man and woman are matched with someone they know.<br /><br />

##### Question 3
#### Maximum weight closure problem <br />
A subset of vertices S in a digraph is closed if there are no edges pointing from S to a vertex outside S. Given a digraph with weights (positive or negative) on the vertices, find a closed subset of vertices of maximum total weight. <br /><br /><br />


### Interview Questions: Radix Sorts (ungraded)
TOTAL POINTS 3
##### Question 1
#### 2-sum. <br />
Given an array a of n 64-bit integers and a target value T, determine whether there are two distinct integers i and j such that a[i] + a[j] = T .Your algorithm should run in linear time in the worst case. <br /><br />

##### Question 2
#### American flag sort. <br />
Given an array of n objects with integer keys between 0 and R-1, design a linear-time algorithm to rearrange them in ascending order. Use extra space at most proportional to R. <br /><br />
 
##### Question 3
#### Cyclic rotations. <br />
 Two strings s and t are cyclic rotations of one another if they have the same length and s consists of a suffix of t followed by a prefix of t. For example, "suffixsort" and "sortsuffix" are cyclic rotations.

Given n distinct strings, each of length L, design an algorithm to determine whether there exists a pair of distinct strings that are cyclic rotations of one another. <br /> 
For example, the following list of n = 12 strings of length L = 10 contains exactly one pair of strings ("suffixsort" and "sortsuffix") that are cyclic rotations of one another.
[ algorithms polynomial sortsuffix boyermoore
structures minimumcut suffixsort stackstack
binaryheap digraphdfs stringsort digraphbfs ] <br />
The order of growth of the running time should be nL^2 (or better) in the worst case. Assume that the alphabet size R is a small constant.

Signing bonus. Do it in NnL time in the worst case.
