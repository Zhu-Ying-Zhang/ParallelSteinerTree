package com.joe.parallelSteinerTree.supervisor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Utils {
	public static Set<String> loadFeatures(String filename) throws IOException {
		Set<String> result = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			result.add(line);
		}
		br.close();
		return result;
	}
	
	public static Graph loadGraph(String filename) throws IOException {
		Graph graph = new Graph();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			StringTokenizer st = new StringTokenizer(line, "\t");
			if (st.countTokens() == 2) {
				graph.addLink(st.nextToken(), st.nextToken(), 1.0);
			} else if (st.countTokens() == 3) {
				graph.addLink(st.nextToken(), st.nextToken(), Double.parseDouble(st
						.nextToken()));
			}
		}
		br.close();
		return graph;
	}

	public static OrGraph loadOrGraph(String filename) throws IOException {
		BufferedReader br = null;
		Graph graph = null;
		Set<String> query = new HashSet<>();
		try
		{
			br = new BufferedReader(new FileReader(filename));
			graph = new Graph();

			// Get node and link count
			String line = br.readLine().trim();
			StringTokenizer st = new StringTokenizer(line, " ");
			int nodeCount = Integer.parseInt(st.nextToken());
			int linkCount = Integer.parseInt(st.nextToken());
			
			// Get the links
			for (int i = 0; i < linkCount; i++) {
				line = br.readLine().trim();
				st = new StringTokenizer(line, " ");
				graph.addLink(st.nextToken(), st.nextToken(), Double.parseDouble(st.nextToken()));
			}
			if (graph.nodeCount() != nodeCount) {
				throw new StreamCorruptedException("Unexpected node count");
			}
			
			// Get the query size
			line = br.readLine().trim();
			st = new StringTokenizer(line, " ");
			int querySize = Integer.parseInt(st.nextToken());
			
			// Get the query
			for (line = br.readLine(); line != null; line = br.readLine()) {
				st = new StringTokenizer(line.trim());
				while (st.hasMoreTokens()) {
					query.add(st.nextToken());
				}
			}
			
			if (query.size() != querySize) {
				throw new StreamCorruptedException("Unexpected query size");
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return new OrGraph(graph, query);
	}

	public static OrGraph loadOrGraph(String filename, boolean isNew) throws IOException {
		Graph graph = null;
		Set<String> query = new HashSet<>();
		try
		{
			Reader.init(filename);


			Reader.next();						//SECTION
			Reader.next();						//Graph
			Reader.next();
			int nodeCount = Reader.nextInt();
			Reader.next();                      //Edges
			int linkCount = Reader.nextInt();
			graph = new Graph(nodeCount);
			// Get the links
			for (int i = 0; i < linkCount; i++) {
				Reader.next();                   // E
				String a = Reader.next();
				String b = Reader.next();
				String c = Reader.next();
				graph.addLink(a, b, Double.parseDouble(c));
				graph.costMatrix[Integer.parseInt(a)][Integer.parseInt(b)] = Integer.parseInt(c);
				graph.costMatrix[Integer.parseInt(b)][Integer.parseInt(a)] = Integer.parseInt(c);
			}
			System.out.println("Node Count: " + graph.nodeCount());
			if (graph.nodeCount() != nodeCount) {
				throw new StreamCorruptedException("Unexpected node count");
			}

			// Get the query size
			Reader.next();						// END
			Reader.next();						// SECTION
			Reader.next();						// Terminals
			Reader.next();						// Terminals
			int querySize = Reader.nextInt();

			// Get the query
			for (int i = 0; i < querySize; i++) {
				Reader.next();                   // T
				query.add(Reader.next());
			}
			System.out.println("Query Count: " + query.size());
			if (query.size() != querySize) {
				throw new StreamCorruptedException("Unexpected query size");
			}
		} finally {
			Reader.close();
		}
		return new OrGraph(graph, query);
	}
}
