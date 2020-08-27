### Interview Questions: Reductions (ungraded)
TOTAL POINTS 3
##### Question 1
#### Longest path and longest cycle <br />
 Consider the following two problems <br />
LongestPath: Given an undirected graph G and two distinct vertices s and t, find a simple path (no repeated vertices) between s and t with the most edges.<br/>
LongestCycle: Given an undirected graph G′, find a simple cycle (no repeated vertices or edges except the first and last vertex) with the most edges. <br />
Show that LongestPath linear-time reduces to LongestCycle. <br /><br />

##### Question 2
#### 3Sum and 4Sum. <br />
Consider the following two problems: <br />
3Sum: Given an integer array a, are there three distinct indices i, j, and k such that a[i] + a[j] + a[k] = 0 <br />
4Sum: Given an integer array b, are there four distinct integers i, j, k, and ℓ such that b[i] + b[j] + b[k] + b[l] = 0 <br />
Show that 3Sum linear-time reduces to 4Sum.<br /><br />

##### Question 3
#### 3Sum and 3Linear <br />
Consider the following two problems: <br />
3Linear: Given an integer array a, are there three indices (not necessarily distinct) i, j, and k such that a[i] + a[j] = 8*a[k] <br />
3Sum: Given an integer array b, are there three indices (not necessarily distinct) i, j, and k such that b[i] + b[j] + b[k] = 0 <br />
Show that 3Linear linear-time reduces to 3Sum.<br /><br /><br />


### Interview Questions: Linear Programming (ungraded)
TOTAL POINTS 3
##### Question 1
#### Feasibility detection. <br />
Suppose that you want to solve a linear program but you don't have a starting initial basic feasible solution—perhaps the all 0 vector is not feasible. Design a related linear program whose solution will be a basic feasible solution to the original linear program (assuming the original linear program has a basic feasible solution). <br /><br />

##### Question 2
#### Detecting unboundedness <br />
 Describe how to modify the simplex algorithm to detect an unbounded linear program—a linear program in which there is a feasible solution that makes the objective function arbitrarily large.<br /><br />
 
##### Question 3
#### Birkhoff-von Neumann theorem. <br />
 Consider the polyhedron P defined by ∑i Xij = 1, ∑j Xij = 1 ; Xij≥0. Prove that all extreme points of P have integer coordinates (0 or 1). <br /><br /><br />
 
### Interview Questions: Intractability (ungraded)
TOTAL POINTS 3
##### Question 1
#### Graph 3-colorability <br />
An undirected graph is 3-colorable if the vertices can be colored red, green, or blue in such a way that no edge connects two vertices with the same color. Prove that 3COLOR is NP-complete. <br /><br />

##### Question 2
#### Decision problems <br />
Traditionally, the complexity classes P and NP are defined in terms of decision problems (where the answer is either yes or no) instead of search problems (where the answer is the solution itself). Prove that the search problem version of SAT (find a binary solution to a given system of boolean equations?) polynomial-time reduces to the decision version of SAT (does there exists a binary solution to a given system of boolean equations?) <br /><br />
 
##### Question 3
#### Optimization problems <br />
 Given an undirected graph with positive edge weights, the traveling salesperson problem is to find a simple cycle that visits every vertex and has minimum total weight. The search problem version of the problem is, given a parameter L, find a tour of length at most L. Prove that the optimization version of the problem polynomial-time reduces to the search version of the problem. <br />
  Remark: for many problems such as this one, the optimization version of the problem (which is not known to be in NP) is solvable in polynomial time if and only if the search version of the problem (which is easily seen to be in NP) is.
