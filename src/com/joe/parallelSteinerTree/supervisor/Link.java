package com.joe.parallelSteinerTree;

public class Link
{
    private final double weight;
    
    private final Node source;
    
    private final Node destination;
    
    public Link(Node source, Node destination, double weight)
    {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
    public double getWeight()
    {
        return weight;
    }
    
    public Node getSource()
    {
        return source;
    }
    
    public Node getDestination()
    {
        return destination;
    }
    
    public String toString()
    {
        return source.getName() + "--" + weight + "->" + destination.getName();
    }
}
