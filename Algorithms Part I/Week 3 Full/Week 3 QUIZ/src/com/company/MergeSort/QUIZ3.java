package com.company.MergeSort;

/**
 * credit:
 * https://github.com/yixuaz/algorithm4-princeton-cos226/blob/master/princetonSolution/src/part1/week3/mergesort/ShuffleLinkedList.java
 */

import java.util.Random;

public class QUIZ3 {

    /**
     * this question is hard. and the answer in
     * https://stackoverflow.com/questions/12167630/algorithm-for-shuffling-a-linked-list-in-n-log-n-time
     * is totally wrong.
     * <p>
     * u can use this algorithm, tester will failed.
     * above algorithm's problem is uneven probability.
     * why?
     * because they choose left part and right part with 0.5 and 0.5, and ignore the size of each part.
     * take an easy example, left is only 1, right have 2, 3
     * so 1 move to the first is 0.5, move to the second is 0.25, move to the third is 0.25
     * <p>
     * so the correct way should calculate current two list size. so same example. only 33.333% to move 1,
     * 66.6% to move 2. if 2 go to the front.then we have 50 % and 50 % to choose 1 and 3.
     * now the 1 have 33% chance to go to first, 66% * 50% go the the second, and 66% * 50% go to the last.
     * some people may ask how about 2, it have 66% chance to go the first.
     * u should keep the previous step when only have 2 and 3. 2 have 50% chance become previous than 3, so same as 3.
     * in another try, may situtaiton is 1 and 3,2. with 50% probability. so it is uniform.
     */
    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    public Node buildLinkedList(int n) {
        Node root = new Node(0);
        Node pre = root;
        for (int i = 1; i < n; i++, pre = pre.next) {
            pre.next = new Node(i);
        }
        return root;
    }

    public Node shuffle(Node list) {//TIME : O (N LOG N), SPACE : O (LOG N)
        if (list == null || list.next == null) return list;
        //split half
        Node slow = list, fast = list.next;
        int preL = 1;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            preL++;
            fast = fast.next.next;
        }
        int postL = preL;
        if (fast == null) {
            postL--;
        }
        Node post = slow.next, pre = list;
        slow.next = null;
        return merge(shuffle(pre), preL, shuffle(post), postL);
    }

    private Node merge(Node pre, int preL, Node post, int postL) {
        Random r = new Random();
        Node dummy = new Node(-1), p = dummy;
        for (; pre != null || post != null; p = p.next) {
            if (r.nextInt(preL + postL) < preL) {
                p.next = pre;
                pre = pre.next;
                preL--;
            } else {
                p.next = post;
                post = post.next;
                postL--;
            }
        }
        Node ret = dummy.next;
        dummy.next = null;
        return ret;
    }

//    public static void main(String[] args) {
//        SecureRandom random=new SecureRandom();
//        String[] s= StdIn.readAllLines();
//        int n=s.length;
//
//        Double[] a=new Double[n];
//
//        for(int i=0;i<n;i++){
//            a[i]=random.nextDouble();
//        }
//
//        int[] index= Merge.indexSort(a);
//
//        for (int value : index) {
//            System.out.println(s[value]);
//        }
//
//    }
}
