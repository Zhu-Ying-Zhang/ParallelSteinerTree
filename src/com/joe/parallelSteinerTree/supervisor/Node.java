package com.joe.parallelSteinerTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Node
{
    private final String name;
    
    private final Map<Node, Token> candidate = new HashMap<Node, Token>();
    
    private final List<Link> neighbours = new ArrayList<Link>();
    
    public Node(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    
    public void addLink(Node n, double w)
    {
        Link l = new Link(this, n, w);
        neighbours.add(l);
    }
    
    public String toString()
    {
        StringBuffer b = new StringBuffer();
        b.append("Node: ");
        b.append(name);
        b.append("\n");
        for (Link l : neighbours)
        {
            b.append("    ");
            b.append(l.toString());
            b.append("\n");
        }
        return b.toString();
    }

    public String candidateToString()
    {
        String result = "\n";
        for (Node n : candidate.keySet())
        {
            result += candidate.get(n).toString() + "\n";
        }
        return result;
    }

    public void reset()
    {
    	candidate.clear();
    }

    public void send(Message m, Algorithm algorithm)
    {
    	//
    	// NB if we had a list of messages waiting at this node, we could just merge
    	// all of them before deciding to broadcast.
    	// This should provide fewer messages, more memory locality and
    	// fewer context switches.
    	//
        if (mergeMessageIntoCandidate(m))
        {
        	//
        	// The message changed this node, so continue.
        	//

        	if (algorithm.weightLessThanBest(calculateTotalWeight(), candidate.size(), this))
        	{
        		//
        		// Our solution is good, so
        		// broadcast our candidate to our neighbors.
        		//

        		List<Token> tokens = createTokenList();

        		for (Link l : neighbours)
        		{
        			algorithm.enqueue(new Message(l.getDestination(), l.getWeight(), tokens));
        		}
        	}
        }
    }

	private List<Token> createTokenList()
	{
		List<Token> tokens = new ArrayList<Token>();
		for (Node n : candidate.keySet())
		{
			Token myToken = candidate.get(n);
			Token messageToken = new Token(n, myToken.getWeight(), this);
			tokens.add(messageToken);
		}
		return tokens;
	}

	private boolean mergeMessageIntoCandidate(Message m)
	{
		//
        // First merge the new tokens into our candidate.
        //
        double inc = m.getWeight() / m.size(); // tokens share cost of ride
        boolean changed = false;
        for (Token t : m)
        {
            Node origin = t.getOrigin();
            double w = t.getWeight() + inc;
            Token curr = candidate.get(origin);
            if (curr == null || w < curr.getWeight())
            {
                // Either we don't have a token from there or the new one is better
            	// so copy the latest one, remembering to add the (shared) weight of the last trip.
                curr = new Token(origin, w, t.getDirection());
                candidate.put(origin, curr);
                changed = true;
            }
        }
		return changed;
	}

	private double calculateTotalWeight()
	{
		//
        // Calculate the weight of our candidate.
        //
        double totalWeight = 0.0;
        for (Node n : candidate.keySet())
        {
            totalWeight += candidate.get(n).getWeight();
        }
		return totalWeight;
	}
	
    public void followAllTrails(Set<Node> results)
    {
        results.add(this);
        
        for (Node n : candidate.keySet())
        {
            Node dir = candidate.get(n).getDirection();
            if (dir != null && dir != this)
            {
                dir.followTrailTo(n, results);
            }
        }
    }

    private void followTrailTo(Node n, Set<Node> results)
    {
        results.add(this);
        if (n == this)
        {
            return;
        }
        Node dir = candidate.get(n).getDirection();
        if (dir != null && dir != this)
        {
            dir.followTrailTo(n, results);
        }
    }
}
