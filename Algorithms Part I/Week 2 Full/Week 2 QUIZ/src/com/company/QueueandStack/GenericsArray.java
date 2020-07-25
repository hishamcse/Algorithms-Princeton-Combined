//package com.company.QueueandStack;
//
//public class GenericsArray {
//    Why is Generic Array Creation not Allowed in Java?

//    To understand this topic let us directly start with an example.
//
//    List<Integer> arrayOfIntegerList[] = new ArrayList<>[10]; // compile time error !!
//    You will find that a simple statement like this will not even compile because the Java compiler does not allow this. To understand the reason, you first need to know two arrays are covariant and generics are invariant.
//
//    Covariant: It means you can assign subclass type array to its superclass array reference. For instance,
//
//    Object objectArray[] = new Integer[10]; // it will work fine
//    Invariant: It means you cannot assign subclass type generic to its super class generic reference because in generics any two distinct types are neither a subtype nor a supertype. For instance,
//
//    List<Object> objectList = new ArrayList<Integer>(); // won't compile
//    Because of this fundamental reason, arrays and generics do not fit well with each other.
//
//    Now let’s get back to the actual question. If generic array creation were legal, then compiler generated casts would correct the program at compile time but it can fail at runtime, which violates the core fundamental system of generic types.
//
//    Let us consider the following example to understand that:-
//
//1) List<Integer> arrayOfIdList[] = new ArrayList<Integer>[10];// Suppose generic array creation is legal.
//2) List<String> nameList = new ArrayList<String>();
//3) Object objArray[] = arrayOfIdList; // that is allowed because arrays are covariant
//4) objArray[0] = nameList;
//5) Integer id = objArray[0].get(0);
//    As we assumed generic array creation is legal, so line 1 is valid and creates an array of ID List.
//
//    In line 2, we have created a simple list of string.
//
//    In line 3, we passed an arrayOfIdList object to objArray reference, which is legal because arrays are covariant.
//
//    In line 4, we have assigned nameList (i.e. the list of string) into objArray that is pointing to the arrayOfIdList object. It is alright because of Type Erasure, means the runtime instance of List<String> is List and List<Integer> arrayOfIdList[] is list[], so this will not generate any exception. Here comes the biggest problem, we have assigned the list of string (i.e., List<String>) into an array that can only contain the list of integer.
//
//    In line 5, mistakenly, we are trying to get the first element of the 0th element of an array. As arrayOfIdList declared as an array of integer list, the compiler will cast that assignment to Integer which will generate ClassCastException at runtime.
//
//    Here one of the major purposes of generic is failed (i.e., compile time strict type checking.) Therefore, to address this problem compile time error gets generated at line 1.
//}
