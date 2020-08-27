### Interview Questions: Tries (ungraded)
TOTAL POINTS 3
##### Question 1
#### Prefix free codes <br />
 In data compression, a set of binary strings is if no string is a prefix of another. For example, {01,10,0010,1111} is prefix free, but {01,10,0010,10100} is not because 10 is a prefix of 10100. Design an efficient algorithm to determine if a set of binary strings is prefix-free. The running time of your algorithm should be proportional the number of bits in all of the binary stings. <br /><br />

##### Question 2
#### Boggle. <br />
Boggle is a word game played on an 4-by-4 grid of tiles, where each tile contains one letter in the alphabet. The goal is to find all words in the dictionary that can be made by following a path of adjacent tiles (with no tile repeated), where two tiles are adjacent if they are horizontal, vertical, or diagonal neighbors.<br /><br />

##### Question 3
#### Suffix trees <br />
Learn about and implement , the ultimate string searching data structure <br /><br /><br />


### Interview Questions: Substring Search (ungraded)
TOTAL POINTS 3
##### Question 1
#### Cyclic rotation of a string. <br />
A string s is a cyclic rotation of a string t if s and t have the same length and s consists of a suffix of t followed by a prefix of t. For example, "winterbreak" is a cyclic rotation of "breakwinter" (and vice versa). Design a linear-time algorithm to determine whether one string is a cyclic rotation of another. <br /><br />

##### Question 2
#### Tandem repeat. <br />
A tandem repeat of a base string b within a string s is a substring of s consisting of at least one consecutive copy of the base string b. Given b and s, design an algorithm to find a tandem repeat of b within s of maximum length. Your algorithm should run in time proportional to M + N, where M is length of b and N is the length s.<br />
For example, if s is "abcabcababcaba" and b is "abcab", then "abcababcab" is the tandem substring of maximum length (2 copies). <br /><br />
 
##### Question 3
#### Longest palindromic substring. <br />
 Given a string s, find the longest substring that is a palindrome in expected linearithmic time.<br />

Signing bonus: Do it in linear time in the worst case.
