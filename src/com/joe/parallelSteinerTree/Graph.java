package com.joe.parallelSteinerTree;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * A handy place to store code that applies to all nodes.
 *
 */
public class Graph {
	
	private Map<String, Node> nodes;
	
	public Graph() {
		nodes = new HashMap<>();
	}
	
	public Node getNode(String nodeName) {
		return nodes.get(nodeName);
	}

	public void reset() {
		for (String key : nodes.keySet()) {
			Node n = nodes.get(key);
			n.reset();
		}
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		for (String key : nodes.keySet()) {
			Node n = nodes.get(key);
			b.append(n.toString());
			b.append("\n\n");
		}
		return b.toString();
	}
	
	public void addLink(String source, String destination, double weight) {
		Node sourceNode = addNode(source);
		Node destNode = addNode(destination);
		sourceNode.addLink(destNode, weight);
		destNode.addLink(sourceNode, weight);
	}

	/**
	 * Adds the node if it hasn't already been added.
	 * @param nodeName The name of the node to add.
	 * @return The node.
	 */
	private Node addNode(String nodeName) {
		Node node = nodes.get(nodeName);
		if (node == null) {
			node = new Node(nodeName);
			nodes.put(nodeName, node);
		}
		return node;
	}

	public int nodeCount() {
		return nodes.size();
	}
	

}
