package com.joe.parallelSteinerTree.supervisor;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Algorithm {
	private Graph graph;

    private int nodesInQuery;

    private double bestWeight;

    private Node bestNode = null;

    private ConcurrentLinkedQueue<Message> q = new ConcurrentLinkedQueue<>();

	public Algorithm(Graph graph) {
		this.graph = graph;
		reset();
	}

	private void reset() {
		graph.reset();
        bestWeight = Double.MAX_VALUE;
        bestNode = null;
        nodesInQuery = -1;
	}


    public Set<Node> query(Set<String> nodeNames) {
        reset();
        nodesInQuery = nodeNames.size();
//        System.out.println("Algorithm - query: " + nodeNames.size());
        for (String nodeName : nodeNames) {
            Node node = graph.getNode(nodeName);
//            System.out.println("Algorithm - query - node: " + node.toString());
            Token t = new Token(node, 0.0, null);
//            System.out.println("Algorithm - Token: " + t.toString());
            Message m = new Message(node, 0.0, t);
//            System.out.println("Algorithm - Message: " + m.toString());
            q.add(m); // q is unbounded, so no need to use put
//            System.out.println("Algorithm - q: " + q.toString());
        }
        processQueue();
        return getResultNodes(bestNode);
    }

    private Set<Node> getResultNodes(Node bestNode) {
//        System.out.println("bestNode: " + bestNode);
        if (bestNode == null) {
            return null;
        }
        else {
            Set<Node> results = new HashSet<>();
            bestNode.followAllTrails(results);
            return results;
        }
    }

    private void processQueueOld() {
        while (!q.isEmpty()) {
            System.out.println("Process Queue Size: " + q.size());
            System.out.println("Process Queue: " + q.toString());
            Message m = q.remove();
//            processQueue.add(m);
//            System.out.println("processQueue: " + processQueue.toString());
            m.getDestination().send(m, this);
        }
    }

    private void processQueue() {
        while (!q.isEmpty()) {
            boolean changed = false;
            Node tempNode; // This is for comparing the weight.
            System.out.println("Process Queue Size: " + q.size());
            System.out.println("Process Queue: " + q.toString());
            Message m = q.remove();
            Node d = m.getDestination();
            //            loop looking for == d
            for (Message msg : q) {
                if (msg.getDestination() == d) {
                    changed = true;
                    tempNode = msg.getDestination();
                    q.remove(msg);
                }
            }

            System.out.println("Process Queue: " + q.toString());
            // Confuse part!!!!!
            // not sure how to modify this part.
            // if the init q is [29, 20]
            // and the change flag is false the q won't be extend. In other words, only [29, 20]
            // but if I extend the q, I don't know how to compare two nodes.
            if (changed) {

            } else {
                m.getDestination().send(m, this);
            }
        }
    }

    /**
     * Called by Nodes to put messages on the queue.
     * @param m The message put on the queue by the Node.
     */
    public void enqueue(Message m) {
        q.add(m);
    }

//    public boolean checkProcessNode(Link l) {
//        Iterator<Message> it = processQueue.iterator();
//        boolean result = false;
//        while (it.hasNext()) {
//            Message m = it.next();
//            for(int i = 0; i < m.size(); i++) {
//                Token t = m.getTokens().get(i);
//                if (l.getDestination().equals(t.getOrigin()) && l.getSource().equals(t.getDirection())) {
////                    System.out.println("SAME SAME!!!");
//                    result = true;
//                }
//            }
//        }
//        return result;
//    }

	public boolean weightLessThanBest(double totalWeight, int candidateSize, Node node) {
		boolean result = false;
		if (totalWeight < bestWeight) {
			result = true;
			if (candidateSize == nodesInQuery) {
				bestNode = node;
				bestWeight = totalWeight;
			}
		}
//        System.out.println("weightLessThanBest bestNode: " + bestNode);
//        System.out.println("weightLessThanBest bestWeight: " + bestWeight);
		return result;
	}
}
