package com.joe.parallelSteinerTree.supervisor;

import static com.joe.parallelSteinerTree.supervisor.Utils.loadFeatures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class DisambiguateAmericanFootballTeams
{

	/*************************************************************************
     * 
     ************************************************************************/
	public static void main(String[] args) {
		try {
			//
			//  Load the graph from the graph file and create the algorithm.
			//
			Algorithm a = new Algorithm(Utils.loadGraph("D:\\Newgate\\Projects\\Disambiguation\\americanfootball.txt"));

			//
			// This file contains the features from the result we would add to an asset.
			// i.e. not nicknames, etc.
			//
			Set<String> features = loadFeatures("D:\\Newgate\\Projects\\Disambiguation\\features.txt");

			//
			// Execute each of the queries from the queries file,
			// and output the results.
			//
			BufferedReader br = new BufferedReader(new FileReader(
					"D:\\Newgate\\Projects\\Disambiguation\\footballqueries.txt"));
			for (String queryString = br.readLine(); queryString != null; queryString = br
					.readLine()) {
				System.out.println("\n\nQUERY: " + queryString);
				StringTokenizer st = new StringTokenizer(queryString, "\t");
				if (st.countTokens() > 1) {
					Set<String> query = new HashSet<String>();
					while (st.hasMoreTokens()) {
						query.add(st.nextToken());
					}
					Set<Node> results = a.query(query);
					System.out.println("Results:");
					if (results != null) {
						for (Node n : results) {
							String name = n.getName();
							if (features.contains(name)) {
								System.out.println("--> " + name + " <--");
							} else {
								System.out.println(name);
							}
						}
					} else {
						System.out.println("NULL");
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
