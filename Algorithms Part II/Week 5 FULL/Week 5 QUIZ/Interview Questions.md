### Interview Questions: Regular Expressions (ungraded)
TOTAL POINTS 3
##### Question 1
#### Challenging REs <br />
 Construct a regular expression for each of the following languages over the binary alphabet or prove that no such regular expression is possible:<br />

1. All strings except 11 or 111.<br />
2. Strings with 1 in every odd-number bit position.<br />
3. Strings with an equal number of 0s and 1s.<br />
4. Strings with at least two 0s and at most one 1.<br />
5. Strings that when interpreted as a binary integer are a multiple of 3.<br />
6. Strings with no two consecutive 1s.<br />
7. Strings that are palindromes (same forwards and backwards).<br />
8. Strings with an equal number of substrings of the form 01 and 10. <br /><br />

##### Question 2
#### Exponential-size DFA. <br />
Design a regular expressions of length nn such that any DFA that recognizes the same language has an exponential number of states.<br /><br />

##### Question 3
#### Extensions to NFA <br />
Add to NFA.java(at booksite) the ability to handle multiway or, wildcard, and the + closure operator.<br /><br /><br />


### Interview Questions: Data Compression (ungraded)
TOTAL POINTS 3
##### Question 1
#### Ternary Huffman codes. <br />
Generalize the Huffman algorithm to codewords over the ternary alphabet (0, 1, and 2) instead of the binary alphabet. That is, given a bytestream, find a prefix-free ternary code that uses as few trits (0s, 1s, and 2s) as possible. Prove that it yields optimal prefix-free ternary code. <br /><br />

##### Question 2
#### Optimality. <br />
 1.Identify an optimal uniquely-decodable code that is neither prefix free nor suffix tree.<br />
 2.Identify two optimal prefix-free codes for the same input that have a different distribution of codeword lengths. <br /><br />
 
##### Question 3
#### Move-to-front coding. <br />
 Design an algorithm to implement move-to-front encoding so that each operation takes logarithmic time in the worst case. That is, maintain alphabet of symbols in a list. A symbol is encoded as the number of symbols that precede it in the list. After encoding a symbol, move it to the front of the list.
