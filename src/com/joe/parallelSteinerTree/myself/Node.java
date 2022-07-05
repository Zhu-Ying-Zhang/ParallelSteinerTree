package com.joe.parallelSteinerTree.myself;

public class Node{
    int num;
    int distance;
    boolean isTerminal;

    boolean visited;
    Node prev;

    public Node(int i){
        num=i;
        isTerminal=false;

        distance=Integer.MAX_VALUE;
        visited=false;
        prev=null;

    }

    public void reset() {
        distance=Integer.MAX_VALUE;
        visited=false;
        prev=null;
    }
}
