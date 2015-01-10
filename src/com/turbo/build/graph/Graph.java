package com.turbo.build.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.turbo.build.util.Jar;

public class Graph {
	private Set<Vertex> vertexSet = new HashSet<Vertex>();
	
	private Map<String, ArrayList<Jar>> confMap;

	private Map<Vertex, ArrayList<Vertex>> edgeMap = new HashMap<Vertex, ArrayList<Vertex>>();
	
	public Map<String, ArrayList<Jar>> getConfMap() {
		return confMap;
	}

	public Graph(Map<String, ArrayList<Jar>> confMap) {
		this.confMap = confMap;
		
		Iterator<String> mapIter = confMap.keySet().iterator();

		while (mapIter.hasNext()) {
			String key = mapIter.next();
			ArrayList<Jar> list = confMap.get(key);

			addEdges(list);
//			for (Jar jar : list) {
//				System.out.println("  >>> "
//						+ jar.getElement().getAttributeValue("path"));
//				
//			}
		}
	}

	public Set<Vertex> getVertexSet() {
		return vertexSet;
	}

	public Map<Vertex, ArrayList<Vertex>> getEdgeMap() {
		return edgeMap;
	}

	/**
	 * Add an edge from vertex src to vertex obj: src --> obj
	 * 
	 * @param obj
	 * @param src
	 */
	private void addEdge(Vertex src, Vertex obj) {
		ArrayList<Vertex> list = edgeMap.get(src);
		if (list == null) {
			list = new ArrayList<Vertex>();
			list.add(obj);

			edgeMap.put(src, list);
		} else if(!list.contains(obj)){
			list.add(obj);
		}
	}
	
	/**
	 * Compose all edges between nodes
	 * 
	 * @param jars
	 */
	private void addEdges(ArrayList<Jar> jars) {
		for (Jar src : jars) {
			Vertex node = new Vertex(src);
			vertexSet.add(node);

			for (Jar obj : jars) {

				if (src.equals(obj)) {
					continue;
				}

				addEdge(node, new Vertex(obj));
			}
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		
		Iterator<Vertex> mapIter = edgeMap.keySet().iterator();
		
		while(mapIter.hasNext()) {
			Vertex src = mapIter.next();
			str += src.name + ":\n";
			ArrayList<Vertex> list = edgeMap.get(src);
			for(Vertex vertex : list) {
				str += "  " + vertex.name + "\n";
			}
		}
		
		return str;
	}
}
