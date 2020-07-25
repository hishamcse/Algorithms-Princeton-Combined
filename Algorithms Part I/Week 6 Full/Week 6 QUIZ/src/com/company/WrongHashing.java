package com.company;

public class WrongHashing {

    // at this stackoverflow post: very nice discussion on this
    // (https://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java)

//    Collision happens. Two non-equal objects have the same hash code. This will show down the performance of the hash table.
//    Since multiple objects have the same hash code, java need to iterate them the find the right one. Time complexity should be O(n),
//    where n is the number of objects having the same hash code.
//
//    Describe what happens if you override equals() but not hashCode().
//
//    Key should be unique in hash table. It means that for equal objects, it should have only one key associated with them.
//    However, if the hashCode() is not override, then there'll be duplicate keys for equal objects.
//    Therefore, the existing key cannot be modified by a newer key: they don't have the same hash code! Horrible!
//
//    Describe what happens if you override hashCode() but implement public boolean equals(Athlete that) instead of
//    public boolean equals(Object that)
//
//    Error: The method equals(Athlete) of type Athlete must override or implement a supertype method.
}
