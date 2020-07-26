### Interview Questions: Priority Queues (ungraded)
TOTAL POINTS 3
##### Question 1
#### Dynamic median. <br />
Design a data type that supports insert in logarithmic time, find-the-median in constant time, and remove-the-median in logarithmic time. 
If the number of keys in the data type is even, find/remove the lower median.<br /><br />

##### Question 2
#### Randomized priority queue. <br />
Describe how to add the methods sample() and delRandom() to our binary heap implementation. The two methods return a key that is chosen uniformly at random among 
the remaining keys, with the latter method also removing that key. The sample() method should take constant time; the delRandom() method should take logarithmic time. 
Do not worry about resizing the underlying array. <br /><br />
##### Question 3
#### Taxicab numbers. <br />
A taxicab number is an integer that can be expressed as the sum of two cubes of positive integers in two different ways: a^3 + b^3 = c^3 + d^3. For example, 1729 is the smallest taxicab number: 9^3 + 10^3 = 1^3 + 12^39
 Design an algorithm to find all taxicab numbers with a, b, c, and d less than n. <br />
•	Version 1: Use time proportional to n^2 logn and space proportional to n^2. <br />
•	Version 2: Use time proportional to n^2 logn and space proportional to n.

### Interview Questions: Elementary Symbol Tables (ungraded)
TOTAL POINTS 4
##### Question 1
#### Java autoboxing and equals(). <br/>
Consider two double values a and b and their corresponding Double values x and y. <br/>
 * Find values such that (a==b) is true but x.equals(y) is false. <br/>
 * Find values such (a==b) is false but x.equals(y) is true. <br/><br/>

##### Question 2
#### Check if a binary tree is a BST. <br/>
Given a binary tree where each Node contains a key, determine whether it is a binary search tree. Use extra space proportional to the height of the tree. <br/><br/>

##### Question 3
#### Inorder traversal with constant extra space. <br/>
Design an algorithm to perform an inorder traversal of a binary search tree using only a constant amount of extra space. <br/><br/>

##### Question 4
#### Web tracking. <br/>
Suppose that you are tracking n web sites and mm users and you want to support the following API: <br/>
	* User visits a website.
	* How many times has a given user visited a given site?
 
What data structure or data structures would you use?

