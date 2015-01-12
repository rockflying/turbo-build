package com.turbo.build.opt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.turbo.build.graph.Graph;
import com.turbo.build.graph.Vertex;

public class ConflictResolver {
	public static Graph g = null;

	public static Graph resolveConfct(Graph graph) {
		g = graph;
		if (null == g) {
			return null;
		}

		Map<Vertex, ArrayList<Vertex>> edgeMap = g.getEdgeMap();
		
		Iterator<Vertex> mapIter = edgeMap.keySet().iterator();
		while (mapIter.hasNext()) {
			Vertex src = mapIter.next();

			ArrayList<Vertex> list = edgeMap.get(src);
			for (Vertex vertex : list) {
				switch (src.comapreTo(vertex)) {
				case -1:
					g.removeEdge(vertex, src);
					break;
				case 0:  //the same jar, remove both
					g.removeEdge(src, vertex);
					g.removeEdge(vertex, src);
					break;
				case 1:
					g.removeEdge(src, vertex);
					break;
				case 4:
					// TODO do something to resolve this issue
					break;
				default:
				}
			}
		}
		
		return g;
	}
}
