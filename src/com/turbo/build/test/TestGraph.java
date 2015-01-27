package com.turbo.build.test;

import java.util.List;

import com.turbo.build.graph.Graph;
import com.turbo.build.graph.Vertex;
import com.turbo.build.opt.ClassPathParser;
import com.turbo.build.util.Jar;
import com.turbo.build.util.JarUtil;

public class TestGraph {
	public static void main(String[] args) {

		ClassPathParser parser = new ClassPathParser(
				"test-cases\\case2\\App\\.classpath");

		parser.extractJars();

		JarUtil util = new JarUtil(parser.getEntries());

		List<Jar[]> jars = util.findConflictJars();
		for (Jar[] jar : jars) {
			System.out.println(jar[0].getFullname() + " | "
					+ jar[1].getFullname());
		}

		Graph g = new Graph(util.findConflictJars());
		// System.out.println(g);

		System.out.println(g.getVertexSet().size());
		System.out.println(g.getEdgeMap().size());

		for (Vertex key : g.getEdgeMap().keySet()) {
			System.out.println(key.getName() + " : " + key.getJar().getPath());
			for (Vertex v : g.getEdgeMap().get(key)) {
				System.out.print("\t" + v.getName() + " ");
			}
			System.out.println();
		}

		Vertex[] res = g.topologicalSort();
		for (Vertex v : res) {
			System.out.println(v.getName());
		}
		
		System.out.println(g.checkCircles());
	}
}
