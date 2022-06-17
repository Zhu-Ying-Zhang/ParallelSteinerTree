package com.billy.disambiguation;

public class Token
{
    private final Node origin;
    
    private final double weight;
    
    private final Node direction;
    
    public Token(Node origin, double weight, Node direction)
    {
        this.origin = origin;
        this.weight = weight;
        this.direction = direction;
    }

    public Node getOrigin()
    {
        return origin;
    }
    
    public double getWeight()
    {
        return weight;
    }
    
    public Node getDirection()
    {
        return direction;
    }
    
    public String toString()
    {
        return ((origin == null) ? "null" : origin.getName()) + "," +
        weight + "," +
        ((direction == null) ? "null" : direction.getName());
    }
}
