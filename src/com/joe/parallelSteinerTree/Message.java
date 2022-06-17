package com.joe.parallelSteinerTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Message implements Iterable<Token>
{
	private final Node destination;
    
    private final double weight;
    
    private final List<Token> tokens = new ArrayList<Token>();

    public Message(Node destination, double weight, Token token)
    {
    	this.destination = destination;
    	this.weight = weight;
    	tokens.add(token);
    }
    
    public Message(Node destination, double weight, List<Token> tokens)
    {
    	this.destination = destination;
    	this.weight = weight;
    	this.tokens.addAll(tokens);
    }
    
    public Node getDestination() {
		return destination;
	}

    public double getWeight()
    {
        return weight;
    }
    
    public int size()
    {
    	return tokens.size();
    }

	public Iterator<Token> iterator()
	{
		return tokens.iterator();
	}

//    public void send(DisambiguateAmericanFootballTeams algorithm)
//    {
//        destination.process(this, algorithm);
//    }
    
    public String toString()
    {
        String result = "Message to " + destination.getName();
        result += "\nWeight = " + weight;
        for (Token t : tokens)
        {
            result += "\n" + t;
        }
        result += "\n";
        return result;
    }
}
