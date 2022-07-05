package com.joe.parallelSteinerTree.myself;

import java.util.LinkedList;

public class OrGraph {
    static int Nodes;
    static int Edges;
    static int Terminals;

    static int[][] weights;
    static LinkedList<Node>[] adjacencyList;
    static Node[] node;

    static void initialize() {
        weights=new int[Nodes][Nodes];
        adjacencyList=new LinkedList[Nodes];
        node=new Node[Nodes];
        for(int i = 0;i < Nodes;i++) {
            node[i]=new Node(i);
            adjacencyList[i]=new LinkedList<Node>();
        }
    }
}
