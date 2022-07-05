package com.joe.parallelSteinerTree;

import java.util.Set;

/**
 * 
 * The OR Library at http://people.brunel.ac.uk/~mastjjb/jeb/orlib/steininfo.html
 * has test data for graphs that can be represented by this class. The files contain
 * the nodes and links for a graph, but also a query to try on the graph.
 * 
 * The Utils class has a method to parse one of these files and load it into this structure.
 *
 */
public class OrGraph {
	
	private Graph graph;
	
	private Set<String> query;

	public OrGraph(Graph graph, Set<String> query) {
		this.setGraph(graph);
		this.setQuery(query);
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public Set<String> getQuery() {
		return query;
	}

	public void setQuery(Set<String> query) {
		this.query = query;
	}
	
}
