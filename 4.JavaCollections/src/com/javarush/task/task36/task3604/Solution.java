package com.javarush.task.task36.task3604;

/* 
Разбираемся в красно-черном дереве
*/
public class Solution {
    public static void main(String[] args) {

        RedBlackTree redBlackTree = new RedBlackTree();
        System.out.println(redBlackTree.current);
        redBlackTree.insert(1);
        System.out.println(redBlackTree.current);
        redBlackTree.insert(2);
        System.out.println(redBlackTree.current);
        redBlackTree.clear();
        System.out.println(redBlackTree.isEmpty());
    }
}
