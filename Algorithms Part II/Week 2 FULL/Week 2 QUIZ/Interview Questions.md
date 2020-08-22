### Interview Questions: Minimum Spanning Trees (ungraded)
TOTAL POINTS 3
##### Question 1
#### Bottleneck minimum spanning tree <br />
 Given a connected edge-weighted graph, design an efficient algorithm to find a minimum bottleneck spanning tree. The bottleneck capacity of a spanning tree is the weights of its largest edge. A minimum bottleneck spanning tree is a spanning tree of minimum bottleneck capacity <br /><br />

##### Question 2
#### Is an edge in a MST. <br />
Given an edge-weighted graph G and an edge e, design a linear-time algorithm to determine whether e appears in some MST of G. <br />
Note: Since your algorithm must take linear time in the worst case, you cannot afford to compute the MST itself.<br /><br />

##### Question 3
#### Minimum-weight feedback edge set. <br />
A feedback edge set of a graph is a subset of edges that contains at least one edge from every cycle in the graph. If the edges of a feedback edge set are removed, the resulting graph is acyclic. Given an edge-weighted graph, design an efficient algorithm to find a feedback edge set of minimum weight. Assume the edge weights are positive. <br /><br /><br />


### Interview Questions: Shortest Paths (ungraded)
TOTAL POINTS 3
##### Question 1
#### Monotonic shortest path. <br />
Given an edge-weighted digraph G, design an ElogE algorithm to find a monotonic shortest path from s to every other vertex. A path is monotonic if the sequence of edge weights along the path are either strictly increasing or strictly decreasing. <br /><br />

##### Question 2
#### Second shortest path. <br />
Given an edge-weighted digraph and let P be a shortest path from vertex s to vertex t. Design an ElogV algorithm to find a path (not necessarily simple) other than P from s to t that is as short as possible. Assume all of the edge weights are strictly positive.. <br /><br />
 
##### Question 3
#### Shortest path with one skippable edge. <br />
Given an edge-weighted digraph, design an ElogV algorithm to find a shortest path from s to t where you can change the weight of any one edge to zero. Assume the edge weights are nonnegative.
