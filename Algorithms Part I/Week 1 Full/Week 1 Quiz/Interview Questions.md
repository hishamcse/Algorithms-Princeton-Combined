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
Given a set of nn integers S={0,1,...,n−1} and a sequence of requests of the following form: <br />
   * Remove x from S <br />
   * Find the successor of x: the smallest y in S such that y≥x. <br />
design a data type so that all operations (except construction) take logarithmic time or better in the worst case. <br /><br /><br />
