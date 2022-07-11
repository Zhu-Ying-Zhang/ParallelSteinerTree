package com.joe.parallelSteinerTree.supervisor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

public class ReadOrGraph {

	public static void main(String[] args) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get("/Users/zhuyingzhang/Work/StrathclydeLife/CS957/Steiner/newData/C/c03.stp"))) {
			paths.filter(Files::isRegularFile).sorted().forEach(filePathWithName -> {
				Algorithm a = null;
				OrGraph orGraph = null;
				Set<Node> result = null;
				try {
					long startTime = System.currentTimeMillis();
					orGraph = Utils.loadOrGraph(filePathWithName.toString(), true);
//					System.out.println("=== GRAPH ===");
//					System.out.println(orGraph.getGraph());
					System.out.println("=== " + filePathWithName.getFileName() + " QUERY ===");
					System.out.println(orGraph.getQuery());
					a = new Algorithm(orGraph.getGraph());
					result = a.query(orGraph.getQuery());
					System.out.println("=== " + filePathWithName.getFileName() + " RESULT ===");
					System.out.println(result);
					long endTime = System.currentTimeMillis();
					System.out.println("Runtime(second): " + (double) (endTime - startTime) / 1000);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
//			OrGraph orGraph = Utils.loadOrGraph("/Users/zhuyingzhang/Work/StrathclydeLife/CS957/Steiner/data/SteinerB/steinb1.txt");
//			System.out.println("=== GRAPH ===");
//			System.out.println(orGraph.getGraph());
//			System.out.println("=== QUERY ===");
//			System.out.println(orGraph.getQuery());
//
//			Algorithm a = new Algorithm(orGraph.getGraph());
//			Set<Node> result = a.query(orGraph.getQuery());
//
//			System.out.println("=== RESULT ===");
//			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
