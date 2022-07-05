package com.joe.parallelSteinerTree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Algorithm {
	private Graph graph;

    private int nodesInQuery;
    
    private double bestWeight;
    
    private Node bestNode = null;
    
    private Queue<Message> q = new LinkedList<Message>();

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

	
    public Set<Node> query(Set<String> nodeNames)
    {
        reset();
        nodesInQuery = nodeNames.size();
        for (String nodeName : nodeNames)
        {
            Node node = graph.getNode(nodeName);
            Token t = new Token(node, 0.0, null);
            Message m = new Message(node, 0.0, t);
            q.add(m); // q is unbounded, so no need to use put
        }
        processQueue();
        return getResultNodes(bestNode);
    }

    private Set<Node> getResultNodes(Node bestNode)
    {
        if (bestNode == null)
        {
            return null;
        }
        else
        {
            Set<Node> results = new HashSet<Node>();
            bestNode.followAllTrails(results);
            return results;
        }
    }

    private void processQueue()
    {
        while (!q.isEmpty())
        {
            Message m = q.remove();
            m.getDestination().send(m, this);
        }
    }
    
    /**
     * Called by Nodes to put messages on the queue.
     * @param m The message put on the queue by the Node.
     */
    public void enqueue(Message m)
    {
    	q.add(m);
    }

	public boolean weightLessThanBest(double totalWeight, int candidateSize, Node node)
	{
		boolean result = false;
		if (totalWeight < bestWeight)
		{
			result = true;
			if (candidateSize == nodesInQuery)
			{
	            //System.out.print("S");
				bestNode = node;
				bestWeight = totalWeight;
			}
		}
		return result;
	}
}
